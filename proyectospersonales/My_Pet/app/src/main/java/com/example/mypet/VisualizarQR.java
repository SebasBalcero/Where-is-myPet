package com.example.mypet;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class VisualizarQR extends AppCompatActivity {
    Bitmap bitmap;
    private ImageView ivCodeQR;
    Button btnSaveImagen;
    private static  final int REQUEST_PERMISSION_WRITE_STORE=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_qr);
        String inf = getIntent().getStringExtra("dato");
        ivCodeQR = (ImageView) findViewById(R.id.ivCodeQR);
        btnSaveImagen = (Button) findViewById(R.id.btnSaveImage);
        try{

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.encodeBitmap(inf, BarcodeFormat.QR_CODE, 750, 750);
            ivCodeQR.setImageBitmap(bitmap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(this,"Information saved successfully",Toast.LENGTH_LONG).show();
    }
    public void back(View view){
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
    }
    public void saveImage(){
        OutputStream fos = null;
        File file = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentResolver resolver =getContentResolver();
            ContentValues values = new ContentValues();
            String fileName = System.currentTimeMillis() + "QRImage";
            values.put(MediaStore.Images.Media.DISPLAY_NAME,fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/WhereIsMyPet");
            values.put(MediaStore.Images.Media.IS_PENDING,1);
            Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri imageUri =  resolver.insert(collection,values);
            try {
                fos= resolver.openOutputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            values.clear();
            values.put(MediaStore.Images.Media.IS_PENDING,0);
            resolver.update(imageUri,values,null,null);
        }else{
            String imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            String fileName = System.currentTimeMillis() + ".jpg";
            file = new File(imageDir,fileName);
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        boolean saved = bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        if(saved){
            Toast.makeText(this,"Picture was saved successfully", Toast.LENGTH_LONG).show();
        }
        if(fos!=null){
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(file!=null){
            MediaScannerConnection.scanFile(this, new String[]{file.toString()},null,null);
        }

    }
    //para el boton
    public void saveToGallery(View view){
        checkPermissionStorage();

    }

    private void checkPermissionStorage() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    saveImage();
                }else{
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION_WRITE_STORE
                    );
                }
            }else{
                saveImage();
            }
        }else{
            saveImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_WRITE_STORE){
            if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
            }
        }
    }
}