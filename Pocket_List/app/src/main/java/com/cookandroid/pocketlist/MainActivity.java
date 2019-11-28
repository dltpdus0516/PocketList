package com.cookandroid.pocketlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
=======

        settingBtn = (ImageButton)findViewById(R.id.settingBtn);

        // 환경설정 버튼 누르면 SettingActivity를 불러옴
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);
            }
        });
>>>>>>> Stashed changes
    }
}
