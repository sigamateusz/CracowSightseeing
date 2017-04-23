package com.example.cj.cracowsightseeing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import static com.example.cj.cracowsightseeing.LeaderBoard.jsonObject;

public class Menu extends Activity implements View.OnClickListener {

    Button mapBt;
    Button beacon;
    Button leaderboard;
    Button logout;

    TextView playerLevel;
    TextView experience;
    TextView nick;

    String maps_url = "http://192.170.21.107:5000/android/map";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        playerLevel = (TextView) findViewById(R.id.playerLevel);
        experience = (TextView) findViewById(R.id.experience);
        nick = (TextView) findViewById(R.id.nick);

        Log.i("Name: ", ApplicationController.user.getName());
        playerLevel.setText(Integer.toString(ApplicationController.user.getLevel()));
        experience.setText(Integer.toString(ApplicationController.user.getScore()));
        nick.setText(ApplicationController.user.getName());

        mapBt = (Button) findViewById(R.id.mapBt);
        beacon = (Button) findViewById(R.id.beacon);
        leaderboard = (Button) findViewById(R.id.leaderboard);
        logout = (Button) findViewById(R.id.logout);

        mapBt.setOnClickListener(this);
        beacon.setOnClickListener(this);
        leaderboard.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mapBt:
                try {
                    JSONObject userObj = new JSONObject().put("nick", ApplicationController.user.getName());
                    getMapLocations(userObj);

                    } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.leaderboard:
                getBoard();
                break;
            case R.id.beacon:
                startActivity(new Intent("Beacon"));
                break;
            case R.id.logout:
                ApplicationController.user = new User();
                System.exit(0);
//                startActivity(new Intent("MainActivity"));
                break;
        }
    }

    public void getBoard() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                LeaderBoard.server_url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response: ", response.toString());
                        jsonObject = response;
                        startActivity(new Intent("LeaderBoard"));

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

    private void getMapLocations(JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                maps_url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        MapsActivity.locations = response;
                        startActivity(new Intent("MapsActivity"));

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

}
