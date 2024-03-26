package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnContinue;
    MaterialToolbar btnBack;
    TextInputLayout Email, Password;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnContinue = findViewById(R.id.btnContinue);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        mAuth = FirebaseAuth.getInstance();
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Email.setError("Nhập email để đăng nhập");
                    check =  false;
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Email không hợp lệ");
                    check =  false;
                }
                else {
                    Email.setError(null);
                }

                if (password.isEmpty()){
                    Password.setError("Nhập password để đăng nhập");
                    check =  false;
                }
                else {
                    Password.setError(null);
                }
                if(check){
                    progressBar.setVisibility(View.VISIBLE);
                    loginUserWithEmailAndPassword();
                }
            }
        });
    }
    private void loginUserWithEmailAndPassword() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Password.setError("Email hoặc mật khẩu không chính xác");
                            updateUI(null);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, navigate to HomeActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is signed out
            // Do something if needed
        }
    }
}