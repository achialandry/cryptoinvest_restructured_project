package info.lansachia.cryptoinvest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 * @author  Landry Achia
 */
public class LoginActivity extends AppCompatActivity  {


    /**
     * Autocomplete text view for user email
     */
    private AutoCompleteTextView mEmailView;

    /**
     * user password EditText
     */
    private EditText mPasswordView;

    /**
     * link to sign up activity
     */
    private Button mlinkToSignUp;
    /**
     * for firebase auth
     */
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser() !=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signInExistingUser(view);
            }
        });

        mlinkToSignUp = (Button) findViewById(R.id.link_to_sign_up);

        mlinkToSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, info.lansachia.cryptoinvest.SignUp.class);
                finish();
                startActivity(intent);
            }
        });

    }




    /**
     * Sign in Existing Users
     */
    public void signInExistingUser(View v)   {
        attemptLogin();

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Log.d("CryptoInvest", "Login not successful" + task.getException());
                        showErrorDialog("There Was a Problem Signing you In...Try again?");
                    }else {

                        Log.d("CryptoInvest", "Login Attempt() successful"  + task.isSuccessful());
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class );
                        finish();
                        startActivity(intent);
                    }
                }
            });


        }
    }

    /**
     * show error dialog
     * @param loginErrorMessage to show user
     */
    private void showErrorDialog(String loginErrorMessage){
        new AlertDialog.Builder(this)
                .setTitle("Oops!")
                .setMessage(loginErrorMessage)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * check if user email is valid
     * @param email of the user
     * @return true if valid email
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * password validation method
     * @param password of the user
     * @return true if password passes validation
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


}

