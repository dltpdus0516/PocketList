package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    ImageButton settingBtn;
    Intent settingIntent;

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
    }
}
