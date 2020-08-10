package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class dialog_loading extends AppCompatActivity {
    ImageView loadingImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_loading);

        loadingImageView = findViewById(R.id.loading_imageView);
        Glide.with(this).load(R.drawable.searching).into(loadingImageView);
    }
}