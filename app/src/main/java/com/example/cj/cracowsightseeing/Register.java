package com.example.cj.cracowsightseeing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends Activity implements View.OnClickListener {
    Button button;
    private EditText login;
    private EditText password;
    private EditText password_copy;
    String server_url = "http://192.170.21.107:5000/android/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        button = (Button) findViewById(R.id.registerBt);
        login = (EditText) findViewById(R.id.login_register);
        password = (EditText) findViewById(R.id.password_first);
        password_copy = (EditText) findViewById(R.id.password_copy);

        button.setOnClickListener(this);
//        checkPermissionAndStart();
//        Beacon.checkPermissionAndStart();
    }

    private void buttonClick() {
        String out_login = login.getText().toString();
        String out_password = password.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login", out_login);
            jsonObject.put("password", out_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeJsonObjReq(jsonObject);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBt:
                if (isCorrectPassword()) {
                    buttonClick();
                } else {
                    Toast.makeText(this, "Passwords must be the same", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    boolean isCorrectPassword(){
        return password.getText().toString().equals(password_copy.getText().toString());
    }

    private void makeJsonObjReq(JSONObject jsonObject) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                server_url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("status")) {
                                Log.i("response", response.toString());
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Register.this, "Wrong name or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error: ", error.getMessage());

                //todo : Error template
                //        startActivity(new Intent("MapsActivity"));

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("un", "xyz@gmail.com");
                params.put("p", "somepasswordhere");
                return params;
            }

        };

        String isNull = ApplicationController.getInstance() == null ? " jest nullem" : "jest ok";
        Log.d("aa", "makeJsonObjReq: " + isNull);
        ApplicationController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void checkPermissionAndStart() {
        int checkSelfPermissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);

        if (PackageManager.PERMISSION_GRANTED == checkSelfPermissionResult) {
            Log.i("checkPermission: ", "zaakceptowane");
            //already granted
//            startScanning();
        } else {
            //request permission
            Log.i("checkPermission:", "niezaakpcetowane");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//            System.exit(0);
        }
    }
}
