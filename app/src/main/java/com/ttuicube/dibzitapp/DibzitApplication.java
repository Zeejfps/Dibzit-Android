package com.ttuicube.dibzitapp;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by zeejfps on 10/10/2017.
 */

public class DibzitApplication extends Application {

    private static DibzitApplication _instance;

    public static DibzitApplication instance() {
        return _instance;
    }

    private DibzitRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        _instance = this;
        repository = new DibzitRepository(this);
    }

    public DibzitRepository getRepository() {
        return repository;
    }

}
