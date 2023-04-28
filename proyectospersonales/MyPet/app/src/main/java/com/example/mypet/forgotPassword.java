package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgotPassword extends AppCompatActivity {
EditText edtEmail;
FirebaseAuth auth;
    Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        auth = FirebaseAuth.getInstance();

    }
    public  void resetPassword(View view){
        String email = edtEmail.getText().toString();
        Matcher mather = pattern.matcher(email);
        if(email.isEmpty()){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
        }
        else if (mather.find() == true){
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(forgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                        edtEmail.setText("");
                    }else{
                        Toast.makeText(forgotPassword.this, "Try again! Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "El correo ingresado no es valido", Toast.LENGTH_SHORT).show();
        }
    }
}