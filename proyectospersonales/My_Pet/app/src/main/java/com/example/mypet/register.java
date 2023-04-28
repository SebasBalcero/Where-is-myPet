package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText edtEmail, edtPassword, edtConfirmedPassword;
    Button btnRegister;
    Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtConfirmedPassword = (EditText)findViewById(R.id.edtPasswordConfirmed);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        firebaseAuth= FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String passwordConfirmed = edtConfirmedPassword.getText().toString();

                if(password.length()!=0 && passwordConfirmed.length()!=0 &&  email.length()!=0){
                    Matcher mather = pattern.matcher(email);
                    if(mather.find() == true){
                        if(password.length()> 7){
                            if(password.equals(passwordConfirmed)){
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(register.this, "Se envio un codigo de verificacion a correo: " + email, Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            user.sendEmailVerification();
                                            FirebaseAuth.getInstance().signOut();
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(register.this, "Se produjo un error en la creacion de la cuenta", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(register.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(register.this, "La contraseña debe tener minimo 8 caracteres", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(register.this, "El correo ingresado no es valido", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(register.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}