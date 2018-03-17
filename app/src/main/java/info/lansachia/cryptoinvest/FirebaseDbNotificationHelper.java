package info.lansachia.cryptoinvest;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * @author  Landry Achia
 *
 * Class is a notification helper class which will handle the saving or pushing
 * of data user wants to track onto firebase
 */

public class FirebaseDbNotificationHelper {
    /**
     * Database reference variable
     */
    private DatabaseReference mDatabaseReference;

    /**
     * boolean to check if data is saved successfully
     */
    private Boolean mSaved = null;

    /**
     * to assign userId for logged in user from firebase
     */
    private String mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


    /**
     * constructor for the firebase helper class
     * @param databaseReference for DatabaseReference
     */
    public FirebaseDbNotificationHelper(DatabaseReference databaseReference){
        this.mDatabaseReference = databaseReference;
    }

    /**
     * Saving data to database
     * @param currencyFireBaseDataModel for currencies
     * @return boolean if data is saved to firebase
     */
    protected Boolean saveDataToFirebase(CurrencyFireBaseDataModel currencyFireBaseDataModel){
        if (currencyFireBaseDataModel == null){
            mSaved = false;
        }else{
            try {
                //actual save to database
                mDatabaseReference.child(mUserId).setValue(currencyFireBaseDataModel);
                mSaved = true;
                Log.d("Firebase Db", "Saving data to Fb method");
            }catch (DatabaseException e){
                e.printStackTrace();
                mSaved = false;
            }
        }

        return mSaved;
    }


}
