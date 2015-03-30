package com.kevincherian.cz2006;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class UserProfileFragment extends Fragment {
    UserInfoRetrievalInterface UserInfo = new ParseUserInfoRetrieval();
    TextView username,firstname,lastname,dob,nric,email,phone,residence,addz,nationality;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        //button to go to edit screen
        final Button button = (Button) root.findViewById(R.id.profileButton);
        intent = new Intent(getActivity(), UserProfileEditActivity.class);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        retrieveUserDate();
    }

    public void retrieveUserDate()
    {
       //connect XML to JAVA
        username=(TextView)getView().findViewById(R.id.usernameNull);
        firstname=(TextView)getView().findViewById(R.id.firstnameNull);
        lastname=(TextView)getView().findViewById(R.id.lastnameNull);
        dob=(TextView)getView().findViewById(R.id.dobNull);
        nric=(TextView)getView().findViewById(R.id.nricNull);
        email=(TextView)getView().findViewById(R.id.emailNull);
        phone=(TextView)getView().findViewById(R.id.phoneNull);
        residence=(TextView)getView().findViewById(R.id.residenceNull);
        addz=(TextView)getView().findViewById(R.id.addzNull);
        nationality=(TextView)getView().findViewById(R.id.nationalityNull);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser!=null)
        {
            firstname.setText(UserInfo.getFirstName());
            lastname.setText(UserInfo.getLastName());
            phone.setText(UserInfo.getPhoneNO());
            nric.setText(UserInfo.getNRIC());
            username.setText(UserInfo.getUserName());
            email.setText(UserInfo.getEmail());
            residence.setText(UserInfo.getResidence());
            addz.setText(UserInfo.getAddz());
            nationality.setText(UserInfo.getNationality());
        }
        else
            username.setText("Error");


    }

}