package com.onePtwoL.acneanalyzer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {
    private ArrayList<Result> mList;
    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView resultImage;
        public TextView resultTypeTextView, cureTextView;
        public CheckBox checkBoxHome, checkBoxHospital, checkBoxinfection;

        public MyViewHolder2(View v) {
            super(v);
            resultImage = v.findViewById(R.id.result_skin_imageView);
            resultTypeTextView = v.findViewById(R.id.result_type_textView);
            cureTextView = v.findViewById(R.id.result_cure_textView);
            checkBoxHome = v.findViewById(R.id.checkbox_home);
            checkBoxHospital = v.findViewById(R.id.checkbox_hospital);
            checkBoxinfection = v.findViewById(R.id.checkbox_infection);

        }
    }

    public MyAdapter2(ArrayList<Result> myDataset) {
        mList = myDataset;
    }

    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_row, parent, false);
        MyViewHolder2 vh = new MyViewHolder2(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, final int position) {
        // 이미지 뷰
        String imagepath = mList.get(position).getSkinPicturePath();

        File imageFile = new File(imagepath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        holder.resultImage.setImageBitmap(bitmap);

        // 결과 적용
        String typeNumber = mList.get(position).getAcneTypeNum();
        if(typeNumber.equals("1")){
            holder.resultTypeTextView.setText("화이트헤드 (1단계)");
            holder.checkBoxHome.setChecked(true);
            holder.checkBoxHospital.setChecked(false);
            holder.checkBoxinfection.setChecked(false);
            holder.cureTextView.setText(
                    "스팀타올로 모공을 연 뒤 피지연화제품으로 피지를 녹이고 모공 수축 팩을 모공을 수축시켜야 합니다. " +
                            "압출 할 경우, 압출기, 소독용 솜, 면봉을 활용하여 제거해야 합니다. " +
                            "또한 주기적으로 각질 제거를 하여 모공이 막히지 않게 관리하여야 합니다.");
        }else if(typeNumber.equals("2")){
            holder.resultTypeTextView.setText("블랙헤드 (2단계)");
            holder.checkBoxHome.setChecked(true);
            holder.checkBoxHospital.setChecked(false);
            holder.checkBoxinfection.setChecked(false);
            holder.cureTextView.setText(
                    "스팀 타올로 모공을 열어 피지연화 제품으로 피지를 녹이고 모공 수축 기능 제품으로 모공을 수축시켜야 합니다. " +
                            "그 뒤, 수분감 있는 기초 화장품으로 마무리하여 빈 모공을 채워줍니다. ");

        }else if(typeNumber.equals("3")){
            holder.resultTypeTextView.setText("구진 (3단계)");
            holder.checkBoxHome.setChecked(true);
            holder.checkBoxHospital.setChecked(true);
            holder.checkBoxinfection.setChecked(true);
            holder.cureTextView.setText("절대로 압출하면 안됩니다. 고름이 생성될 때 까지 기다리고 압출하거나, 여드름 치료제로 관리를 해야합니다.");

        }else if(typeNumber.equals("4")){
            holder.resultTypeTextView.setText("농포 (4단계)");
            holder.checkBoxHome.setChecked(true);
            holder.checkBoxHospital.setChecked(true);
            holder.checkBoxinfection.setChecked(true);
            holder.cureTextView.setText("압출이 가능하지만 권장하지 않습니다. 압출 후, 여드름 흉터가 될 확률이 매우 높습니다. 병원 치료를 권장합니다.");

        }else if(typeNumber.equals("5")){
            holder.resultTypeTextView.setText("결절, 낭종 (5단계)");
            holder.checkBoxHome.setChecked(false);
            holder.checkBoxHospital.setChecked(true);
            holder.checkBoxinfection.setChecked(true);
            holder.cureTextView.setText("압출 되지 않으며, 연고를 발라도 효과가 미치지 않습니다. 병원에서 적절한 치료를 받아야 합니다.");

        }else{
            holder.resultTypeTextView.setText("존재하지 않음");
            holder.checkBoxHome.setChecked(false);
            holder.checkBoxHospital.setChecked(false);
            holder.checkBoxinfection.setChecked(false);
            holder.cureTextView.setText("존재하지 않는 내용");
        }

    }



    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}