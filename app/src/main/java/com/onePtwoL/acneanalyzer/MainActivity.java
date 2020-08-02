package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /* Activity와 연결된 컴포넌트 */
    Button AddPicBtn;
    ImageView DiagnosisImageView, NextImageView;
    LinearLayout NextLinearLayout;

    /* RecyclerView와 관련된 컴포넌트 */
    ArrayList<Skin> skinArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MyAdapter myAdapter;
    int count = 0;

    /* 카메라, 갤러리와 관련된 컴포넌트 */
    DialogForImage dialog;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_PICTURE = 3;
    private static final int PICK_FROM_FILE = 4;

    private static Uri imageUri;
    private String imagePath;
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddPicBtn = findViewById(R.id.add_pic_btn);
        DiagnosisImageView = findViewById(R.id.diag_ImageView);
        NextImageView = findViewById(R.id.next_ImageView);
        NextLinearLayout = findViewById(R.id.next_LinearLayout);
        // UI(Custom ActionBar)
        setActionBarButton();

        // RecyclerView 설정
        skinArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.picture_RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(skinArrayList);
        recyclerView.setAdapter(myAdapter);
        Log.d("리싸이클러뷰 생성", "true");
        //setClickActionWithAddBtn_TEST();
        clickAddPictureBtn();

    }

    /* 진단 버튼 클릭시 이미지 효과 */
    public void setActionBarButton(){
        DiagnosisImageView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        NextImageView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        NextLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    DiagnosisImageView.setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.SRC_IN);
                    NextImageView.setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.SRC_IN);
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    DiagnosisImageView.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                    NextImageView.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                }
                return false;
            }
        });
    }

    /* RecyclerView 추가 버튼 - TEST */
    public void setClickActionWithAddBtn_TEST(){
        AddPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Skin skin = new Skin("pimple"+count);
                skinArrayList.add(skin);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    public void clickAddPictureBtn(){
        AddPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new DialogForImage(MainActivity.this, new DialogClickListener() {
                    @Override
                    public void onCameraClick() {
                        Log.d("카메라 클릭", "OK");
                    }

                    @Override
                    public void onGalleryClick() {
                        Log.d("갤러리 클릭", "OK");
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
        });

    }




}