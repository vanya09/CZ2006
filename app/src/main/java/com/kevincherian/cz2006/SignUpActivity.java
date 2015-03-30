//Sign Up Activity

package com.kevincherian.cz2006;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.widget.Spinner;
import android.os.Build;
import android.widget.Toast;


import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends ActionBarActivity {


    // Toolbar for the activity
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.sign_up_progress)
    View mProgressView;

    @InjectView(R.id.sign_up_form)
    View mSignUpFormView;

    @InjectView(R.id.firstname)
    EditText mFirstnameEditText;

    /**
     * UI for user to enter last name
     */
    @InjectView(R.id.lastname)
    EditText mLastnameEditText;

    /**
     * UI for user to enter username
     */
    @InjectView(R.id.username)
    EditText mUsernameEditText;

    /**
     * UI for user to enter password
     */
    @InjectView(R.id.password)
    EditText mPasswordEditText;

    /**
     * UI for user to enter e-mail
     */
    @InjectView(R.id.email)
    EditText mEmailEditText;

    /**
     * Button to submit the sign up data
     */
    @InjectView(R.id.sign_up_button)
    Button mSignUpButton;

    /**
     * UI for user to choose age group
     */
    @InjectView(R.id.age)
    EditText mAgeEditText;

    @InjectView(R.id.phone)
    EditText mPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_clear_mtrl_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });

    }

    public void attemptSignUp() {
        // Reset errors.
        mUsernameEditText.setError(null);
        mEmailEditText.setError(null);
        mPasswordEditText.setError(null);
        mFirstnameEditText.setError(null);
        mLastnameEditText.setError(null);
        mPhoneEditText.setError(null);
        mAgeEditText.setError(null);

        // Store values at the time of the login attempt.
        String firstName = mFirstnameEditText.getEditableText().toString();
        String lastName = mLastnameEditText.getEditableText().toString();
        String username = mUsernameEditText.getEditableText().toString();
        String email = mEmailEditText.getEditableText().toString();
        String password = mPasswordEditText.getEditableText().toString();
        String age = mAgeEditText.getEditableText().toString();
        String phone = mPhoneEditText.getEditableText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid username
        if (TextUtils.isEmpty(username)) {
            mUsernameEditText.setError(getString(R.string.error_field_required));
            focusView = mUsernameEditText;
            cancel = true;
        }

        // Check for a valid first name
        if (TextUtils.isEmpty(firstName)) {
            mFirstnameEditText.setError(getString(R.string.error_field_required));
            focusView = mFirstnameEditText;
            cancel = true;
        }

        // Check for a valid last name
        if (TextUtils.isEmpty(lastName)) {
            mLastnameEditText.setError(getString(R.string.error_field_required));
            focusView = mLastnameEditText;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordEditText.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordEditText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError(getString(R.string.error_field_required));
            focusView = mEmailEditText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailEditText.setError(getString(R.string.error_invalid_email));
            focusView = mEmailEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first 
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showProgress(true);
            Managers.getUserManager().signUp(firstName, lastName, username, age, phone, password, email, new ResultCallback<Integer>() {
                @Override
                public void onResult(Integer data) {
                    onSignUpResult(data);

                }
            });

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Helper function to check if email is valid
     *
     * @param email
     * @return
     */
    private boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    /**
     * Helper function to check if password is valid
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Called when the result of the sign up is returned
     *
     * @param errorCode Error code of the result if any
     */
    private void onSignUpResult(Integer errorCode) {
        showProgress(false);

        if (errorCode == 0) {
            Toast.makeText(SignUpActivity.this, "Sign up success! Please login to continue.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(SignUpActivity.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
            switch (errorCode) {
                case ParseException.USERNAME_TAKEN:
                    mUsernameEditText.setError("This username has been taken.");
                    mUsernameEditText.requestFocus();
                    break;
                case ParseException.INVALID_EMAIL_ADDRESS:
                    mEmailEditText.setError("Invalid email address.");
                    mEmailEditText.requestFocus();
                    break;
                case ParseException.EMAIL_TAKEN:
                    mEmailEditText.setError("This email is already linked to another user.");
                    mEmailEditText.requestFocus();
                    break;
                default:
                    Toast.makeText(SignUpActivity.this, "An unknown error occured, please try again later!", Toast.LENGTH_SHORT).show();
            }
        }


    }
}

//    protected EditText mUsername;
//    protected EditText mPassword;
//    protected EditText mEmail;
//    protected Button mSignUpButton;
//    
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        setContentView(R.layout.activity_sign_up);
//        mUsername = (EditText)findViewById(R.id.usernameField);
//        mPassword = (EditText)findViewById(R.id.passwordField);
//        mEmail = (EditText)findViewById(R.id.emailField);
//        mSignUpButton = (Button)findViewById(R.id.registerButton);
//        mSignUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // To check if the user adds valid input
//                String username = mUsername.getText().toString();
//                String password = mPassword.getText().toString();
//                String email = mEmail.getText().toString();
//                
//                //To eliminate white spaces entered by accident
//                
//                username = username.trim();
//                password = password.trim();
//                email = email.trim();
//                
//                //To check if any field is left empty
//                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                    builder.setMessage(R.string.signup_error_message)
//                        .setTitle(R.string.signup_error_title)
//                        .setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//                else {
//                    // create the new user!
//                    setSupportProgressBarIndeterminateVisibility(true);
//                    ParseUser newUser = new ParseUser();
//                    newUser.setUsername(username);
//                    newUser.setPassword(password);
//                    newUser.setEmail(email);
//                    newUser.signUpInBackground(new SignUpCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            setSupportProgressBarIndeterminateVisibility(false);
//                            
//                            if (e == null) {
//                                // Success!
//                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                                // clear the navigation history to prevent the user form going back after signing up
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                                builder.setMessage(e.getMessage())
//                                        .setTitle(R.string.signup_error_title)
//                                        .setPositiveButton(android.R.string.ok, null);
//                                AlertDialog dialog = builder.create();
//                                dialog.show();
//                            }
//                        }
//                    });
//                }
//                
//            }
//        });
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
