package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    /* Activity와 연결된 컴포넌트 */
    Button AddPicBtn;
    ImageView DiagnosisImageView, NextImageView, TestImageView;
    LinearLayout NextLinearLayout;

    /* RecyclerView와 관련된 컴포넌트 */
    ArrayList<Skin> skinArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MyAdapter myAdapter;
    int count = 0;

    /* 카메라, 갤러리와 관련된 컴포넌트 */
    DialogForImage dialog;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_PICTURE = 3;
    private static final int PICK_FROM_FILE = 4;

    private static Uri imageUri;
    private String imageFilePath, imageFileName;
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddPicBtn = findViewById(R.id.add_pic_btn);
        DiagnosisImageView = findViewById(R.id.diag_ImageView);
        NextImageView = findViewById(R.id.next_ImageView);
        NextLinearLayout = findViewById(R.id.next_LinearLayout);
        // UI(Custom ActionBar)
        setActionBarButton();

        // RecyclerView 설정
        skinArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.picture_RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(skinArrayList);
        recyclerView.setAdapter(myAdapter);
        Log.d("리싸이클러뷰 생성", "true");

        // 6.0 마쉬멜로우 이상일 경우에 권한 체크 후 권한 요청
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                Log.d("Permission66","권한 설정 완료");
            }else{
                Log.d("Permission66", "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        clickAddPictureBtn();   // 진단할 사진 추가하기

    }

    /* 진단 버튼 클릭시 이미지 효과 */
    public void setActionBarButton(){
        DiagnosisImageView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        NextImageView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        NextLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    DiagnosisImageView.setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.SRC_IN);
                    NextImageView.setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.SRC_IN);
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    DiagnosisImageView.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                    NextImageView.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                }
                return false;
            }
        });
    }

    /* RecyclerView 추가 버튼 - TEST */
    public void setClickActionWithAddBtn_TEST(){
        AddPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Skin skin = new Skin("pimple"+count);
                skinArrayList.add(skin);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    /* RecyclerView 데이터 추가 버튼 */
    public void setRecyclerViewData(String path){
        Log.d("리사이클러뷰 데이터 추가", path+"");
        Skin skin = new Skin(path+"");
        skinArrayList.add(skin);
        myAdapter.notifyDataSetChanged();
    }

    /* RecyclerView 추가 다이얼로그 버튼 */
    public void clickAddPictureBtn(){
        AddPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new DialogForImage(MainActivity.this, new DialogClickListener() {
                    @Override
                    public void onCameraClick() {
                        Log.d("카메라 클릭", "OK");
                        takePhotoWithCamera();
                    }

                    @Override
                    public void onGalleryClick() {
                        Log.d("갤러리 클릭", "OK");
                        selectFromGallery();
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
        });
    }


    // 카메라 intent 연결하는 함수
    private void takePhotoWithCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createImageFile();
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.onePtwoL.acneanalyzer.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
            }
        }
    }

    /* 이미지 파일 생성하는 함수 */
    private File createImageFile(){
        // 이미지 파일 이름
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName =  "test_" +timeStamp ;
        Log.d("이미지 파일 타임스탬프", imageFileName+"");

        // 이미지가 저장될 폴더 이름(outline) & 빈 폴더 생성
        File storageDir = Environment.getExternalStorageDirectory(); // 외부 저장소 (공용공간) -> /storage/emulated/0/
        if(storageDir.exists()){
            storageDir.mkdirs();
        }
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageFilePath = image.getAbsolutePath();
        Log.d("이미지 파일 절대경로", imageFilePath+"");
        return image;
    }

    /* 갤러리에 저장하는 함수 */
    private void saveImage(Bitmap finalBitmap){
        String imageFileForGalName =  imageFileName + ".jpg";
        Log.d("갤러리 저장될 때", imageFileForGalName+"");
        File myDir = new File(Environment.getExternalStorageDirectory().toString());
        myDir.mkdirs();
        File file = new File(myDir, imageFileForGalName);
        try{
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            out.flush();
            out.close();;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 갤러리 intent 연결하는 함수 */
    private void selectFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    /* 이미지 파일 경로 URI -> String*/
    public String getImagePathFromURI(Uri imageUri){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                // 카메라로 찍은 이미지 불러오는 경우
                case PICK_FROM_CAMERA: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(imageFilePath);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                        if (imageBitmap != null) {
                            ExifInterface ei = new ExifInterface(imageFilePath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                            int exifDegree = exifOrientationToDegrees(orientation);

                            Bitmap rotatedBitmap = rotateImage(imageBitmap, exifDegree);

                            setRecyclerViewData(imageFilePath);  // RecyclerView에 데이터 추가
                            Log.d("카메라 이미지 URI_re", imageUri+"");

                            saveImage(rotatedBitmap);   // 갤러리에 저장하는 함수
                            //startActivityForResult(intent, CROP_FROM_CAMERA);
                        }
                    }
                    break;

                }
                // 갤러리에서 이미지 불러오는 경우
                case PICK_FROM_ALBUM :{
                    if(resultCode == RESULT_OK){
                        Uri ImageUri = data.getData();
                        imageFilePath = getImagePathFromURI(ImageUri);
                        Log.d("갤러리이미지path", imageFilePath+"");
                        File tempFile = new File(imageFilePath);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
                        Log.d("갤러리 절대경로", tempFile.getAbsolutePath()+"");

                        if(bitmap != null){
                            ExifInterface ei = new ExifInterface(tempFile.getAbsolutePath());
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                            int exifDegree = exifOrientationToDegrees(orientation);

                            //Bitmap rotatedBitmap = rotateImage(bitmap, exifDegree);
                            setRecyclerViewData(imageFilePath);  // RecyclerView에 데이터 추가
                            Log.d("갤러리 이미지 URI_re", ImageUri+"");
                        }
                    }else {
                        Log.d("갤러리이미지로딩", "NULL");
                    }
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }

    }


    // 이미지 회전하는 함수
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Log.d("Rotate", angle+"");
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        else{
            return 0;
        }
    }

}