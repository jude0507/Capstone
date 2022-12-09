package com.example.learnmoto.Model;

import com.google.firebase.firestore.Exclude;

public class AnnouncementModel {
    String myID;
    String message, authorID, authorName;

    public AnnouncementModel(){

    }

    public AnnouncementModel(String message, String authorID, String authorName) {
        this.message = message;
        this.authorID = authorID;
        this.authorName = authorName;
    }

    @Exclude
    public String getMyID() {
        return myID;
    }

    public void setMyID(String myID) {
        this.myID = myID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
