package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultOfDiagnosis extends AppCompatActivity {
    ImageView infoImageView;
    TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_diagnosis);

        infoImageView = findViewById(R.id.result_info_imageView);
        clickInfoImageView();

        dateTextView = findViewById(R.id.result_date_textView);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        dateTextView.setText(timeStamp);

        Intent intent = getIntent();
        ArrayList<Skin> mSkinList = (ArrayList<Skin>)intent.getSerializableExtra("skinArray");
        // 제대로 넘어온 것 확인 O
    }

    public void clickInfoImageView(){

    }
}