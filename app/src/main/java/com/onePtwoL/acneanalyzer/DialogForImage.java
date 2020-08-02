package com.onePtwoL.acneanalyzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DialogForImage extends Dialog {

    private Context context;
    private DialogClickListener dialogClickListener;
    private ImageView CameraBtn, GalleryBtn;

    public DialogForImage(@NonNull Context context, DialogClickListener dialogClickListener) {
        super(context);
        this.context = context;
        this.dialogClickListener = dialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_for_image);

        CameraBtn = findViewById(R.id.dialog_camera_btn);
        GalleryBtn = findViewById(R.id.dialog_gallary_btn);
        CameraBtn.setClickable(true);
        GalleryBtn.setClickable(true);

        CameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onCameraClick();
                dismiss();
            }
        });

        GalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onGalleryClick();
                dismiss();
            }
        });
    }
}