package info.lansachia.cryptoinvest;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * @author  Landry Achia
 *
 * This Class will run a job once conditions are met with the constraints
 * As soon as user device boots. it will generate currency list and compare to
 * Firebase user data for authenticated user
 * a notification showing updates about price changes for currency user is tracking
 *
 */

public class NotificationJobs extends JobService {


    /**
     * TAG for notification used for debugging
     */
    private static final String TAG = "Notification Jobs";

    /**
     * notification channel id
     */
    private static final String CHANNEL_ID = "notification_channel_crypt_invest3_3_p";

    /**
     * notification id
     */
    private static final int NOTIFICATION_ID = 3;

    /**
     * currency price when queried from api
     */
    private List<CurrencyItem> mNewCurrencyPrice;

    /**
     * database price when use set initial data to track
     */
    private String mDbPrice;

    /**
     * id of currency from database which user is tracking
     */
    private String mDbCurrencyId;

    /**
     * tracking price for currency which is obtained from database
     */
    private String mDbUserTrackPrice;

    /**
     * price of currency
     */
    int mCurrentPriceCurrency;

    /**
     * Icon for notification
     */
    private String mNotificationIcon;

    /**
     * notification helper
     */
    private FirebaseDbNotificationHelper mDbNotificationHelper;

    /**
     * notification Title
     */
    private static final String mNotificationTitle = "Crypto Invest";

    /**
     * Notification message
     */
    private String mNotificationMessage;


    /**
     * context
     */
    Context mContext = getBaseContext();



    @Override
    public boolean onStartJob(final JobParameters job) {
        //Offloading work to a new thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                compareData();
            }
        }).start();

        return true; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        return false; // Answers the question: "Should this job be retried?"
    }


    /**
     * method to compare user data and api data before firing notification
     */
    public void compareData() {
        try {

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("crypto-invest-cff6e");

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                         mDbPrice =  dataSnapshot1.child("currencyPrice").getValue(String.class);
                                         System.out.println("price is: "+ mDbPrice);
                                         mDbCurrencyId = dataSnapshot1.child("currencyId").getValue(String.class);
                                         System.out.println("Currency Id is : " + mDbCurrencyId);
                                         mDbUserTrackPrice = dataSnapshot1.child("userSettingCurrencyValue").getValue(String.class);
                                         onSuccessNotif(mDbCurrencyId, mDbPrice, mDbUserTrackPrice);
                                     }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Log.d(TAG, "completeJob: " + "jobStarted");
            Thread.sleep(9000);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * check the price set by user
     * @return new currency price from api
     */
    public int check(){
        mNewCurrencyPrice = CurrencyLab.get(getApplicationContext()).getCurrencyItems();
        if (mNewCurrencyPrice != null) {

            for (int index = 0; index < mNewCurrencyPrice.size(); index++) {
                if (mNewCurrencyPrice.get(index).getCurrencyName().toLowerCase().equals(mDbCurrencyId)) {
                    //getting current price of currency at that time before comparing
                    mCurrentPriceCurrency = Integer.parseInt(mNewCurrencyPrice.get(index).getPrice());
                }
                break;

            }

        }

        return mCurrentPriceCurrency;
    }

    /**
     * push notification based on results amd comparison
     */
    public void pushNotifications(){
        try{
            int toTRack = Integer.parseInt(mDbPrice);
            int compareValTRack = Integer.parseInt(mDbUserTrackPrice);
            mNotificationIcon = mDbCurrencyId;
            if (mCurrentPriceCurrency - toTRack > compareValTRack ){

                compareValTRack = mCurrentPriceCurrency - toTRack;

                mNotificationMessage = mDbCurrencyId.toUpperCase() + " price increased by" + compareValTRack;

                NotificationHelper notificationHelper = new NotificationHelper(mContext);
                NotificationCompat.Builder builder = notificationHelper.getNotificationBuilder(mNotificationTitle, mNotificationMessage);
                notificationHelper.getNotificationManager().notify(NOTIFICATION_ID, builder.build());
            }else {

                mNotificationMessage = mDbCurrencyId.toUpperCase() + " Investment Not recommended Now @" + compareValTRack;

                NotificationHelper notificationHelper = new NotificationHelper(mContext);
                NotificationCompat.Builder builder = notificationHelper.getNotificationBuilder(mNotificationTitle, mNotificationMessage);
                notificationHelper.getNotificationManager().notify(NOTIFICATION_ID, builder.build());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * method to act as callback to make sure all data is obtained before other methods
     * run
     * @param currencyId of currency
     * @param currencyPrice price of currency
     * @param userTrackPrice tracking price to check changes based on this price
     */
    public void onSuccessNotif(String currencyId, String currencyPrice, String userTrackPrice){
        check();
        pushNotifications();
    }
}
