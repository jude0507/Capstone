package com.example.learnmoto.Model;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class TeacherModel {

    String myid;
    public String teacher_name, teacher_address, teacher_phone, teacher_ID, teacher_pass, myAdvisoryClass, imageurl, imagename;
    List<String> assignLevel;
    List<String> assignSubject;
    public TeacherModel() {
    }

    public TeacherModel(String teacher_name, String teacher_address, String teacher_phone, String teacher_ID, String teacher_pass, String myAdvisoryClass, String imageurl, String imagename, List<String> assignLevel, List<String> assignSubject) {
        this.teacher_name = teacher_name;
        this.teacher_address = teacher_address;
        this.teacher_phone = teacher_phone;
        this.teacher_ID = teacher_ID;
        this.teacher_pass = teacher_pass;
        this.myAdvisoryClass = myAdvisoryClass;
        this.imageurl = imageurl;
        this.imagename = imagename;
        this.assignLevel = assignLevel;
        this.assignSubject = assignSubject;
    }

    @Exclude
    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_address() {
        return teacher_address;
    }

    public void setTeacher_address(String teacher_address) {
        this.teacher_address = teacher_address;
    }

    public String getTeacher_phone() {
        return teacher_phone;
    }

    public void setTeacher_phone(String teacher_phone) {
        this.teacher_phone = teacher_phone;
    }

    public String getTeacher_ID() {
        return teacher_ID;
    }

    public void setTeacher_ID(String teacher_ID) {
        this.teacher_ID = teacher_ID;
    }

    public String getTeacher_pass() {
        return teacher_pass;
    }

    public void setTeacher_pass(String teacher_pass) {
        this.teacher_pass = teacher_pass;
    }

    public String getMyAdvisoryClass() {
        return myAdvisoryClass;
    }

    public void setMyAdvisoryClass(String myAdvisoryClass) {
        this.myAdvisoryClass = myAdvisoryClass;
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

    public List<String> getAssignLevel() {
        return assignLevel;
    }

    public void setAssignLevel(List<String> assignLevel) {
        this.assignLevel = assignLevel;
    }

    public List<String> getAssignSubject() {
        return assignSubject;
    }

    public void setAssignSubject(List<String> assignSubject) {
        this.assignSubject = assignSubject;
    }
}
