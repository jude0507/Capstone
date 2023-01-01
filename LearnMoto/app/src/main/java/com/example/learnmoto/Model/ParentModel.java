package com.example.learnmoto.Model;

import com.google.firebase.firestore.Exclude;

public class ParentModel {

    String myid;
    public String pName, pAddress, pPhoneNumber, pPassword, pID, pChildID,imageurl, imagename;


    public ParentModel(){

    }

    public ParentModel(String pName, String pAddress, String pPassword, String pID, String pChildID, String imageurl, String imagename, String pPhoneNumber) {
        this.pName = pName;
        this.pAddress = pAddress;
        this.pPassword = pPassword;
        this.pID = pID;
        this.pChildID = pChildID;
        this.imageurl = imageurl;
        this.imagename = imagename;
        this.pPhoneNumber = pPhoneNumber;
    }

    @Exclude
    public String getMyid() {
        return myid;
    }

    public void setMyid(String documentid) {
        this.myid = myid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpPassword() {
        return pPassword;
    }

    public void setpPassword(String pPassword) {
        this.pPassword = pPassword;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getpChildID() {
        return pChildID;
    }

    public void setpChildID(String pChildID) {
        this.pChildID = pChildID;
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

    public String getpPhoneNumber() {
        return pPhoneNumber;
    }

    public void setpPhoneNumber(String pPhoneNumber) {
        this.pPhoneNumber = pPhoneNumber;
    }
}
