package com.example.learnmoto.Model;

import com.google.firebase.firestore.Exclude;

public class ToDoModel {
    String myID;
    String message, authorID, authorName, level;

    public ToDoModel(){

    }

    public ToDoModel(String message, String authorID, String authorName, String level) {
        this.message = message;
        this.authorID = authorID;
        this.authorName = authorName;
        this.level = level;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
