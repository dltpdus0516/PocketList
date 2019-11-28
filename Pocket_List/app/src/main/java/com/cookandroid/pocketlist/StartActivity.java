package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {

    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mainIntent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
