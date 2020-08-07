package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultOfDiagnosis extends AppCompatActivity {
    ImageView infoImageView;
    TextView dateTextView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_diagnosis);

        infoImageView = findViewById(R.id.result_info_imageView);
        infoImageView.setClickable(true);
        clickInfoImageView();

        dateTextView = findViewById(R.id.result_date_textView);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        dateTextView.setText(timeStamp);

        //Intent intent = getIntent();
        //ArrayList<Skin> mSkinList = (ArrayList<Skin>)intent.getSerializableExtra("skinArray");
        // 제대로 넘어온 것 확인 O
    }

    public void clickInfoImageView(){
        infoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("다이얼로그 클릭 확인", "OK");
                dialog = new DialogForInform(ResultOfDiagnosis.this);
                Log.d("다이얼로그 생성 확인", "OK");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
        });
    }
}