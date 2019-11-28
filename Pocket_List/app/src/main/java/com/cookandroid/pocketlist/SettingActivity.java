
package com.cookandroid.pocketlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends Activity {

    int toastStart = 0;
    String[] spinnerItems = {"버킷명 오름차순", "버킷명 내림차순", "중요도 오름차순", "중요도 내림차순"};
    ImageButton backBtn;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // spinner
        spinner = (Spinner)findViewById(R.id.spinner); //spinner 선언
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter); //스피너에 어댑터를 넣어줌

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(toastStart != 0) {
                    Toast.makeText(getApplicationContext(), spinnerItems[position], Toast.LENGTH_LONG).show();
                }
                toastStart = 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        // spinner

        // 뒤로가기 버튼누르면 MainActivity를 다시 불러옴
        backBtn = (ImageButton)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}