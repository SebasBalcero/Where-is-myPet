package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class SeePet extends AppCompatActivity {
    private ImageView ivPet;
    private EditText  edtNombre, edtRaza, edtTel1,edtTel2,edtCorreo,edtDescripcion,edtVacunas,edtDueño;
    Uri uri;
    int petId;
    private Spinner spKind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_pet);
        ivPet = (ImageView) findViewById(R.id.ivPet);

        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtRaza=(EditText)findViewById(R.id.edtRaza);
        edtDueño = (EditText)findViewById(R.id.edtDueño);
        edtTel1 =(EditText)findViewById(R.id.edtTel1);
        edtTel2=(EditText)findViewById(R.id.edtTel2);
        edtCorreo=(EditText)findViewById(R.id.edtCorreo);
        edtDescripcion=(EditText)findViewById(R.id.edtDescripcion);
        edtVacunas=(EditText)findViewById(R.id.edtVacunas);
        spKind = (Spinner) findViewById(R.id.spKind);
        String [] options = {"Dog","Cat","Horse","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,options);
        spKind.setAdapter(adapter);


        //ID de la mascota
        petId = Integer.parseInt(getIntent().getStringExtra("petId"));
        //Foto de la mascota
        uri=Uri.parse(getIntent().getStringExtra("petUri"));
        Picasso.get().load(uri).into(ivPet);


    }
    public void informacion(View view){

        if(edtNombre.getText().toString().length()!=0){
            if(edtDueño.getText().toString().length()!=0){
                if(edtTel1.getText().toString().length()!=0 || edtTel2.getText().toString().length()!=0 ){


                    String raza = "",tel1="",tel2="",email="",descripcion="",vacunas="";

                    if(edtRaza.getText().toString().length()!=0){
                        raza = "\nRaza: " + edtRaza.getText().toString();
                    }
                    if(edtTel1.getText().toString().length()!=0){
                        tel1 = "\nTelefono 1: " + edtTel1.getText().toString();
                    }if(edtTel2.getText().toString().length()!=0){
                        tel2 = "\nTelefono 2: " + edtTel2.getText().toString();
                    }if(edtCorreo.getText().toString().length()!=0){
                        email = "\nCorreo: " + edtCorreo.getText().toString();
                    }if(edtDescripcion.getText().toString().length()!=0){
                        descripcion = "\nDescripcion de la mascota: " + edtDescripcion.getText().toString();
                    }if(edtVacunas.getText().toString().length()!=0){
                        vacunas = "\nVacunas: " + edtVacunas.getText().toString();
                    }
                    // use la varaible uri que tiene la direccion de la imagen
                    String selected = spKind.getSelectedItem().toString();
                    String inf = "INFORMACION DE LA MASCOTA\n" + "Nombre de la mascota: " + edtNombre.getText().toString() + "\nTipo: " + selected + raza + "\nDueño: " + edtDueño.getText().toString() + tel1 + tel2 + email + descripcion + vacunas;
                    Intent i = new Intent(this, VisualizarQR.class);
                    i.putExtra("dato", inf);
                    startActivity(i);


                }



                else{
                    Toast.makeText(this, "Put at least one phone", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, "Put the name of the owner", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Put the name of your pet", Toast.LENGTH_LONG).show();
        }

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



    public void changes(View view){

        // Modificar en la base de datos daniel
        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_LONG).show();
    }

    public  void  delete(View view){
        //Daniel has lo tuyo

        Toast.makeText(this, "Pet profile deleted", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,SecondActivity.class);
        startActivity(i);
    }

}