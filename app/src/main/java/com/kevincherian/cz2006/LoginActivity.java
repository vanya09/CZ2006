package com.kevincherian.cz2006;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class LoginActivity extends ActionBarActivity {

    
    protected Button mSignUpButton;
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // To show the looping progress while user is being logged in
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);
        
        mSignUpButton = (Button)findViewById(R.id.signupButton);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    
    mUsername = (EditText)findViewById(R.id.usernameField);
    mPassword = (EditText)findViewById(R.id.passwordField);
    mLoginButton = (Button)findViewById(R.id.loginButton);
    mLoginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // To check if the user adds valid input
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            //To eliminate white spaces entered by the users accidently

            username = username.trim();
            password = password.trim();


            //To check if any field is left empty
            if (username.isEmpty() || password.isEmpty() ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage(R.string.login_error_message)
                        .setTitle(R.string.login_error_title)
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                // Login
                setSupportProgressBarIndeterminateVisibility(true);
                
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        setSupportProgressBarIndeterminateVisibility(false); //Indicator of Progress
                        
                        if (e == null) {
                            //Success!
                            Intent intent = new Intent(LoginActivity.this,NavigationDrawerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                    }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(e.getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
            }

        }
    });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
