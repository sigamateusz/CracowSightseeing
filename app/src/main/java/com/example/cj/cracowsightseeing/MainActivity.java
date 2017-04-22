package com.example.cj.cracowsightseeing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button;
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        button = (Button) findViewById(R.id.button);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        button.setOnClickListener(this);
    }

    private void buttonClick() {
        String out_login = login.getText().toString();
        String out_password = password.getText().toString();

        Log.i("Login: ", out_login);
        Log.i("Pass: ", out_password);

        startActivity(new Intent("MapsActivity"));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                buttonClick();
                break;
        }
    }
}
