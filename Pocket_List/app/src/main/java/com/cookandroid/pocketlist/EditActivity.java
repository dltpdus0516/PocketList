package com.cookandroid.pocketlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EditActivity extends Activity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    EditText name, info;
    int nameOK; // name에 입력된 text가 있는지 확인
    int listCnt; // List의 하위에 있는 list 개수를 구하여 +1을 해줌. 새로 생성할 child의 이름에 넣어주면 child의 이름이 순서대로 생성됨. (ex. List01, List02 ...)
    String slistCnt; // listCnt의 값을 string으로 변환하여 저장 (만약 10이하면 0을 붙여줌)
    String picturePath;
    int pictureId = 0;

    LinearLayout biglayout;

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

        name = (EditText)findViewById(R.id.name);
        info = (EditText)findViewById(R.id.info);

        /* database write */
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("List");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCnt = (int)dataSnapshot.getChildrenCount() + 1; // 현재 데이터베이스에 있는 데이터 개수 (List의 child 개수) + 1

                if(listCnt < 10){
                    slistCnt = "0" + String.valueOf(listCnt);
                }
                else{
                    slistCnt = String.valueOf(listCnt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오다 에러 발생시
                Log.e("EditActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
        /* database write */

        /* 사진 가져오기 */
        photo = (ImageView) findViewById(R.id.photo);
        photoDeleteBtn = (Button)findViewById(R.id.photoDeleteBtn);

        photo.setBackground(new ShapeDrawable(new OvalShape()));
        photo.setClipToOutline(true);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // layout 띄움
            }
        });

        photoDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureId = 0;
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
            public void onClick(View v) { // 선택한 별 전의 별도 모두 star로 바꾸어줌.
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
        final int year, monthOfYear, dayOfMonth;

        Calendar calendar = new GregorianCalendar(Locale.KOREA); // 한국의 날짜 정보를 받아옴.
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
                    // datdtimepicker dialog를 열어줄 때는 키패드를 닫아줌
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
                    },year, monthOfYear, dayOfMonth); // 기본 날짜를 오늘로 설정해줌
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
                showMessage(1, "경고", "종료하시겠습니까?\n종료하시면 입력하셨던 내용이 저장되지 않습니다.");
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nameOK = 0;
                // 필수 입력 정보를 입력했는지 학인 (버킷명)
                if(name.getText().toString().length() == 0){
                    showMessage(2, "실패", "버킷명을 입력해주세요.");
                    nameOK = 1;
                }

                switch (pictureId){
                    case 1:
                        picturePath = "";
                        break;
                    case 2:
                        picturePath = "";
                        break;
                    case 3:
                        picturePath = "";
                        break;
                    case 4:
                        picturePath = "";
                        break;
                    case 5:
                        picturePath = "";
                        break;
                    case 6:
                        picturePath = "";
                        break;
                    case 7:
                        picturePath = "";
                        break;
                    case 8:
                        picturePath = "";
                        break;
                    case 9:
                        picturePath = "";
                        break;
                    case 10:
                        picturePath = "";
                        break;
                    case 11:
                        picturePath = "";
                        break;
                    case 12:
                        picturePath = "";
                        break;
                    default:
                        picturePath = ""; // 기본 사진
                        break;
                }

                List list = new List(picturePath, name.getText().toString(), info.getText().toString(), starCnt, date.getText().toString());
                databaseReference.child("List" + slistCnt).setValue(list);

                if(nameOK != 1){
                    finish();
                    overridePendingTransition(R.anim.slide_not, R.anim.slide_up);
                }
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

    public void showMessage(final int id, String title, String msg){ // 알림 띄우기
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.drawable.danger);

        if (id == 1) {
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
        }
        else if (id == 2){
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
