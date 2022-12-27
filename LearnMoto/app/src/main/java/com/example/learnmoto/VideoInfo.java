package com.example.learnmoto;

public class VideoInfo {

    String VideoName;
    String VideoUrl;
    String VideoLevel;
    String VideoSubject;

    public VideoInfo(){

    }

    public VideoInfo(String videoName, String videoUrl, String videoLevel, String videoSubject) {
        VideoName = videoName;
        VideoUrl = videoUrl;
        VideoLevel = videoLevel;
        VideoSubject = videoSubject;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getVideoLevel() {
        return VideoLevel;
    }

    public void setVideoLevel(String videoLevel) {
        VideoLevel = videoLevel;
    }

    public String getVideoSubject() {
        return VideoSubject;
    }

    public void setVideoSubject(String videoSubject) {
        VideoSubject = videoSubject;
    }
}
