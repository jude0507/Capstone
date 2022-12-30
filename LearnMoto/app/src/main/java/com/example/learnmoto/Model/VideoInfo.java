package com.example.learnmoto.Model;

import com.google.firebase.firestore.Exclude;

public class VideoInfo {

    String VideoName;
    String VideoUrl;
    String VideoLevel;
    String VideoSubject;
    String UploadedBy;
    String myid;

    public VideoInfo(){

    }

    public VideoInfo(String videoName, String videoUrl, String videoLevel, String videoSubject, String uploadedBy) {
        VideoName = videoName;
        VideoUrl = videoUrl;
        VideoLevel = videoLevel;
        VideoSubject = videoSubject;
        UploadedBy = uploadedBy;
    }

    @Exclude
    public String getMyid() {
        return myid;
    }

    public void setMyid(String documentid) {
        this.myid = myid;
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

    public String getUploadedBy() {
        return UploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        UploadedBy = uploadedBy;
    }
}
