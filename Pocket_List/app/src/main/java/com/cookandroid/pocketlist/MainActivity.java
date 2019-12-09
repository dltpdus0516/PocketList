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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements CustomAdapter.OnListListener {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    int sort = 0;
    int showCompl;
    String orderby;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<List> arrayList;

    LinearLayout navigationView, nvedit, nvcheck, nvbin, stars, completes;
    FloatingActionButton closeBtn;

    FloatingActionButton editBtn;
    ImageButton settingBtn;
    Intent settingIntent, editIntent;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Recycler View */
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 ArrayList (Adapter 쪽으로)

        adapter = new CustomAdapter(arrayList, this, this);
        recyclerView.setAdapter(adapter); // RecyclerView에 Adapter 연결
        /* RecyclerVIew */

        /* DB에 저장되어 있는 setting 값 가져오기 */
        database = FirebaseDatabase.getInstance(); // 파이어베이스 DB 연결
        databaseReference = database.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Setting setting = dataSnapshot.child("Setting").getValue(Setting.class);
                sort = setting.getSort();
                showCompl = setting.getShowCompl();

                if (sort == 0) {
                    orderby = "name";
                } else {
                    orderby = "star";
                }

                databaseReference.child("List").orderByChild(orderby).addValueEventListener( new ValueEventListener() { //database read
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // 파이어베이스 DB의 데이터를 받아옴
                        arrayList.clear(); //초기화

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // 반복문으로 데이터 List를 추출해냄
                            List list = snapshot.getValue(List.class); // 만들어뒀던 List 객체에 데이터를 담는다.

                            if(showCompl == 0){ // 만약 완료 항목 보여주기가 꺼져있다면
                                if(list.getComplete() == 0){ // 완료하지 않은 항목만 list에 arrayList에 추가해라
                                    arrayList.add(list); // 담은 데이터들을 arrayList에 넣고 RecyclerView로 보낼 준비
                                }
                            }
                            else{
                                arrayList.add(list);
                            }
                        }
                        adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // DB를 가져오다 에러 발생시
                        Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /* DB에 저장되어 있는 setting 값 가져오기 */

        /* 환경설정 버튼 누르면 SettingActivity를 불러옴 */
        settingBtn = (ImageButton)findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);
            }
        });
        /* 환경설정 버튼 누르면 SettingActivity를 불러옴 */

        /* 추가 버튼을 누르면 EditActivity를 불러옴 */
        editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editIntent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(editIntent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_not);
            }
        });
        /* 추가 버튼을 누르면 EditActivity를 불러옴 */
    }

    @Override
    public void onListClick(final int position) {
        closeBtn = (FloatingActionButton)findViewById(R.id.closeBtn);
        navigationView = (LinearLayout)findViewById(R.id.navigationView);
        nvedit = (LinearLayout) findViewById(R.id.nvedit) ;
        nvcheck = (LinearLayout) findViewById(R.id.nvcheck);
        nvbin = (LinearLayout) findViewById(R.id.nvbin);

        closeBtn.setVisibility(View.VISIBLE);
        navigationView.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.GONE);

        nvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 편집
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        nvcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 완료
                Map<String, Object> hopperUpdates = new HashMap<>();
                hopperUpdates.put("complete", 1);

                num = arrayList.get(position).getNum();

                databaseReference.child("List").child("List" + String.valueOf(num)).updateChildren(hopperUpdates);
            }
        });

        nvbin.setOnClickListener(new View.OnClickListener() { // 삭제
            @Override
            public void onClick(View v) {
                num = arrayList.get(position).getNum();

                databaseReference.child("List").child("List" + String.valueOf(num)).removeValue();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBtn.setVisibility(View.GONE);
                navigationView.setVisibility(View.GONE);
                editBtn.setVisibility(View.VISIBLE);
            }
        });
    }
}
