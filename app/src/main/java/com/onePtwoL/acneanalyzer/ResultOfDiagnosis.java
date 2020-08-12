package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultOfDiagnosis extends AppCompatActivity {
    /* Activity와 연결된 컴포넌트 */
    ImageView infoImageView;
    TextView dateTextView;
    Dialog dialog;

    /* RecyclerView와 관련된 컴포넌트 */
    ArrayList<Skin> mSkinList;
    ArrayList<Result> resultArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MyAdapter2 myAdapter2;

    String resultString = "";
    String[] typeNums;
    String testTypeNum = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_diagnosis);

        infoImageView = findViewById(R.id.result_info_imageView);
        infoImageView.setClickable(true);
        clickInfoImageView();

        // 오늘 날짜 설명 UI
        dateTextView = findViewById(R.id.result_date_textView);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        dateTextView.setText(timeStamp);

        // RecyclerView 설정
        Intent intent = getIntent();
        mSkinList = (ArrayList<Skin>)intent.getSerializableExtra("skinArray");

        resultString = intent.getExtras().getString("resultString");
        typeNums = resultString.split(",");

        resultArrayList = new ArrayList<>();
        for(int i=0;i<mSkinList.size();i++){
            Result resultData = new Result(mSkinList.get(i).getSkinPicturePath(), typeNums[i]);
            //Result resultData = new Result(mSkinList.get(i).getSkinPicturePath(), testTypeNum);
            //testTypeNum++;
            Log.d("Result 데이터 확인", resultData.getSkinPicturePath()+" 와 "+typeNums[i]);
            resultArrayList.add(resultData);
        }

        recyclerView = findViewById(R.id.result_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myAdapter2 = new MyAdapter2(resultArrayList);
        recyclerView.setAdapter(myAdapter2);
        Log.d("결과 리싸이클러뷰", "OK");


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