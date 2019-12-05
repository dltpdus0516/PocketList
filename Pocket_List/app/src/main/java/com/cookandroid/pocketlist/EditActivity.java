package com.cookandroid.pocketlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EditActivity extends Activity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    LinearLayout biglayout;

    static final int REQUEST_CODE = 0;
    ImageView photo;
    Button photoDeleteBtn;

    int starCnt = 1;
    ImageButton star1, star2, star3, star4, star5;

    int dateBtnState = 0;
    TextView date;
    ImageButton  dateBtn;
    DatePickerDialog datePickerDialog;
    FloatingActionButton cancelBtn, saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        /* database write */
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("List");

        databaseReference.setValue("Hello, World!");
        /* database write */

        /* 사진 가져오기 */
        photo = (ImageView) findViewById(R.id.photo);
        photoDeleteBtn = (Button)findViewById(R.id.photoDeleteBtn);

        photo.setBackground(new ShapeDrawable(new OvalShape()));
        photo.setClipToOutline(true);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        photoDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.setImageResource(R.drawable.picture);
            }
        });
        /* 사진 가져오기 */

        /* 중요도 선택하기 */
        star1 = (ImageButton) findViewById(R.id.star1);
        star2 = (ImageButton) findViewById(R.id.star2);
        star3 = (ImageButton) findViewById(R.id.star3);
        star4 = (ImageButton) findViewById(R.id.star4);
        star5 = (ImageButton) findViewById(R.id.star5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCnt = 1;
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star_empty);
                star3.setImageResource(R.drawable.star_empty);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCnt = 2;
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star_empty);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCnt = 3;
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCnt = 4;
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star_empty);
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCnt = 5;
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star);
            }
        });
        /* 중요도 선택하기 */

        /* DatePicker */
        final int year, monthOfYear, dayOfMonth; // 오늘 날짜

        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        final Calendar mindate = Calendar.getInstance();


        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        mindate.set(year,monthOfYear,dayOfMonth);

        date = (TextView) findViewById(R.id.date);
        dateBtn = (ImageButton)findViewById(R.id.dateBtn);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dateBtnState == 0){ // 날짜가 설정되어 있지 않을 때 (설정)
                    /* 키패드 제어 */
                    if(getCurrentFocus() != null) { // 만약 키패드가 열려있다면
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    datePickerDialog = new DatePickerDialog(EditActivity.this, R.style.DialogStyle, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateBtnState = 1;
                            date.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일");
                            dateBtn.setRotation(45);
                        }
                    },year, monthOfYear, dayOfMonth);
                    datePickerDialog.getDatePicker().setMinDate(mindate.getTime().getTime()); // 오늘 이전 날짜는 선택하지 못하도록 함
                    datePickerDialog.show();
                }
                else{ // 날짜가 설정되어 있을 때 (취소)
                    dateBtnState = 0;
                    date.setText("");
                    dateBtn.setRotation(0);
                }
            }
        });
        /* DatePicker */

        /* FloatingActionButton */
        cancelBtn = (FloatingActionButton)findViewById(R.id.cancelBtn);
        saveBtn = (FloatingActionButton)findViewById(R.id.saveBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_not, R.anim.slide_up);
                // DB에 정보 저장
            }
        });

        /* 빈 공간 터치하면 키패드 숨기기 */
        biglayout = (LinearLayout)findViewById(R.id.biglayout);
        biglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus() != null) { // 만약 키패드가 열려있다면
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
        /* 빈 공간 터치하면 키패드 숨기기 */
    }


    /* 사진 가져오기 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    photo.setImageBitmap(img);

                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
    /* 사진 가져오기 */

    public void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("경고");
        builder.setMessage("종료하시겠습니까?\n종료하시면 입력하셨던 내용이 저장되지 않습니다.");
        builder.setIcon(R.drawable.danger);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                overridePendingTransition(R.anim.slide_not, R.anim.slide_up);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
