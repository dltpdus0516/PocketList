package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    FloatingActionButton editBtn;
    ImageButton settingBtn;
    Intent settingIntent, editIntent;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ListAdapter adapter;
    LinearLayout mainlayout, navigationView, nvedit, nvcheck, nvbin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        nvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        /* 리사이클 뷰 */
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter();

        adapter.addItem(new List("자퇴하기", "세연이 바보", 3, "2019년 12월 03일"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(ListAdapter.ViewHolder holder, View view, int position) {
                List item = adapter.getItem(position);

                navigationView.setVisibility(View.VISIBLE);
                // ******************************요기에서 편집/완료/삭제 선택하는거 튀어나오게 하면 됨 ******************************
            }
        });
        /* 리사이클 뷰 */

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
