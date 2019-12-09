package com.cookandroid.pocketlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditActivity extends Activity {

    int edit;
    int position;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    EditText name, info;
    int listCnt; // List의 하위에 있는 list 개수를 구하여 +1을 해줌. 새로 생성할 child의 이름에 넣어주면 child의 이름이 순서대로 생성됨. (ex. List1, List2 ...)

    int pictureId = 0;
    int colorCnt;
    String picturePaths[] = {
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration01.png?alt=media&token=29e2c993-126c-4142-b644-fdda5ef14dad",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration02.jpg?alt=media&token=8c384fb1-2d44-40e4-9047-47b8a725f225",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration03.jpg?alt=media&token=d393c401-8cea-4bb3-ae74-17dc47e41ba4",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration04.png?alt=media&token=bccd416a-c882-4480-a734-f3b7369ff29f",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration05.png?alt=media&token=5f467d3d-f915-421d-ac95-480173b783af",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration06.jpg?alt=media&token=75ade0a1-90dd-4e40-af74-ff559814df97",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration07.png?alt=media&token=11b15314-9128-4915-9096-b2ad82f63f6c",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration08.png?alt=media&token=2b62c223-d58a-402e-aaa0-a5004c5d8318",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration09.png?alt=media&token=dfad3dbd-7e40-4609-b82e-e733a617fbc5",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration10.png?alt=media&token=13eeee13-edfe-4429-8366-b96f174e0a01",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration11.png?alt=media&token=f7de5920-90ad-4229-bc84-eae065c6a981",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/illustration12.png?alt=media&token=6cacce0c-7be0-472b-89ce-e594302bea17"
    };
    String colorPaths[] = {
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/color01.png?alt=media&token=3df661bc-7e40-434c-ba10-f98a81e924a6",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/color02.png?alt=media&token=1eba79ad-da84-44e9-af65-e3c56392b2d8",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/color03.png?alt=media&token=aab4f0c8-8a8a-4728-b5a5-a53b0ef803eb",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/color04.png?alt=media&token=7906a458-33d2-4fa1-99ca-1326a61a2322",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/color05.png?alt=media&token=1793ccf6-e38b-43ab-863a-39fac48be8e4",
            "https://firebasestorage.googleapis.com/v0/b/pocket-list-52f18.appspot.com/o/color06.png?alt=media&token=fc0642e7-edf5-4087-9817-f4d9aa834ba3"
    };
    String picturePath;

    ConstraintLayout biglayout;

    ImageView photo;
    Button photoDeleteBtn;
    Intent pictureIntent;

    int starCnt = 1;
    ImageButton star1, star2, star3, star4, star5;

    int dateBtnState = 0;
    TextView date;
    ImageButton dateBtn;
    DatePickerDialog datePickerDialog;
    FloatingActionButton cancelBtn, saveBtn;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        /* DB에 저장되어 있는 listCnt, colorCnt 값 가져오기 */
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCnt = dataSnapshot.getValue(ListCnt.class).getListCnt() + 1;
                colorCnt = dataSnapshot.getValue(ColorCnt.class).getColorCnt();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /* DB에 저장되어 있는 listCnt, colorCnt 값 가져오기 */

        Intent intent = getIntent();
        edit = intent.getIntExtra("edit", 0);

        if (edit == 1) { // 수정인 경우
            position = intent.getIntExtra("position", 0);

            databaseReference.child("List").child("List" + String.valueOf(position)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List listBackUp = dataSnapshot.getValue(List.class);

                    name.setText(listBackUp.getName());
                    info.setText(listBackUp.getInfo());
                    for (int i = 1; i < listBackUp.getStar(); i++) {
                        if (i == 2)
                            star2.setImageResource(R.drawable.star);
                        if (i == 3)
                            star3.setImageResource(R.drawable.star);
                        if (i == 4)
                            star4.setImageResource(R.drawable.star);
                        if (i == 5)
                            star5.setImageResource(R.drawable.star);
                    }
                    date.setText(listBackUp.getDate());

                    pictureId = listBackUp.getPictureid();
                    PictureSet(pictureId);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        name = (EditText) findViewById(R.id.name);
        info = (EditText) findViewById(R.id.info);

        /* 사진 선책하기 */
        photo = (ImageView) findViewById(R.id.photo);
        photoDeleteBtn = (Button) findViewById(R.id.photoDeleteBtn);

        photo.setBackground(new ShapeDrawable(new OvalShape()));
        photo.setClipToOutline(true);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureIntent = new Intent(EditActivity.this, PictureActivity.class);
                startActivityForResult(pictureIntent, 0);
            }
        });

        photoDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureId = 0;
                photo.setImageResource(R.drawable.picture);
            }
        });
        /* 사진 선택하기 */

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

        mindate.set(year, monthOfYear, dayOfMonth);

        date = (TextView) findViewById(R.id.date);
        dateBtn = (ImageButton) findViewById(R.id.dateBtn);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dateBtnState == 0) { // 날짜가 설정되어 있지 않을 때 (설정)
                    // datdtimepicker dialog를 열어줄 때는 키패드를 닫아줌
                    if (getCurrentFocus() != null) { // 만약 키패드가 열려있다면
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    datePickerDialog = new DatePickerDialog(EditActivity.this, R.style.DialogStyle, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateBtnState = 1;
                            date.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                            dateBtn.setRotation(45);
                        }
                    }, year, monthOfYear, dayOfMonth); // 기본 날짜를 오늘로 설정해줌
                    datePickerDialog.getDatePicker().setMinDate(mindate.getTime().getTime()); // 오늘 이전 날짜는 선택하지 못하도록 함
                    datePickerDialog.show();
                } else { // 날짜가 설정되어 있을 때 (취소)
                    dateBtnState = 0;
                    date.setText("");
                    dateBtn.setRotation(0);
                }
            }
        });
        /* DatePicker */

        /* FloatingActionButton */
        cancelBtn = (FloatingActionButton) findViewById(R.id.cancelBtn);
        saveBtn = (FloatingActionButton) findViewById(R.id.saveBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(1, "경고", "종료하시겠습니까?\n종료하시면 입력하셨던 내용이 저장되지 않습니다.");
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 필수 입력 정보를 입력했는지 학인 (버킷명)
                if (name.getText().toString().trim().length() == 0) {
                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(200);

                    showMessage(2, "실패", "버킷명을 입력해주세요.");
                } else {
                    // 사진 링크 설정
                    if (1 <= pictureId && pictureId <= 12) {
                        picturePath = picturePaths[pictureId - 1];
                    } else { // 사진을 선택하지 않으면 6가지 색상 배경을 Defalut 사진으로 지정해줌
                        picturePath = colorPaths[colorCnt];

                        colorCnt++;
                        if (colorCnt >= 6)
                            colorCnt = 0;
                        databaseReference.child("colorCnt").setValue(colorCnt);
                    }

                    if(edit == 0){
                        //.trim() 좌우 공백 문자 제거
                        List list = new List(pictureId, picturePath, name.getText().toString().trim(), info.getText().toString().trim(), starCnt, date.getText().toString().trim(), 0, listCnt);
                        databaseReference.child("List").child("List" + listCnt).setValue(list);

                        databaseReference.child("listCnt").setValue(listCnt);
                    }
                    else{
                        Map<String, Object> hopperUpdates = new HashMap<>();
                        hopperUpdates.put("pictureid", pictureId);
                        hopperUpdates.put("picture", picturePath);
                        hopperUpdates.put("name", name.getText().toString().trim());
                        hopperUpdates.put("info", info.getText().toString().trim());
                        hopperUpdates.put("star", starCnt);
                        hopperUpdates.put("date", date.getText().toString().trim());

                        databaseReference.child("List").child("List" + String.valueOf(position)).updateChildren(hopperUpdates);
                    }

                    finish();
                    overridePendingTransition(R.anim.slide_not, R.anim.slide_up);
                }
            }
        });

        /* 빈 공간 터치하면 키패드 숨기기 */
        biglayout = (ConstraintLayout) findViewById(R.id.biglayout);
        biglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) { // 만약 키패드가 열려있다면
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
        /* 빈 공간 터치하면 키패드 숨기기 */
    }

    public void showMessage(final int id, String title, String msg) { // 알림 띄우기
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.drawable.danger);

        if (id == 1) {
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    overridePendingTransition(R.anim.slide_not, R.anim.slide_up);
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        } else if (id == 2) {
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //데이터 받기

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("intData", 1);
                pictureId = result;

                PictureSet(pictureId);
            }
        }
    }

    private void PictureSet(int pictureId) {
        switch (pictureId) {
            case 1:
                photo.setImageResource(R.drawable.illustration01);
                break;
            case 2:
                photo.setImageResource(R.drawable.illustration02);
                break;
            case 3:
                photo.setImageResource(R.drawable.illustration03);
                break;
            case 4:
                photo.setImageResource(R.drawable.illustration04);
                break;
            case 5:
                photo.setImageResource(R.drawable.illustration05);
                break;
            case 6:
                photo.setImageResource(R.drawable.illustration06);
                break;
            case 7:
                photo.setImageResource(R.drawable.illustration07);
                break;
            case 8:
                photo.setImageResource(R.drawable.illustration08);
                break;
            case 9:
                photo.setImageResource(R.drawable.illustration09);
                break;
            case 10:
                photo.setImageResource(R.drawable.illustration10);
                break;
            case 11:
                photo.setImageResource(R.drawable.illustration11);
                break;
            case 12:
                photo.setImageResource(R.drawable.illustration12);
                break;
        }
    }
}