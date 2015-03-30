package com.kevincherian.cz2006;

import android.content.Context;
import android.os.AsyncTask;


import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Implementation of the user manager using Parse
 * Allows for login and sign up of users
 *
 * @author Vanya
 */
public class UserManager implements UserManagerInterface {

    public void signUp(String firstName, String lastName, String username, String age, String phone, String password, String email, final ResultCallback<Integer> callback) {
        new UserSignUpTask(firstName, lastName, username, age, phone, password, email) {
            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if (callback != null) {
                    callback.onResult(result);
                }
            }
        }.execute();
    }

    public void login(Context context, String username, String password, final ResultCallback<Integer> callback) {
        new UserLoginTask(context, username, password) {
            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if (callback != null) {
                    callback.onResult(result);
                }
            }
        }.execute();
    }

    public void updateProfile(Context context, String userId, String firstName, String lastName, String email, String age, String phone, final ResultCallback<Boolean> callback) {
        new UpdateProfileTask(context, userId, firstName, lastName, email, age, phone) {
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (callback != null) {
                    callback.onResult(result);
                }
            }
        }.execute();
    }

//    public void getUserbyId(Context context, String userId, final ResultCallback<Boolean> callback) {
//        new FetchUserByIdTask(context, userId) {
//            @Override
//            protected void onPostExecute(Boolean result) {
//                super.onPostExecute(result);
//                if (callback != null) {
//                    callback.onResult(result);
//                }
//            }
//        }.execute();
//    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mUsername;
        private final String mPassword;
        private final Context mContext;

        UserLoginTask(Context context, String username, String password) {
            mContext = context;
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                ParseUser.logOut();
                UserHelper.removeCurrentUser(mContext);
                ParseUser user = ParseUser.logIn(mUsername, mPassword);
                if (user != null) {
                    String email = user.getString(DbConstants.USER_EMAIL);
                    String firstName = user.getString(DbConstants.USER_FIRST_NAME);
                    String lastName = user.getString(DbConstants.USER_LAST_NAME);
                    String age = user.getString(DbConstants.USER_AGE);
                    String phone = user.getString(DbConstants.USER_PHONE);
                    
//                    ParseObject company = user.getParseObject(DbConstants.USER_COMPANY_ID);
//                    if (company != null)
//                        company.fetchIfNeeded();
//                    String companyId = company == null ? "" : company.getObjectId();
//                    String companyName = company == null ? "" : company.getString(DbConstants.COMPANY_NAME);

                    User currentUser = new User(user.getObjectId(), mUsername, firstName, lastName, age, phone, email);
                    UserHelper.storeUser(mContext, currentUser);
                    System.out.printf("User: %s, %s, %s, %s, %s, %s, %s, %s\n", user.getObjectId(), mUsername, firstName, lastName, age, phone, email);
                    return 0;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return e.getCode();
            }
            return -1;
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserSignUpTask extends AsyncTask<Void, Void, Integer> {
        private final String mFirstname;
        private final String mLastname;
        private final String mUsername;
        private final String mAge;
        private final String mPhone; 
        private final String mEmail;
        private final String mPassword;
        private int errorCode;

        UserSignUpTask(String firstName, String lastName, String username, String age, String phone, String password, String email) {
            mFirstname = firstName;
            mLastname = lastName;
            mUsername = username;
            mAge = age;
            mEmail = email;
            mPhone= phone; 
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            try {
                ParseUser user = new ParseUser();
                user.setUsername(mUsername);
                user.setPassword(mPassword);
                user.setEmail(mEmail);

                user.put(DbConstants.USER_FIRST_NAME, mFirstname);
                user.put(DbConstants.USER_LAST_NAME, mLastname);
                user.put(DbConstants.USER_AGE, mAge);
                user.put(DbConstants.USER_PHONE, mPhone);

                user.signUp();

                return 0;
            } catch (ParseException e) {
                errorCode = e.getCode();
                e.printStackTrace();
                return errorCode;
            }

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class FetchUserByIdTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserId;
        private final Context mContext;

        FetchUserByIdTask(Context context, String userId) {
            mContext = context;
            mUserId = userId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ParseUser user = ParseUser.getQuery().get(mUserId);

                String username = user.getUsername();
                String email = user.getString(DbConstants.USER_EMAIL);
                String firstName = user.getString(DbConstants.USER_FIRST_NAME);
                String lastName = user.getString(DbConstants.USER_LAST_NAME);
                String age = user.getString(DbConstants.USER_AGE);
                String phone = user.getString(DbConstants.USER_PHONE);
                
//                ParseObject company = user.getParseObject(DbConstants.USER_COMPANY_ID);
//                if (company != null)
//                    company.fetchIfNeeded();
//                String companyId = company == null ? "" : company.getObjectId();
//                String companyName = company == null ? "" : company.getString(DbConstants.COMPANY_NAME);

                User currentUser = new User(user.getObjectId(), username, firstName, lastName, age, phone, email);
                UserHelper.storeUser(mContext, currentUser);
                System.out.printf("User: %s, %s, %s, %s, %s, %s, %s, %s\n", user.getObjectId(), username, firstName, lastName, age, phone, email);
                return true;

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private class UpdateProfileTask extends AsyncTask<Void, Void, Boolean> {
        private final String mFirstname;
        private final String mLastname;
        private final String mUserId;
        private final String mAge;
        private final String mPhone;
        private final String mEmail;
        private final Context mContext;

        UpdateProfileTask(Context context, String userId, String firstName, String lastName, String email, String age, String phone) {
            mContext = context;
            mUserId = userId;
            mFirstname = firstName;
            mLastname = lastName;
            mAge = age;
            mPhone=phone; 
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                ParseUser user = ParseUser.getQuery().get(mUserId);
                user.put(DbConstants.USER_EMAIL, mEmail);
                user.put(DbConstants.USER_FIRST_NAME, mFirstname);
                user.put(DbConstants.USER_LAST_NAME, mLastname);
                user.put(DbConstants.USER_PHONE, mPhone);
                if (mAge != null)
                    user.put(DbConstants.USER_AGE, mAge);
                user.save();

                String username = user.getUsername();
                String email = user.getString(DbConstants.USER_EMAIL);
                String firstName = user.getString(DbConstants.USER_FIRST_NAME);
                String lastName = user.getString(DbConstants.USER_LAST_NAME);
                String age = user.getString(DbConstants.USER_AGE);
                String phone = user.getString(DbConstants.USER_PHONE); 
//                ParseObject company = user.getParseObject(DbConstants.USER_COMPANY_ID);
//                if (company != null)
//                    company.fetchIfNeeded();
//                String companyId = company == null ? "" : company.getObjectId();
//                String companyName = company == null ? "" : company.getString(DbConstants.COMPANY_NAME);

                User currentUser = new User(user.getObjectId(), username, firstName, lastName, age, phone, email);
                UserHelper.storeUser(mContext, currentUser);
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
