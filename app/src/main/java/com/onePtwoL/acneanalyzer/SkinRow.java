package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class SkinRow extends AppCompatActivity {
    ImageView RowDeleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_row);

        RowDeleteBtn = findViewById(R.id.skin_closeBtn);
        Log.d("색깔 변경", "OK");
        RowDeleteBtn.setColorFilter(Color.parseColor("#f2f2f2"), PorterDuff.Mode.SRC_IN);
        Log.d("색깔 변경", "OK2");
    }
}