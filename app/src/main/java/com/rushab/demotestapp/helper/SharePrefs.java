package com.rushab.demotestapp.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePrefs {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static SharePrefs sharePrefs;

    public static final String SHARED_PREF_NAME = "PDCUSTOMER_PREF";


    public static SharePrefs getInstance() {
        if (sharePrefs == null) {
            sharePrefs = new SharePrefs();
        }
        return sharePrefs;
    }

    public static void ClearSharePref() {
        sharedPreferences.edit().clear().commit();
    }

    public void InitizeSharePref(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getSharePrefStringValue(String key, String defaultValue) {
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString(key, defaultValue);
    }

    public static String getSharePrefStringValue(String key) {
        if (sharedPreferences == null) {
            return "";
        }
        String value = sharedPreferences.getString(key, "");

        return sharedPreferences.getString(key, "");

    }


    public static void savesharePrefBooleanValue(String key, Boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getSharePrefBooleanValue(String key) {
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean(key, false);
    }

    public static Boolean getSharePrefBooleanValue(String key, Boolean defaultvalue) {
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean(key, defaultvalue);
    }

    public static void savesharePrefStringValue(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void savesharePrefIntValue(String key, int value) {
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static int getSharePrefIntValue(String key, int defaultvalue) {
        if (sharedPreferences == null) {
            return 0;
        }
        return sharedPreferences.getInt(key, defaultvalue);
    }
}
