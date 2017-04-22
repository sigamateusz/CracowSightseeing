package com.example.cj.cracowsightseeing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    private void buttonClick() {
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
