package com.example.learnmoto.Model;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class StudentModel {

    String myId;
    public String sName, sAddress, sAge,sPassword, guardianPhone, sID, sGender, sBirthday,
            sLevel, sGuardian, imageurl, imagename, mathScoreQuiz, engScoreQuiz, scienceScoreQuiz, filipinoScoreQuiz, clScoreQuiz,
            mathScoreAct,engScoreAct,scienceScoreAct,filipinoScoreAct,clScoreAct;

    public StudentModel(){

    }

    public StudentModel(String myId, String sName, String sAddress, String sAge, String sPassword, String guardianPhone,
                        String sID, String sGender, String sBirthday, String sLevel, String sGuardian, String imageurl,
                        String imagename, String mathScoreQuiz, String engScoreQuiz, String scienceScoreQuiz, String filipinoScoreQuiz,
                        String clScoreQuiz, String mathScoreAct, String engScoreAct, String scienceScoreAct, String filipinoScoreAct, String clScoreAct) {
        this.myId = myId;
        this.sName = sName;
        this.sAddress = sAddress;
        this.sAge = sAge;
        this.sPassword = sPassword;
        this.guardianPhone = guardianPhone;
        this.sID = sID;
        this.sGender = sGender;
        this.sBirthday = sBirthday;
        this.sLevel = sLevel;
        this.sGuardian = sGuardian;
        this.imageurl = imageurl;
        this.imagename = imagename;
        this.mathScoreQuiz = mathScoreQuiz;
        this.engScoreQuiz = engScoreQuiz;
        this.scienceScoreQuiz = scienceScoreQuiz;
        this.filipinoScoreQuiz = filipinoScoreQuiz;
        this.clScoreQuiz = clScoreQuiz;
        this.mathScoreAct = mathScoreAct;
        this.engScoreAct = engScoreAct;
        this.scienceScoreAct = scienceScoreAct;
        this.filipinoScoreAct = filipinoScoreAct;
        this.clScoreAct = clScoreAct;
    }

    @Exclude

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
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

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
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

    public String getMathScoreQuiz() {
        return mathScoreQuiz;
    }

    public void setMathScoreQuiz(String mathScoreQuiz) {
        this.mathScoreQuiz = mathScoreQuiz;
    }

    public String getEngScoreQuiz() {
        return engScoreQuiz;
    }

    public void setEngScoreQuiz(String engScoreQuiz) {
        this.engScoreQuiz = engScoreQuiz;
    }

    public String getScienceScoreQuiz() {
        return scienceScoreQuiz;
    }

    public void setScienceScoreQuiz(String scienceScoreQuiz) {
        this.scienceScoreQuiz = scienceScoreQuiz;
    }

    public String getFilipinoScoreQuiz() {
        return filipinoScoreQuiz;
    }

    public void setFilipinoScoreQuiz(String filipinoScoreQuiz) {
        this.filipinoScoreQuiz = filipinoScoreQuiz;
    }

    public String getClScoreQuiz() {
        return clScoreQuiz;
    }

    public void setClScoreQuiz(String clScoreQuiz) {
        this.clScoreQuiz = clScoreQuiz;
    }

    public String getMathScoreAct() {
        return mathScoreAct;
    }

    public void setMathScoreAct(String mathScoreAct) {
        this.mathScoreAct = mathScoreAct;
    }

    public String getEngScoreAct() {
        return engScoreAct;
    }

    public void setEngScoreAct(String engScoreAct) {
        this.engScoreAct = engScoreAct;
    }

    public String getScienceScoreAct() {
        return scienceScoreAct;
    }

    public void setScienceScoreAct(String scienceScoreAct) {
        this.scienceScoreAct = scienceScoreAct;
    }

    public String getFilipinoScoreAct() {
        return filipinoScoreAct;
    }

    public void setFilipinoScoreAct(String filipinoScoreAct) {
        this.filipinoScoreAct = filipinoScoreAct;
    }

    public String getClScoreAct() {
        return clScoreAct;
    }

    public void setClScoreAct(String clScoreAct) {
        this.clScoreAct = clScoreAct;
    }
}
