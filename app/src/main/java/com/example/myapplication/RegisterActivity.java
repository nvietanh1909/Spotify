package com.example.myapplication;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText ReUsername, ReEmail, RePassWord, ReWritePassword;
    Button btnSignUp, btnAlready;
    MaterialToolbar materialToolbarSignUp;
    TextInputLayout edtReUsername, edtReEmail, edtRePassword, edtReWritePassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find views
        ReUsername = findViewById(R.id.ReUsername);
        ReEmail = findViewById(R.id.ReEmail);
        RePassWord = findViewById(R.id.RePassWord);
        ReWritePassword = findViewById(R.id.ReWritePassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnAlready = findViewById(R.id.btnAlready);
        materialToolbarSignUp = findViewById(R.id.materialToolbarSignUp);
        edtReEmail = findViewById(R.id.edtReEmail);
        edtReUsername = findViewById(R.id.edtReUsername);
        edtRePassword = findViewById(R.id.edtRePassword);
        edtReWritePassword = findViewById(R.id.edtReWritePassword);
        progressBar = findViewById(R.id.progressBar);

        // Set click listener
        materialToolbarSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for "Sign Up" button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                String username = ReUsername.getText().toString().trim();
                String email = ReEmail.getText().toString().trim();
                String password = RePassWord.getText().toString().trim();
                String reWritePassword = ReWritePassword.getText().toString().trim();

                if (username.isEmpty()) {
                    edtReUsername.setError("Vui lòng nhập tên người dùng");
                    check =  false;
                }
                else {
                    edtReUsername.setError(null);
                }
                if (email.isEmpty()) {
                    edtReEmail.setError("Vui lòng nhập email");
                    check =  false;
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtReEmail.setError("Email không hợp lệ");
                    check =  false;
                }
                else {
                    edtReEmail.setError(null);
                }

                if (password.isEmpty()) {
                    edtRePassword.setError("Vui lòng nhập mật khẩu");
                    check =  false;
                }
                else if(password.length() < 6){
                    edtRePassword.setError("Password phải có ít nhất 6 kí tự");
                    check =  false;
                }
                else {
                    edtRePassword.setError(null);
                }

                if (reWritePassword.isEmpty()) {
                    edtReWritePassword.setError("Vui lòng xác nhận mật khẩu");
                    check =  false;
                }
                else if (!password.equals(reWritePassword)) {
                    edtReWritePassword.setError("Mật khẩu xác nhận không khớp");
                    check =  false;
                }
                else {
                    edtReWritePassword.setError(null);
                }
                // Validate input fields
                if (check) {
                    progressBar.setVisibility(View.VISIBLE);
                    createUserWithEmailAndPassword();
                }
            }
        });
    }

    // Create user with email and password
    private void createUserWithEmailAndPassword() {
        String email = ReEmail.getText().toString();
        String password = RePassWord.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại. Vui lòng thử lại.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    // Update UI after successful authentication
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, navigate to HomeActivity
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is signed out
            // Do something if needed
        }
    }
}
