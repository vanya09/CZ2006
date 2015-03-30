package com.kevincherian.cz2006;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

public class cz2006Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "DGYQgVsVtXfWyxiHafBTMFgnba2ZSZUiZipKfS0M", "aAM0cTwVtu6HusEuHnFA3C534XwlB1UUKK4AhZtj");


    }
}