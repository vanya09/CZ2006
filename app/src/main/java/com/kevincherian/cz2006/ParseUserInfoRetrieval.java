package com.kevincherian.cz2006;

import com.parse.ParseUser;

/**
 * Created by hp on 3/24/2015.
 */
public class ParseUserInfoRetrieval implements UserInfoRetrievalInterface
{
    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    public String getUserName() {
        return((String)currentUser.getUsername());
    }

    @Override
    public String getFirstName() {
        return((String)currentUser.get("first_name"));
    }

    @Override
    public String getLastName() {
        return((String)currentUser.get("last_name"));
    }

    @Override
    public String getPhoneNO() {
        return((String)currentUser.get("phone"));
    }

    @Override
    public String getNRIC() {
        return((String)currentUser.get("nric"));
    }

    @Override
    public String getEmail() {
        return((String)currentUser.getEmail());
    }

    @Override
    public String getResidence() {
        return((String)currentUser.get("country"));
    }

    @Override
    public String getAddz() {
        return((String)currentUser.get("address"));
    }

    @Override
    public String getNationality() {
        return((String)currentUser.get("nationality"));
    }

}
