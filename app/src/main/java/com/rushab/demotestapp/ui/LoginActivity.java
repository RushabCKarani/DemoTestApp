package com.rushab.demotestapp.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.rushab.demotestapp.R;
import com.rushab.demotestapp.helper.ApiHandler;
import com.rushab.demotestapp.helper.Constants;
import com.rushab.demotestapp.helper.SharePrefs;
import com.rushab.demotestapp.helper.VolleyHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    EditText etPassword, etEmail;
    TextView tvErrorMessage;
    Button cbLogin;
    String email, password;

    private ProgressDialog progressDialog;
    ApiHandler mVolleyService;
    VolleyHelper mResultCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initlayout();

        mResultCallback = new VolleyHelper() {
            @Override
            public void notifySuccess(String requestType, String response, String Key) {

                progressDialog.dismiss();
                if (Key.equals(Constants.LOGIN)) {
                    try {
                        JSONObject response_main = new JSONObject(response);
                        Constants.showToast("Success", LoginActivity.this);
                        SharePrefs.savesharePrefStringValue(Constants.TOKEN, response_main.getString("token"));
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } catch (Exception e) {
                        Constants.showToast(e.getMessage(), LoginActivity.this);
                    }
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                progressDialog.dismiss();
                Constants.showToast("Fail", LoginActivity.this);
            }
        };

        mVolleyService = new ApiHandler(mResultCallback, this);
    }

    private void initlayout() {
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        cbLogin = findViewById(R.id.cbLogin);

        cbLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbLogin:
                if (verify())
                    login(email, password);
                break;
        }
    }

    private void login(String email, String password) {
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();

        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("password", password);

        mVolleyService.postStringRequestVolley(Constants.POSTCALL, Constants.DOMAIN + "" + Constants.LOGIN, params,
                Constants.LOGIN);
    }

    public boolean verify() {
        Boolean value = true;
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (email.equals("")) {
            tvErrorMessage.setText(R.string.error_msg_email);
            value = false;
        } else if (password.equals("")) {
            tvErrorMessage.setText(R.string.error_msg_password);
            value = false;
        }


        return value;
    }
}