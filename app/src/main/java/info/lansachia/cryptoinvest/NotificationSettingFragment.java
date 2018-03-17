package info.lansachia.cryptoinvest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * @author  Landry Achia
 *
 * This fragment handles the setting user will want to make tracking for a particular
 * currency
 */

public class NotificationSettingFragment extends Fragment {

    /**
     * UI reference to Name of currency user will be tracking
     */
    private EditText mCurrencyTrackName;

    /**
     * UI Reference to the value of currency user will select from spinner
     */
    private Spinner mCurrencyValueTrack;

    /**
     * UI reference to apply setting of user selection
     */
    private Button mApplySettingButton;

    /**
     * reference to database
     */
    private DatabaseReference mDatabaseReference;

    /**
     * helper to firebase notification class
     */
    private FirebaseDbNotificationHelper mDbNotificationHelper;

    /**
     * check for price to be incremented by
     */
    private String currencyUserPriceIncrementValueForNotification;

    /**
     * name of currency to have notifications for
     */
    private String userCurrencyNameForNotification;

    /**
     * currency list items
     */
    private List<CurrencyItem> mItems;

    /**
     * currency price
     */
    String currentPriceCurrency;

    /**
     * currency Id
     */
    String currencyId;

    /**
     * required empty controller
     */

    public NotificationSettingFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mItems = CurrencyLab.get(getContext()).getCurrencyItems();

        //Setting up firebase Db
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDbNotificationHelper = new FirebaseDbNotificationHelper(mDatabaseReference);
        //inflate the layout from this view
        View notificationView = inflater.inflate(R.layout.notification_setting_fragment, container, false);

        //initalize the views
        mCurrencyTrackName = (EditText) notificationView.findViewById(R.id.currency_track_name_on_fragment);
        mCurrencyValueTrack = (Spinner) notificationView.findViewById(R.id.currency_track_value_spinner);
        mApplySettingButton = (Button) notificationView.findViewById(R.id.apply_notification_setting);

        fillTrackValues();

        mApplySettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseData();
                sendData();
            }
        });
        return notificationView;
    }


    /**
     * fill values into spinner
     */
    private void fillTrackValues() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");
        adapter.add("5");
        adapter.add("10");
        adapter.add("25");
        adapter.add("50");
        adapter.add("100");
        adapter.add("150");
        adapter.add("250");
        adapter.add("300");
        adapter.add("500");
        adapter.add("750");
        adapter.add("1000");
        adapter.add("1500");
        adapter.add("2000");
        adapter.add("2500");
        adapter.add("5000");

        //set adapter instance to spinner
        mCurrencyValueTrack.setAdapter(adapter);

    }

    /**
     * saves user setting values to main activity so user knows which
     * currency is currently tracked and what price changes are tracked
     */
    private void sendData() {

        //intent object
        Intent i = new Intent(getContext(),
                MainActivity.class);

        //package data
        i.putExtra("FRAGMENT_CURRENCY_NAME_KEY", "NotificationSettingFragment");
        i.putExtra("TRACK_NAME_KEY", mCurrencyTrackName.getText().toString());
        i.putExtra("TRACK_VALUE_KEY", Integer.valueOf(mCurrencyValueTrack.getSelectedItem().toString()));

        //reset widgets
        mCurrencyTrackName.setText("");
        mCurrencyValueTrack.setSelection(0);

        //Start activity
        getActivity().startActivity(i);
    }

    /**
     * properties to be saved to firebase
     */
    private void firebaseData() {
        CurrencyFireBaseDataModel model = new CurrencyFireBaseDataModel();

        //get data from user entry on fragment
        currencyUserPriceIncrementValueForNotification = mCurrencyValueTrack.getSelectedItem().toString();
        userCurrencyNameForNotification = mCurrencyTrackName.getText().toString();

        Log.d("Firebase Db", "Created Firebase");


        if (mItems != null) {

            for (int index = 0; index < mItems.size(); index++) {
                if (mItems.get(index).getCurrencyName().toLowerCase().equals(userCurrencyNameForNotification.toLowerCase())) {
                    currentPriceCurrency = mItems.get(index).getPrice();
                    currencyId = mItems.get(index).getCurrencyLogo();
                    break;
                }
            }

        }

        //returns null check
        model.setCurrencyId(currencyId);
        //returns null check
        model.setCurrencyPrice(currentPriceCurrency);

        model.setUserSettingCurrencyName(userCurrencyNameForNotification);
        model.setUserSettingCurrencyValue(currencyUserPriceIncrementValueForNotification);
        //saving the data
        if (model.getCurrencyPrice() != null) {
            mDbNotificationHelper.saveDataToFirebase(model);
            Log.d("Firebase Db", "Saved to Firebase");
        } else {
            Log.d("Firebase Db", "Saved to Firebase Failed");
            Toast.makeText(getActivity(), "Invalid Currency name or No Input", Toast.LENGTH_SHORT).show();
        }

    }

}
