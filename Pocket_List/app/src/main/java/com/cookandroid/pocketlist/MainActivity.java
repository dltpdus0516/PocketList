package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout mainlayout, navigationView, nvedit, nvcheck, nvbin;
    FloatingActionButton editBtn;
    ImageButton settingBtn;
    Intent settingIntent, editIntent;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<List> arrayList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 리사이클 뷰 */
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 ArrayList (Adapter 쪽으로)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 DB 연결

        databaseReference = database.getReference("List");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() { //database read
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 DB의 데이터를 받아옴
                arrayList.clear(); //초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // 반복문으로 데이터 List를 추출해냄
                    List list = snapshot.getValue(List.class); // 만들어뒀던 List 객체에 데이터를 담는다.
                    arrayList.add(list); // 담은 데이터들을 arrayList에 넣고 RecyclerView로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오다 에러 발생시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // RecyclerView에 Adapter 연결
        /* 리사이클 뷰 */

        mainlayout = (LinearLayout)findViewById(R.id.mainlayout);
        navigationView = (LinearLayout)findViewById(R.id.navigationView);
        nvedit = (LinearLayout) findViewById(R.id.nvedit) ;
        nvcheck = (LinearLayout) findViewById(R.id.nvcheck);
        nvbin = (LinearLayout) findViewById(R.id.nvbin);

        mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.setVisibility(View.GONE);
            }
        });

//        nvedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        // 환경설정 버튼 누르면 SettingActivity를 불러옴
        settingBtn = (ImageButton)findViewById(R.id.settingBtn);
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
