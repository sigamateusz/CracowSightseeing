package com.example.cj.cracowsightseeing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoard extends Activity {

        static String server_url = "http://192.170.21.107:5000/android/leader_board";

        List<TextView> personList = new ArrayList<>();

        static JSONObject jsonObject = new JSONObject();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.leader_board);
            personList.add((TextView) findViewById(R.id.person1));
            personList.add((TextView) findViewById(R.id.person2));
            personList.add((TextView) findViewById(R.id.person3));
            personList.add((TextView) findViewById(R.id.person4));
            personList.add((TextView) findViewById(R.id.person5));
            personList.add((TextView) findViewById(R.id.person6));
            personList.add((TextView) findViewById(R.id.person7));
            personList.add((TextView) findViewById(R.id.person8));
            personList.add((TextView) findViewById(R.id.person9));
            personList.add((TextView) findViewById(R.id.person10));
            setBoard();
        }

    private void setBoard() {

        for (Integer i=1; i<11; i++) {
            Log.i("PERSON", "W FOr");
            Log.i("JSON OBJ", jsonObject.toString());

            if (jsonObject.has("p" + i.toString())) {
                try {
                    Log.i("PERSON", jsonObject.getJSONObject("p" + i.toString()).toString());
                    JSONObject newObject = jsonObject.getJSONObject("p" + i.toString());
                    personList.get(i - 1).setText(i.toString() + ": " + newObject.getString("nick") + "  " + Integer.toString(newObject.getInt("points")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
