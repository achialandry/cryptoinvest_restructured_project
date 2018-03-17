package info.lansachia.cryptoinvest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This activity class handles the signing up process for a new user.
 *
 * @author  Landry Achia
 */
public class SignUp extends AppCompatActivity {

    /**
     * UI reference to email of register form
     */
    private EditText mEmail;

    /**
     * UI reference to password
     */
    private EditText mPassword;

    /**
     * UI reference to confirm passowrd
     */
    private EditText mConfirmPassword;

    /**
     * UI reference to click to sign in activity
     */
    private TextView mSignInLink;

    /**
     * UI reference to button to confirm user registration
     */
    private Button mRegisterButton;

    /**
     * firebase auth
     */

    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Create a firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        mEmail = (EditText)findViewById(R.id.sign_up_email);
        mPassword = (EditText) findViewById(R.id.password_sign_up);
        mConfirmPassword = (EditText) findViewById(R.id.password_confirm);
        mSignInLink = (TextView)findViewById(R.id.link_to_sign_in);
        mRegisterButton = (Button)findViewById(R.id.sign_up_button);

        //keyboard sign_in action
        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == R.id.register_form_finished || id == EditorInfo.IME_NULL)
                {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        mSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CryptoInvest", "Link to Sign In activity Clicked");
                Intent intent = new Intent(SignUp.this, info.lansachia.cryptoinvest.LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CryptoInvest", "Sign Up Button clicked");
                signUp();
            }
        });
    }

    /**
     * execute when signup button is pressed
     */
    public void signUp(){
        attemptRegistration();
    }

    /**
     * method to attemp registration for new user
     */
    private void attemptRegistration(){
        //reset errors displayed on form
        mEmail.setError(null);
        mPassword.setError(null);

        //store values after registration attempts
        String signUpEmail = mEmail.getText().toString();
        String signUpPassword = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //check if password is valid and if password was entered
        if(!TextUtils.isEmpty(signUpPassword) && isPasswordValid(signUpPassword)){
            mPassword.setError(getString(R.string.minimum_password));
        }

        //check for valid email address
        if(TextUtils.isEmpty(signUpEmail)){
            mEmail.setError(getString(R.string.invalid_email));
        }else if(!isEmailValid(signUpEmail)){
            mEmail.setError(getString(R.string.invalid_email));
        }

        if(cancel){
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }else{
            //create a new firebase user
            createFirebaseUser();
        }
    }

    /**
     * method to check if email is valid
     * @param email of user
     * @return boolean if validation is okay
     */
    private boolean isEmailValid(String email){
        return email.contains("@") && email.length()>5;
    }

    /**
     * method to check if password is valid
     * @param password of user
     * @return true if password passes validation
     */
    private boolean isPasswordValid(String password)
    {
        String confirmPassword = mConfirmPassword.getText().toString();
        return password.equals(confirmPassword) && password.length()<6;
    }

    /**
     * Crete new firebase user method
     */
    private void createFirebaseUser(){
        String userEmail = mEmail.getText().toString();
        String userPassword = mPassword.getText().toString();

        mFirebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Log.d("CryptoInvest", "User Creation Failed" );
                    Toast.makeText(SignUp.this, "Registration failed, try again?" + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("CryptoInvest", "User Created" + task.isSuccessful());
                    Toast.makeText(SignUp.this, "Registration Successful...",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, info.lansachia.cryptoinvest.MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
