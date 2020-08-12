package com.onePtwoL.acneanalyzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

public class DialogForLoading extends Dialog {
    ImageView loadingImageView;
    Context context;


    public DialogForLoading(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_for_loading);

        loadingImageView = findViewById(R.id.loading_progressBar);
        Glide.with(getContext()).load(R.drawable.loader).into(loadingImageView);
    }
}