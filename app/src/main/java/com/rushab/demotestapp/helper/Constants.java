package com.rushab.demotestapp.helper;

import android.content.Context;
import android.widget.Toast;

public class Constants {

    public static final String POSTCALL = "post";
    public static final String GETCALL = "get";
    public static final String TOKEN = "token";

    //APIS
    public static final String DOMAIN = "https://reqres.in/api/";
    public static final String LOGIN = "login";
    public static final String HOME = "users?page=2";

    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

}
