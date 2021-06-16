package com.rushab.demotestapp.helper;

import com.android.volley.VolleyError;


public interface VolleyHelper {
    public void notifySuccess(String requestType, String response, String Key);
    public void notifyError(String requestType, VolleyError error);
}
