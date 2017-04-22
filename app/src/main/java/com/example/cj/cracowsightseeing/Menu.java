package com.example.cj.cracowsightseeing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Menu extends Activity implements View.OnClickListener {
    Button mapBt;
    Button beacon;
    Button leaderboard;
    Button quiz;
    Button logout;

    TextView playerLevel;
    TextView experience;
    TextView nick;

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
        quiz = (Button) findViewById(R.id.quiz);
        logout = (Button) findViewById(R.id.logout);

        mapBt.setOnClickListener(this);
        beacon.setOnClickListener(this);
        leaderboard.setOnClickListener(this);
        quiz.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mapBt:
                startActivity(new Intent("MapsActivity"));
                break;
            case R.id.leaderboard:
                break;
            case R.id.quiz:
                break;
            case R.id.beacon:
                startActivity(new Intent("Beacon"));
                break;
            case R.id.logout:
                break;
        }
    }
}
