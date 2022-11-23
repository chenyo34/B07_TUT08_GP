package com.example.courseplanner;

import java.util.ArrayList;

public class Student extends User {

    private ArrayList<String> TakenCourses;

    public Student() {
        super();
    }
    public Student(String name, String email, String UTORid, ArrayList<String> TakenCourses) {
        super(name, email, UTORid);
    }

    public ArrayList<String> getTakenCourses() {
        return TakenCourses;
    }

    public void setTakenCourses(ArrayList<String> takenCourses) {
        TakenCourses = takenCourses;
    }
}
