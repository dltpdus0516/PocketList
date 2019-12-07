
package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends Activity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    int toastStart = 0;
    String[] spinnerItems = {"버킷명 오름차순", "중요도 내림차순"};
    ImageButton backBtn;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;

    Switch swShow;
    int showCompl;
    int sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        /* DB에 저장되어 있는 setting 값 가져오기 */
        swShow = (Switch)findViewById(R.id.swShow);
        spinner = (Spinner)findViewById(R.id.spinner); //spinner 선언
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter); //스피너에 어댑터를 넣어줌

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Setting");

        databaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Setting setting = dataSnapshot.getValue(Setting.class);
                        sort = setting.getSort();
                        showCompl = setting.getShowCompl();

                        spinner.setSelection(sort);

                        if(showCompl == 1){ // 이전에 설정해놨던 값을 가져와 on/off를 설정함
                            swShow.setChecked(true);
                        }
                        else{
                            swShow.setChecked(false);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        /* DB에 저장되어 있는 setting 값 가져오기 */

        /* Spinner*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(toastStart != 0) { // 맨 처음 layout_setting 페이지를 열었을 때는 toast가 뜨지 않도록 함
                    ToastCustom(spinnerItems[position]);
                }
                else {
                    toastStart = 1;
                }

                sort = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        /* Spinner*/

        /* Switch */
        swShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(swShow.isChecked()){
                    if(toastStart != 0) { // 맨 처음 layout_setting 페이지를 열었을 때는 toast가 뜨지 않도록 함
                        ToastCustom("완료 목록이 표시됩니다.");
                    }

                    showCompl = 1;
                }
                else{
                    if(toastStart != 0) { // 맨 처음 layout_setting 페이지를 열었을 때는 toast가 뜨지 않도록 함
                        ToastCustom("완료 목록이 표시되지 않습니다.");
                    }

                    showCompl = 0;
                }
            }
        });
        /* Switch */

        /* 뒤로가기 버튼누르면 MainActivity를 다시 불러옴 */
        backBtn = (ImageButton)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> hopperUpdates = new HashMap<>();
                hopperUpdates.put("sort", sort);
                hopperUpdates.put("showCompl", showCompl);

                databaseReference.updateChildren(hopperUpdates);

                finish();
            }
        });
        /* 뒤로가기 버튼누르면 MainActivity를 다시 불러옴 */
    }

    /* Toast custom */
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
    /* Toast custom */
}