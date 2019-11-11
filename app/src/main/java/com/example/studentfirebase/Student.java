package com.example.studentfirebase;

import javax.xml.transform.sax.SAXResult;

public class Student {
    String studentName;
    String studentloc;
    String date;
    String gendre;
    String department;
    String studyyear;
    String expectations;
    String hobby;
    String mImageUri;

    public Student(){
    }

    public Student(String studentName, String studentloc, String date,String gendre,String department,String studyyear,String expectations,String hobby, String mImageUri){
        this.studentName=studentName;
        this.studentloc=studentloc;
        this.date=date;
        this.gendre=gendre;
        this.department=department;
        this.studyyear=studyyear;
        this.expectations=expectations;
        this.hobby=hobby;
        this.mImageUri=mImageUri;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getExpectations() {
        return expectations;
    }

    public void setExpectations(String expectations) {
        this.expectations = expectations;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentloc() {
        return studentloc;
    }

    public void setStudentloc(String studentloc) {
        this.studentloc = studentloc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGendre() {
        return gendre;
    }

    public void setGendre(String gendre) {
        this.gendre = gendre;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStudyyear() {
        return studyyear;
    }

    public void setStudyyear(String studyyear) {
        this.studyyear = studyyear;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
