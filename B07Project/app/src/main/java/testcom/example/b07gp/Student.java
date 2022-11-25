package testcom.example.b07gp;

import java.util.ArrayList;

public class Student extends User{

    ArrayList<String> TakenCourses;

    public ArrayList<String> getTakenCourses() {
        return TakenCourses;
    }


    public Student(String name, String email, ArrayList<String> takenCourses) {
        super(name, email);
        TakenCourses = takenCourses;
    }

    public void setTakenCourses(ArrayList<String> takenCourses) {
        this.TakenCourses = takenCourses;
    }

    public void addTakenCourses(ArrayList<String> NewCourses) {
        this.TakenCourses.addAll(NewCourses);
    }


}
