package com.example.galleryimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ITEM_WRITE_STORAGE =102 ;private static final int CAPTURE_COOLER_PHOTO =104 ;
    ImageView imageView,imageView2;Button b;Bitmap resizeImage; @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);
imageView=(ImageView)findViewById(R.id.imageView); initialiseUI();b.setOnClickListener(new View.OnClickListener() { @Override
    public void onClick(View v) { if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1); } }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_ITEM_WRITE_STORAGE);
        }else { takePhoto(); } }}); }         public void Picks(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,100); } @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK){ Uri uri=data.getData();imageView.setImageURI(uri); }
        if (resultCode==RESULT_OK){ switch (requestCode){
                case CAPTURE_COOLER_PHOTO : Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                    imageView2.setImageBitmap(bitmap);saveImagetoGallery(bitmap); break; } } }
                    private void saveImagetoGallery(Bitmap finalBitmap) { String root= Environment.getExternalStorageDirectory().toString();
        File myDir=new File(root+"/saveImages");myDir.mkdir();Random generator=new Random();int n=10000;n=generator.nextInt();
       String coolerFrame="Image-"+n+".jpg";File file=new File(myDir,coolerFrame); if (file.exists()) file.delete();
        try{ FileOutputStream out=new FileOutputStream(file);finalBitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
       String  resizeCoolerImagePath=file.getAbsolutePath();out.flush();out.close();
            Toast.makeText(MainActivity.this,"Photp Save ",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace();
        }catch (Exception e){ e.printStackTrace();Toast.makeText(MainActivity.this,"Throws Exception",Toast.LENGTH_SHORT).show(); } }
            public  void initialiseUI(){ b=(Button)findViewById(R.id.button);imageView2=(ImageView)findViewById(R.id.imageView4); }
 public void takePhoto(){
    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent,CAPTURE_COOLER_PHOTO); }}