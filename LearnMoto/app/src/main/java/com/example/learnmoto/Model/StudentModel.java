package com.example.learnmoto.Model;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class StudentModel {

    String myid;
    List<String> todoList;
    public String sName, sAddress, sAge,sPassword, sID, sGender, sBirthday, sLevel, sGuardian, imageurl, imagename;

    public StudentModel(){

    }

    public StudentModel(String sName, String sAddress, String sAge, String sGender, String sBirthday, String sLevel, String sID, String sPassword, String sGuardian,
                        String imageurl, String imagename, List<String> todoList) {
        this.sName = sName;
        this.sAddress = sAddress;
        this.sAge = sAge;
        this.sGender = sGender;
        this.sBirthday = sBirthday;
        this.sLevel = sLevel;
        this.sPassword = sPassword;
        this.sID = sID;
        this.imageurl = imageurl;
        this.imagename = imagename;
        this.todoList = todoList;
    }

    @Exclude
    public String getMyid() {
        return myid;
    }

    public void setMyid(String documentid) {
        this.myid = myid;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsAge() {
        return sAge;
    }

    public void setsAge(String sAge) {
        this.sAge = sAge;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsGender() {
        return sGender;
    }

    public void setsGender(String sGender) {
        this.sGender = sGender;
    }

    public String getsBirthday() {
        return sBirthday;
    }

    public void setsBirthday(String sBirthday) {
        this.sBirthday = sBirthday;
    }

    public String getsLevel() {
        return sLevel;
    }

    public void setsLevel(String sLevel) {
        this.sLevel = sLevel;
    }

    public String getsGuardian() {
        return sGuardian;
    }

    public void setsGuardian(String sGuardian) {
        this.sGuardian = sGuardian;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public List<String> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<String> todoList) {
        this.todoList = todoList;
    }
}
