package com.example.cj.cracowsightseeing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;

import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Beacon extends AppCompatActivity{

    ImageView ball;
    public static List<Integer> beaconsList = new ArrayList();
    private ProximityManager proximityManager;
    String server_url = "http://192.170.21.107:5000/android/beacon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon);
        ball = (ImageView) findViewById(R.id.ball);
        KontaktSDK.initialize("HrIntLkGdnKSpkrrSJZpesIMXcOkUTht");

        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.setIBeaconListener(createIBeaconListener());
//        proximityManager.setEddystoneListener(createEddystoneListener());

    }

    @Override
    protected void onStart() {
        super.onStart();
        startScanning();
    }

    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                Integer major = ibeacon.getMajor();
                if (!beaconsList.contains(major)) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("major", major);
                        makeJsonObjReq(jsonObject);
                        beaconsList.add(major);
//                        Log.i("Lista beaconow", beaconsList.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void makeJsonObjReq(final JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
            server_url, jsonObject,
            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
//                        Log.i("Status: ", response.getBoolean("status") ? "True" : "False");
                        if (response.getBoolean("status")) {
                            onStop();
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("major", Beacon.beaconsList.get(Beacon.beaconsList.size() - 1));
                            goToQuiz(jsonObject1);
                            startActivity(new Intent("Quiz"));

                            ball.setImageResource(R.drawable.quiz);
                        } else {
                            ball.setImageResource(R.drawable.looking);
                        }
//                        Log.i("Status: ", response.toString());
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

    ApplicationController.getInstance().addToRequestQueue(jsonObjReq);
}
    private void goToQuiz(final JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://192.170.21.107:5000/android/quiz", jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject1 = response;
                        Log.i("respon", jsonObject1.toString());
                        Log.i("DUPA", "DUPA");
                        Quiz.questions = response;
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


}
