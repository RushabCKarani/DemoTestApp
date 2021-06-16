package com.rushab.demotestapp.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiHandler {
    VolleyHelper mResultCallback = null;
    Context mContext;
    private static final String TAG = "ApiHandler";

    public ApiHandler(VolleyHelper resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }

    public void postStringRequestVolley(final String requestType, String url, final Map<String, String> paramsbody,
                                        final String KEY) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            Log.d(TAG, "postDataVolley: url called - " + url);

            StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "postDataVolley: url called - " + response);
                            mResultCallback.notifySuccess(requestType, response, KEY);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //     Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                            mResultCallback.notifyError(requestType, error);
                            Log.d(TAG, "postDataVolley: url called - " + error);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> paramsbody1 = new HashMap<String, String>();
                    paramsbody1 = paramsbody;
                    return paramsbody1;
                }
            };

            queue.add(strRequest);

        } catch (Exception e) {

        }
    }

    public void postStringRequestVolleyWithHeader(final String requestType, String url, final Map<String, String> paramsbody,
                                                  final Map<String, String> paramsheader, final String KEY) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            Log.d(TAG, "postDataVolley: url called - " + url);

            StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "postDataVolley: url called - " + response);
                            mResultCallback.notifySuccess(requestType, response, KEY);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //     Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                            mResultCallback.notifyError(requestType, error);
                            Log.d(TAG, "postDataVolley: url called - " + error);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> paramsbody1 = new HashMap<String, String>();
                    paramsbody1 = paramsbody;
                    return paramsbody1;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> paramsheader1 = new HashMap<String, String>();
                    paramsheader1 = paramsheader;
                    return paramsheader1;
                }
            };

            queue.add(strRequest);

        } catch (Exception e) {

        }
    }

    public void getStringRequestVolleyHeader(final String requestType, String url, final Map<String, String> paramsheader, final String KEY) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            Log.d(TAG, "getStringRequestVolley: url called - " + url);

            StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "getStringRequestVolley: url called - " + response);
                            mResultCallback.notifySuccess(requestType, response, KEY);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //     Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                            mResultCallback.notifyError(requestType, error);
                            Log.d(TAG, "get DataVolley: url called - " + error);
                        }
                    }) {
                /*   @Override
                   protected Map<String, String> getParams() {
                       Map<String, String> params = new HashMap<String, String>();
                       // params = params1;
                       return params;
                   }
   */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params = paramsheader;
                    return params;
                }
            };

            queue.add(strRequest);

        } catch (Exception e) {

        }
    }

    public void getStringRequestVolley(final String requestType, String url, final Map<String, String> paramsdata, final String KEY) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            Log.d(TAG, "getStringRequestVolley: url called - " + url);

            StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "getStringRequestVolley: url called - " + response);
                            mResultCallback.notifySuccess(requestType, response, KEY);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //     Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                            mResultCallback.notifyError(requestType, error);
                            Log.d(TAG, "get DataVolley: url called - " + error);
                        }
                    }) {
              /*  @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params = paramsdata;
                    return params;
                }*/
            };

            queue.add(strRequest);

        } catch (Exception e) {

        }
    }


    public void getJsonRequestVolley(final String requestType, String url, final String KEY) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (mResultCallback != null)
                        Log.d(TAG, "getDataVolley: url called - " + response);
                    mResultCallback.notifySuccess(requestType, response.toString(), KEY);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            });
            queue.add(jsonObj);

        } catch (Exception e) {

        }
    }
}