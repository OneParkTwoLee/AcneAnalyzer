package com.onePtwoL.acneanalyzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private static Uri imageUri, albumUri;
    private String imageFilePath = " ", imageFileName=" ";
    private Bitmap imageBitmap;

    /* OKHTTP3 */
    private TextView mTextViewResult;
    private OkHttpClient client;
    private String OkhttpUrl;
    private Request request;
    private MediaType mediaType;
    private RequestBody body;
    private String resultString = " ";

    private DialogForLoading loadingDialog;
    private ProgressDialog progressDialog = null;


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

        // OKHTTP3
        mTextViewResult = findViewById(R.id.text_view_result);  // 디버깅용
        client = new OkHttpClient();
        mediaType = MediaType.parse("text/plain");
        OkhttpUrl ="http://172.30.1.4:5000/api/acnes";

    }



    /* 진단 버튼 클릭시 이미지 효과 */
    public void setActionBarButton(){
        DiagnosisImageView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        NextImageView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

        NextLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (skinArrayList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "진단할 이미지를 업로드 한 후, 진단을 진행하세요", Toast.LENGTH_SHORT).show();
                } else {

                loadingDialog = new DialogForLoading(MainActivity.this, android.R.style.Theme_Black_NoTitleBar);
                loadingDialog.setCancelable(true);
                loadingDialog.setCanceledOnTouchOutside(false);
                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loadingDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                loadingDialog.show();



                MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                if (skinArrayList.size() == 0) {
                    Log.d("넘길 데이터 없음", "NULL");
                } else {
                    for (int i = 0; i < skinArrayList.size(); i++) {
                        multiBuilder.addFormDataPart("acne", skinArrayList.get(i).getSkinPictureName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"),
                                        new File(skinArrayList.get(i).getSkinPicturePath())));
                    }
                }

                body = multiBuilder.build();
                request = new Request.Builder()
                        .url(OkhttpUrl)
                        .method("POST", body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("연결 실패", "error Connect Server error is" + e.toString());
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //System.out.println("response body is"+response.body().string());
                        if (response.isSuccessful()) {
                            //final String myResponse = response.body().string();
                            try {
                                final JSONObject jsonObject = new JSONObject(response.body().string());
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            resultString = jsonObject.getString("predicted_label");
                                            //Log.d("데이터 확인확인", resultString);
                                            mTextViewResult.setText(resultString);
                                            Log.d("확인해보자", resultString);

                                            loadingDialog.dismiss();
                                            Intent intent = new Intent(getApplicationContext(), ResultOfDiagnosis.class);
                                            intent.putExtra("skinArray", skinArrayList);
                                            intent.putExtra("resultString", resultString);
                                            startActivity(intent);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });


            }
        }
        });




    }

    /* RecyclerView 데이터 추가 버튼 */
    public void setRecyclerViewData(String name, String path){
        Log.d("리사이클러뷰 데이터 추가", path+"");
        Skin skin = new Skin(name, path);
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
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
        });
    }


    // 카메라 intent 연결하는 함수
    private void takePhotoWithCamera(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                    Log.d("카메라 찍음", photoFile.toString()+"");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    Log.d("프로바이더", getPackageName()+"");
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.onePtwoL.acneanalyzer.fileprovider", photoFile);
                    imageUri = photoURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
                }
            } else{
                Log.d("카메라 저장공간", "접근 불가");
            }
        }

    }

    /* 이미지 파일 생성하는 함수 */
    private File createImageFile() throws IOException{
        // 이미지 파일 이름
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName =  "test_" +timeStamp+".jpg" ;
        Log.d("이미지 파일 타임스탬프", imageFileName+"");

        // 이미지가 저장될 폴더 이름(outline) & 빈 폴더 생성
        File storageDir = Environment.getExternalStorageDirectory(); // 외부 저장소 (공용공간) -> /storage/emulated/0/
        //File storageDir = new File(Environment.getExternalStorageDirectory()+"/Pictures", "AcneAnalyzer");
        if(storageDir.exists()){
            storageDir.mkdirs();
        }
        File image = new File(storageDir, imageFileName);
        imageFilePath = image.getAbsolutePath();
        Log.d("이미지 파일 절대경로", imageFilePath+"");
        return image;
    }

    /* 갤러리에 저장하는 함수 */
    private void saveImage(Bitmap finalBitmap){
        String imageFileForGalName = imageFileName;
        Log.d("갤러리 저장될 때", imageFileForGalName+"");
        File myDir = new File(Environment.getExternalStorageDirectory().toString());
        //File myDir = new File(Environment.getExternalStorageDirectory()+"/Pictures", "AcneAnalyzer");
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

    private void galleryAddPic(){
        Log.d("갤러리애드픽", "OK");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imageFilePath);
        Uri uri = Uri.fromFile(file);
        mediaScanIntent.setData(uri);
        sendBroadcast(mediaScanIntent);
    }

    /* 갤러리 intent 연결하는 함수 */
    private void selectFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
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

    /* 이미지 크롭 */
    public void cropImage(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(imageUri, "image/*");

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("output", albumUri);
        startActivityForResult(intent, CROP_PICTURE);
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
                        Log.d("카메라 파일", imageFilePath+"");
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                        Log.d("비트맵 변환 성공", "OK");

                        if (imageBitmap != null) {
                            ExifInterface ei = new ExifInterface(imageFilePath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                            int exifDegree = exifOrientationToDegrees(orientation);

                            Bitmap rotatedBitmap = rotateImage(imageBitmap, exifDegree);
                            Log.d("카메라 이미지 URI_re", imageUri+"");

                            saveImage(rotatedBitmap);   // 갤러리에 저장하는 함수
                        }

                        File cropFile = createImageFile();  // 새 크롭 이미지 (덮어쓰기X)
                        albumUri = Uri.fromFile(cropFile);
                        cropImage();
                    }
                    break;

                }
                // 갤러리에서 이미지 불러오는 경우
                case PICK_FROM_ALBUM :{
                    if(resultCode == RESULT_OK){
                        imageUri = data.getData();
                        imageFilePath = getImagePathFromURI(imageUri);
                        Log.d("갤러리이미지path", imageFilePath+"");

                        File cropFile = createImageFile();  // 새 크롭 이미지 (덮어쓰기X)
                        albumUri = Uri.fromFile(cropFile);
                        cropImage();
                    }else {
                        Log.d("갤러리이미지로딩", "NULL");
                    }
                    break;
                }
                case CROP_PICTURE:{
                    if(resultCode == Activity.RESULT_OK){
                        galleryAddPic();
                        File tempFile = new File(imageFilePath);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
                        Log.d("갤러리 절대경로", tempFile.getAbsolutePath()+"");

                        if(bitmap != null){
                            setRecyclerViewData(imageFileName, imageFilePath);  // RecyclerView에 데이터 추가
                            Log.d("갤러리 이미지 URI_re", imageUri+"");
                        }

                    }
                    break;
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

