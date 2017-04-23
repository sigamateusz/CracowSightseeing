package com.example.cj.cracowsightseeing;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Quiz extends Activity implements View.OnClickListener{

    static Integer questionNumber = 1;

    Button ans1;
    Button ans2;
    Button ans3;
    Button ans4;
    TextView question;

    Integer score = 0;

    static JSONObject questions = new JSONObject();

    String server_url = "http://192.170.21.107:5000/android/quiz";
    String set_score_url = "http://192.170.21.107:5000/android/set_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
//        JSONObject questions = new JSONObject();
        ans1 = (Button) findViewById(R.id.ans1);
        ans2 = (Button) findViewById(R.id.ans2);
        ans3 = (Button) findViewById(R.id.ans3);
        ans4 = (Button) findViewById(R.id.ans4);
        question = (TextView) findViewById(R.id.question);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("major", Beacon.beaconsList.get(Beacon.beaconsList.size() - 1));
//            makeJsonObjReq(jsonObject);
////            Beacon.beaconsList.remove(Beacon.beaconsList.size() - 1);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        showQuestion();
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ans1:
                checkAnswer(1);
                break;
            case R.id.ans2:
                checkAnswer(2);
                break;
            case R.id.ans3:
                checkAnswer(3);
                break;
            case R.id.ans4:
                checkAnswer(4);
                break;
        }
        showQuestion();
    }

    void checkAnswer(Integer ans) {
        try {
            JSONObject newQuestion = questions.getJSONObject("question" + Integer.toString(questionNumber));

            if (ans == newQuestion.getInt("correct")) {
                score++;
            }
            questionNumber++;
            if (questionNumber>2) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("score", score);
                sendScore(jsonObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void makeJsonObjReq(final JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                server_url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject1 = response;
                        questions = response ;
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

    void showQuestion() {
        try {
            Log.i("questions", questions.toString());
            Log.i("JESTEM", "TU KURWA");
            JSONObject newQuestion = questions.getJSONObject("question" + Integer.toString(questionNumber));
            ans1.setText(newQuestion.getString("answer1"));
            ans2.setText(newQuestion.getString("answer2"));
            ans3.setText(newQuestion.getString("answer3"));
            ans4.setText(newQuestion.getString("answer4"));
            question.setText(newQuestion.getString("question"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendScore(JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                set_score_url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("status")) {
                                score = 0;
                                questionNumber = 1;
//                                questions = null;
                                startActivity(new Intent("Menu"));
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


}
