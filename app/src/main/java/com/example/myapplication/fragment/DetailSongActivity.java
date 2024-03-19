package com.example.myapplication.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.classes.Song;
import com.example.myapplication.classes.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailSongActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    ArrayList<Song> songArrayList;
    ImageButton btnPlay, btnBackSong, btnNextSong;
    TextView txtBack, txtNameSong, totalTime, timeSong;
    ImageView imageView;
    SeekBar seekBar;

    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);

        txtBack = findViewById(R.id.txtBack);
        txtNameSong = findViewById(R.id.txtNameSong);
        imageView = findViewById(R.id.imageView);
        btnPlay = findViewById(R.id.btnPlay);
        totalTime = findViewById(R.id.totalTime);
        timeSong = findViewById(R.id.timeSong);
        btnBackSong = findViewById(R.id.btnBackSong);
        btnNextSong = findViewById(R.id.btnNextSong);
        seekBar = findViewById(R.id.seekBar);

        //Method
        createData();
        createMediaPlayer();
        addAllEvents();

        // Lấy thông tin từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String songName = intent.getStringExtra("song_name");
            String songImg = intent.getStringExtra("song_img");
            // Hiển thị tên bài hát
            if (songName != null && !songName.isEmpty()) {
                txtNameSong.setText(songName);
            }
            // Hiển thị thông tin trong activity
            if (songImg != null && !songImg.isEmpty()) {
                imageView.setImageBitmap(Utils.loadBitMapFormAssets(this, songImg, "img_album"));
            }
        }
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailSongActivity.this, HomeActivity.class);
                startActivity(i);
                mediaPlayer.pause();
                finish();
            }
        });
    }

    private void addAllEvents() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.baseline_play_circle_24);
                }
                else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.baseline_pause_circle_24);
                }
                SetTotalTime();
                UpdateTimeSong();
            }
        });
        btnNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > songArrayList.size() - 1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                createMediaPlayer();
                mediaPlayer.start();
                SetTotalTime();
                UpdateTimeSong();
            }
        });
        btnBackSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0){
                    position = songArrayList.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                createMediaPlayer();
                mediaPlayer.start();
                SetTotalTime();
                UpdateTimeSong();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void createMediaPlayer() {
        mediaPlayer = MediaPlayer.create(DetailSongActivity.this, songArrayList.get(position).getFilePath());
    }

    private void createData() {
        songArrayList = new ArrayList<>();
        songArrayList.add(new Song("", "Obito", "Đánh đổi", 300, "Gen Z", R.raw.danhdoi, "obito.jpg"));
        songArrayList.add(new Song("", "Parys", "Vacole", 300, "Gen Z", R.raw.vacole, "vacole.jpg"));
    }

    private void SetTotalTime(){
        SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
        totalTime.setText(fm.format(mediaPlayer.getDuration()) + "");
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
                timeSong.setText(fm.format(mediaPlayer.getCurrentPosition()) + "");
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > songArrayList.size() - 1){
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        SetTotalTime();
                        createMediaPlayer();
                        UpdateTimeSong();
                        mediaPlayer.start();
                    }
                });
                handler.postDelayed(this, 500);
            }
        });
    }
}