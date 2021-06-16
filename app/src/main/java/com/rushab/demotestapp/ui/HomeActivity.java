package com.rushab.demotestapp.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.rushab.demotestapp.R;
import com.rushab.demotestapp.adapter.UserAdpater;
import com.rushab.demotestapp.helper.ApiHandler;
import com.rushab.demotestapp.helper.Constants;
import com.rushab.demotestapp.helper.SharePrefs;
import com.rushab.demotestapp.helper.VolleyHelper;
import com.rushab.demotestapp.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    private static final String TAG = "HomeActivity";
    RecyclerView rcvUsers;
    Button cbLogout, cbShowLocation;
    private LinearLayoutManager linearLayoutManager;
    private UserAdpater adapter;
    private List<Users> userlist;
    private ProgressDialog progressDialog;
    ApiHandler mVolleyService;
    VolleyHelper mResultCallback = null;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initlayout();

        mResultCallback = new VolleyHelper() {
            @Override
            public void notifySuccess(String requestType, String response, String Key) {

                Log.d(TAG, "response: " + response);

                progressDialog.dismiss();

                if (Key.equals(Constants.HOME)) {
                    Constants.showToast("Success", HomeActivity.this);

                    try {
                        JSONObject response_main = new JSONObject(response);
                        JSONArray data = response_main.getJSONArray("data");
                        Gson gson = new Gson();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);

                            Users user = gson.fromJson(jsonObject.toString(), Users.class);
                            userlist.add(user);

                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                progressDialog.dismiss();
                Constants.showToast("Fail", HomeActivity.this);
            }
        };

        mVolleyService = new ApiHandler(mResultCallback, this);

        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.searchBar);

         searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search People");
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);

        return true;
    }

    private void initlayout() {
        rcvUsers = findViewById(R.id.rcvUsers);
        cbShowLocation = findViewById(R.id.cbShowLocation);
        cbLogout = findViewById(R.id.cbLogout);
        progressDialog = new ProgressDialog(this);
        userlist = new ArrayList<>();

        adapter = new UserAdpater(HomeActivity.this, userlist);
        linearLayoutManager = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(linearLayoutManager);
        rcvUsers.setAdapter(adapter);

        cbLogout.setOnClickListener(this);
        cbShowLocation.setOnClickListener(this);
    }

    private void getData() {
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();

        mVolleyService.getJsonRequestVolley(Constants.GETCALL, Constants.DOMAIN + "" + Constants.HOME, Constants.HOME);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Constants.showToast("Query Inserted", HomeActivity.this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        Log.d(TAG, "onQueryTextChange: " + newText);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
            exitByBackKey();
        }

    }

    protected void exitByBackKey() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.app_exit_msg))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        HomeActivity.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //DoNothing
                    }
                })
                .setCancelable(false)
                .show();
    }


    public void logout() {
        SharePrefs.ClearSharePref();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbLogout:
                logout();
                break;

            case R.id.cbShowLocation:
                startActivity(new Intent(this, MapsActivity.class));
                break;
        }
    }
}