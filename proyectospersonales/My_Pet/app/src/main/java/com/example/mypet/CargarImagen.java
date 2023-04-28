package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CargarImagen extends AppCompatActivity {
    private ImageView ivPet;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_imagen);
        ivPet = (ImageView) findViewById(R.id.ivPet);
        ivPet.setImageResource(R.drawable.signo);
    }

    public void imagen(View view) {
        cargarImagen();
    }

    public void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(i.createChooser(i, "Seleccione la aplicacion"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            uri = path;
            ivPet.setImageURI(path);
        }
    }

    public void cambio(View view) {


        Intent i = new Intent(this, generadorQR.class);
        i.putExtra("uri",String.valueOf(uri));
        startActivity(i);

    }
}