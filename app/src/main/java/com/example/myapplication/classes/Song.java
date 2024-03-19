package com.example.myapplication.classes;

import java.io.Serializable;

public class Song implements Serializable {
    private String title;       // Tên bài hát
    private String artist;      // Nghệ sĩ thể hiện
    private String album;       // Tên album
    private int duration;       // Thời lượng bài hát (đơn vị: giây)
    private String genre;       // Thể loại nhạc
    private int filePath;    // Đường dẫn đến tệp nhạc trên thiết bị
    private String albumArt;    // Dữ liệu hình ảnh đại diện cho album (byte array)
    public Song() {
    }
    public Song(String title, String artist, String album, int duration, String genre, int filePath, String albumArt) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.filePath = filePath;
        this.albumArt = albumArt;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getFilePath() {
        return filePath;
    }

    public void setFilePath(int filePath) {
        this.filePath = filePath;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }
}
