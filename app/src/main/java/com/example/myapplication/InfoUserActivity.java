package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class InfoUserActivity extends AppCompatActivity {
    TextView emailUser;
    MaterialToolbar btnback;
    EditText passwordUser;
    Button btnSave;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_user);

        //Lấy id
        btnback = findViewById(R.id.btnBack);
        emailUser = findViewById(R.id.emailUser);
        passwordUser = findViewById(R.id.passwordUser);
        btnSave = findViewById(R.id.btnSave);
        //Sự kiện
        addEvent();
    }

    private void addEvent() {
        //Nút back quay về trang chủ
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        if(currentUser != null) {
            emailUser.setText(currentUser.getEmail());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailUser.getText().toString().trim();
                String newpass = passwordUser.getText().toString().trim();

                //Hiển thị email của người dùng
                if(!email.isEmpty()){
                    emailUser.setText("Email:" + email);
                }
                // Cập nhật mật khẩu mới
                if (!newpass.isEmpty()) {
                    currentUser.updatePassword(newpass)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(InfoUserActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Xử lý khi cập nhật mật khẩu không thành công
                                    Toast.makeText(InfoUserActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}