package com.rushab.demotestapp;

import android.app.Application;
import android.os.StrictMode;

import com.rushab.demotestapp.helper.SharePrefs;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        SharePrefs.getInstance().InitizeSharePref(this);
    }
}
