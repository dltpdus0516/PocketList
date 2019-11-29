
package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {

    int toastStart = 0;
    String[] spinnerItems = {"버킷명 오름차순", "버킷명 내림차순", "중요도 오름차순", "중요도 내림차순"};
    ImageButton backBtn;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;
    Switch swShow;
    int swShowSave = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        /* spinner */
        spinner = (Spinner)findViewById(R.id.spinner); //spinner 선언
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter); //스피너에 어댑터를 넣어줌

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(toastStart != 0) {
                    ToastCustom(spinnerItems[position]);
                }
                toastStart = 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        /* spinner */

        /* Switch */
        swShow = (Switch)findViewById(R.id.swShow);

        if(swShowSave == 0){
            swShow.setChecked(true);
        }

        swShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(swShow.isChecked()){
                    ToastCustom("완료 목록이 표시됩니다.");
                    swShowSave = 0;
                }
                else{
                    ToastCustom("완료 목록이 표시되지 않습니다.");
                    swShowSave = 1;
                }
            }
        });
        /* Switch */

        // 뒤로가기 버튼누르면 MainActivity를 다시 불러옴
        backBtn = (ImageButton)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /* Toast 커스텀 */
    public void ToastCustom(String word){
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.toastborder, (ViewGroup)findViewById(R.id.toast_layout_root));
        TextView text = layout.findViewById(R.id.text);

        Toast toast = new Toast(SettingActivity.this);
        text.setText(word);
        toast.setGravity(Gravity.CENTER, 0, 600);
        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);
        toast.show();
    }
    /* Toast 커스텀 */
}