package com.ezerski.vladislav.utils.application;

import android.app.Application;
import android.content.Context;

public class ExApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ExApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ExApp.context;
    }
}