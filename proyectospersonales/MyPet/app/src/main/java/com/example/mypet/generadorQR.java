package com.example.mypet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class generadorQR extends AppCompatActivity {
    private EditText edtNombre, edtRaza, edtTel1,edtTel2,edtCorreo,edtDescripcion,edtVacunas,edtDueño;
    private Spinner spKind;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generador_qr);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtRaza=(EditText)findViewById(R.id.edtRaza);
        edtTel1 =(EditText)findViewById(R.id.edtTel1);
        edtTel2=(EditText)findViewById(R.id.edtTel2);
        edtCorreo=(EditText)findViewById(R.id.edtCorreo);
        edtDescripcion=(EditText)findViewById(R.id.edtDescripcion);
        edtVacunas=(EditText)findViewById(R.id.edtVacunas);
        spKind = (Spinner) findViewById(R.id.spKind);
        edtDueño = (EditText)findViewById(R.id.edtDueño);
        String [] options = {"Dog","Cat","Horse","Other"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,options);
        spKind.setAdapter(adapter);
        uri = Uri.parse(getIntent().getStringExtra("uri"));

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
}