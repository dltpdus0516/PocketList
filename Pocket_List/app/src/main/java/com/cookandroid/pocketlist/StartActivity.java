package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {

    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Handler hand = new Handler();

        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1000);

    }
}
