package com.kevincherian.cz2006;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by hp on 3/21/2015.
 */
public class UserProfileEditActivity extends ActionBarActivity {
    EditText mfirstName,mlastName,mNationality,mNRIC,mEmail,mPhone,mResidence,mAdress;
    ParseUser currentUser = ParseUser.getCurrentUser();
    Button submit;
    private Toolbar tool_bar;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        tool_bar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool_bar);
        //set button
        submit = (Button)findViewById(R.id.profileEditSubmitButton);
        //set username
        TextView username = (TextView)findViewById(R.id.profileuserError);
        username.setText(currentUser.getUsername());
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                editProfile();
                Toast toast=Toast.makeText(getApplicationContext(),"Submit successful",Toast.LENGTH_SHORT);
                toast.show();
                //Intent intent = new Intent(UserProfileEditActivity.this,Medic.class);
                //startActivity(intent);
            }
        });
    }



    public void editProfile()
    {
        mfirstName = (EditText)findViewById(R.id.firstnameEdit);
        mlastName = (EditText)findViewById(R.id.lastnameEdit);
        mNRIC = (EditText)findViewById(R.id.nricEdit);
        mEmail=(EditText)findViewById(R.id.emailEdit);
        mPhone=(EditText)findViewById(R.id.phoneEdit);
        mResidence=(EditText)findViewById(R.id.residenceEdit);
        mAdress = (EditText)findViewById(R.id.adressEdit);
        mNationality=(EditText)findViewById(R.id.nationalityEdit);

        String firstName = mfirstName.getText().toString();
        String lastName = mlastName.getText().toString();
        String nric = mNRIC.getText().toString();
        String email = mEmail.getText().toString();
        String phone = mPhone.getText().toString();
        String residence = mResidence.getText().toString();
        String addz = mAdress.getText().toString();
        String nationality = mNationality.getText().toString();

        firstName=firstName.trim();
        lastName=lastName.trim();
        nric=nric.trim();
        email=email.trim();
        phone=phone.trim();
        residence=residence.trim();
        addz=addz.trim();
        nationality=nationality.trim();

        if (!firstName.isEmpty())
            currentUser.put("first_name", firstName);
        if (!lastName.isEmpty())
            currentUser.put("last_name", lastName);
        if (!nric.isEmpty())
            currentUser.put("nric", nric);
        if (!email.isEmpty())
            currentUser.put("email", email);
        if (!phone.isEmpty())
            currentUser.put("phone", phone);
        if (!residence.isEmpty())
            currentUser.put("country", residence);
        if (!addz.isEmpty())
            currentUser.put("address", addz);
        if (!nationality.isEmpty())
            currentUser.put("nationality", nationality);

        currentUser.saveInBackground();
        mfirstName.setText("");
        mlastName.setText("");
        mNRIC.setText("");
        mEmail.setText("");
        mPhone.setText("");
        mAdress.setText("");
        mNationality.setText("");
        mResidence.setText("");
    }
}
