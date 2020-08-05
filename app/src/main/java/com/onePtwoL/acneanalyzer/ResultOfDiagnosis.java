package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultOfDiagnosis extends AppCompatActivity {
    ImageView infoImageView;
    TextView dateTextView, testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_diagnosis);

        infoImageView = findViewById(R.id.result_info_imageView);
        dateTextView = findViewById(R.id.result_date_textView);
        testTextView = findViewById(R.id.result_test_textView);

        Intent intent = getIntent();
        ArrayList<Skin> mSkinList = (ArrayList<Skin>)intent.getSerializableExtra("skinArray");
        testTextView.setText(mSkinList.get(0).getSkinPictureName());
    }
}