package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView imageViewDetail;
    TextView txtNameAlbum, txtNameArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
        addControls();
    }
        private void addControls() {
        imageViewDetail = findViewById(R.id.imageViewDetail);
        txtNameAlbum = findViewById(R.id.txtNameAlbum);
        txtNameArtist = findViewById(R.id.txtNameArtist);
    }
}