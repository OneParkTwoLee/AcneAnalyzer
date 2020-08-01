package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button AddPicBtn;
    ImageView DiagnosisImageView, NextImageView;
    LinearLayout NextLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddPicBtn = findViewById(R.id.add_pic_btn);
        DiagnosisImageView = findViewById(R.id.diag_ImageView);
        NextImageView = findViewById(R.id.next_ImageView);
        NextLinearLayout = findViewById(R.id.next_LinearLayout);

        setActionBarButton();


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
}