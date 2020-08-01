package com.onePtwoL.acneanalyzer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Skin> mList;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView RowName;
        public Button RowDeleteBtn;

        public MyViewHolder(View v) {
            super(v);
            RowName = v.findViewById(R.id.skin_TextView);
            RowDeleteBtn = v.findViewById(R.id.skin_closeBtn);
        }
    }

    public MyAdapter(ArrayList<Skin> myDataset) {
        mList = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skin_picture_row, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.RowName.setText(mList.get(position).getSkinPictureName());
        holder.RowDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}