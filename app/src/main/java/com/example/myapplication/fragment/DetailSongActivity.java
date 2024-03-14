package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;

import java.util.Stack;

public class DetailSongActivity extends AppCompatActivity {

    TextView txtBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);

        txtBack = findViewById(R.id.txtBack);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailSongActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}