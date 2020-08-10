package com.onePtwoL.acneanalyzer;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatDialog;

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
            return;
        }

        if(progressDialog != null && progressDialog.isShowing()){

        }else{
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.activity_dialog_loading);
            progressDialog.show();
        }
    }

    public void progressOFF(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
