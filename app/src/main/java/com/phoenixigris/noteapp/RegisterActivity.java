package com.phoenixigris.noteapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText email_id, password;
    Button btn_register;
    TextView go_to_login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        email_id = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        btn_register = findViewById(R.id.btn_register);
        go_to_login = findViewById(R.id.go_to_login);

        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_id.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()) {
                    email_id.setError("Please Enter Email");
                    email_id.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please Enter Password");
                    password.requestFocus();

                } else if ((email.isEmpty()) && (pwd.isEmpty())) {
                    Toast.makeText(RegisterActivity.this, "Fields are empty!!", Toast.LENGTH_SHORT).show();
                } else if (!((email.isEmpty()) && (pwd.isEmpty()))) {
                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}