package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    FloatingActionButton editBtn;
    ImageButton settingBtn;
    Intent settingIntent, editIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingBtn = (ImageButton)findViewById(R.id.settingBtn);

        // 환경설정 버튼 누르면 SettingActivity를 불러옴
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);

            }
        });

        editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editIntent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(editIntent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_not);
            }
        });
    }
}
