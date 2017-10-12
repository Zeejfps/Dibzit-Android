package com.ttuicube.dibzitapp;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by zeejfps on 10/10/2017.
 */

public class DibzitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
