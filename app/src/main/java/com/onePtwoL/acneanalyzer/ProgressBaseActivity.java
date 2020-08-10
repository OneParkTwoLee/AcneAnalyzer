package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressBaseActivity extends AppCompatActivity {

    public void progressON(){
        ProgressBaseApplication.getInstance().progressON(this);
    }
    public void progressOFF(){
        ProgressBaseApplication.getInstance().progressOFF();
    }
}
