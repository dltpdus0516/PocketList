package com.cookandroid.pocketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class PictureActivity extends Activity {

    Intent mainIntent;
    ImageView picture1, picture2, picture3, picture4, picture5, picture6, picture7, picture8, picture9, picture10, picture11, picture12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // ActionBar 숨기기
        setContentView(R.layout.activity_picture);

        picture1 = (ImageView) findViewById(R.id.illustration01);
        picture2 = (ImageView) findViewById(R.id.illustration02);
        picture3 = (ImageView) findViewById(R.id.illustration03);
        picture4 = (ImageView) findViewById(R.id.illustration04);
        picture5 = (ImageView) findViewById(R.id.illustration05);
        picture6 = (ImageView) findViewById(R.id.illustration06);
        picture7 = (ImageView) findViewById(R.id.illustration07);
        picture8 = (ImageView) findViewById(R.id.illustration08);
        picture9 = (ImageView) findViewById(R.id.illustration09);
        picture10 = (ImageView) findViewById(R.id.illustration10);
        picture11 = (ImageView) findViewById(R.id.illustration11);
        picture12 = (ImageView) findViewById(R.id.illustration12);

        picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(1);
            }
        });

        picture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(2);
            }
        });

        picture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(3);
            }
        });

        picture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(4);
            }
        });

        picture5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(5);
            }
        });

        picture6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(6);
            }
        });

        picture7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(7);
            }
        });

        picture8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(8);
            }
        });

        picture9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(9);
            }
        });

        picture10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(10);
            }
        });

        picture11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(11);
            }
        });

        picture12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFunc(12);
            }
        });
    }

    private void returnFunc(int i) {
        mainIntent = new Intent(getApplicationContext(), EditActivity.class);
        mainIntent.putExtra("intData", i);
        setResult(RESULT_OK, mainIntent);

        finish();
    }

    /* 레이아웃 밖을 선택했을 때 레이아웃이 닫히지 않도록 함
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    } */
}