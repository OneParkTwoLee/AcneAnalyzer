package com.onePtwoL.acneanalyzer;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;

public class ProgressBaseApplication extends Application {
    private static ProgressBaseApplication baseApplication;
    AppCompatDialog progressDialog;

    public static ProgressBaseApplication getInstance(){
        return baseApplication;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        baseApplication = this;
    }

    public void progressON(Activity activity){
        if(activity == null || activity.isFinishing()){
            Log.d("프로그레스 Activity", "NULL");
        }

        if(progressDialog != null && progressDialog.isShowing()){

        }else{
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //progressDialog.setContentView(R.layout.activity_dialog_loading);
            progressDialog.show();
        }


    }

    public void progressOFF(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
