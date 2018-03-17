package info.lansachia.cryptoinvest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * @author  Landry Achia
 * Main activity that host other fragments and carries the main business logic and
 * content of the application
 * This activity host the fragments for currency list, and more..
 */

public class MainActivity extends AppCompatActivity {

    /**
     * UI refrence to name of currency user will be tracking
     */
    private TextView mCurrencyTrackingName;

    /**
     * UI reference to the Value of currency user will be tracking
     */
    private TextView mCurrencyTrackingByValue;

    /**
     * UI reference to the button whose click event opens fragment for user
     * to set notification to track currency
     */
    private FloatingActionButton mFloatingActionButton;

    /**
     * time to reschedule notification
     */
    private static final int mPeriodicity = (int) TimeUnit.MINUTES.toSeconds(3);

    /**
     * time persist notification job if it doesn't start in time above
     */
    private static final int mToleranceInterval = (int) TimeUnit.MINUTES.toSeconds(1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // fragment manager to manage fragments and transactions

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.currency_main_act);
        if (fragment == null) {
            fragment = new RecyclerViewCurrency();

            fragmentManager.beginTransaction()
                    .replace(R.id.currency_main_act, fragment)
                    .addToBackStack(null)
                    .commit();
            Log.d("Crypto Invest", "Fragment added");
        }

        //reference Views for currency tracking name and values
        mCurrencyTrackingName = (TextView) findViewById(R.id.currency_tracking_name);
        mCurrencyTrackingByValue = (TextView) findViewById(R.id.currency_tracking_value);

        //reference to notification
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationSettingFragment(true);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //determines what fragment started the activity
            final String sender = bundle.getString("FRAGMENT_CURRENCY_NAME_KEY");

            //If its the fragment then data is allocated to it
            if (sender != null) {
                this.registerCurrencySettingFromFragment();
                Toast.makeText(this, "Notification Setting Registerd!", Toast.LENGTH_LONG).show();
            }
        }
        //shedules the job
        scheduleJob(this);

    }

    /**
     * open currency notification setting fragment on click
     * @param click boolean to have this method run only when true
     */
    private void openNotificationSettingFragment(boolean click) {
        //
        NotificationSettingFragment notificationSettingFragment = new NotificationSettingFragment();
        //show fragment
        if (click){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity, notificationSettingFragment).commit();
        }

    }

    /**
     * Receive data from fragment
     */
    private void registerCurrencySettingFromFragment() {
        //receive setting data from fragment via intents
        Intent i = getIntent();
        String currencyName = i.getStringExtra("TRACK_NAME_KEY");
        int currencyValue = i.getIntExtra("TRACK_VALUE_KEY", 0);

        //Set currency data to views
        mCurrencyTrackingName.setText(currencyName + ":");
        mCurrencyTrackingByValue.setText("$ " + String.valueOf(currencyValue));
    }


    /**
     * method that schedules job for notifications
     * @param context of the application
     */
    public static void scheduleJob(Context context){
        //creating new firebase job dispatcher
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myJob = dispatcher.newJobBuilder()
                .setTag("scheduled_Job_crypto_invest")
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setService(NotificationJobs.class)
                .setTrigger(Trigger.executionWindow(mPeriodicity,mPeriodicity + mToleranceInterval))
                .setReplaceCurrent(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        Log.d("Schedule", "Job Scheduled");

        dispatcher.mustSchedule(myJob);
    }




}
