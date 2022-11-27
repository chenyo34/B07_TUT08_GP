package testcom.example.b07gp;

import java.util.ArrayList;

public class Student extends User{

    ArrayList<String> TakenCourses;

    @Override
    public String toString() {
        return "Student{" +
                "TakenCourses=" + TakenCourses +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Student(String name, String email, String password, String type, String UTROid) {
        super(name, email, password, type, UTROid);
        this.TakenCourses = new ArrayList<>();
    }

    public ArrayList<String> getTakenCourses() {
        return TakenCourses;
    }

    public void setTakenCourses(ArrayList<String> takenCourses) {
        this.TakenCourses = takenCourses;
    }

    public void addTakenCourses(ArrayList<String> NewCourses) {
        this.TakenCourses.addAll(NewCourses);
    }
}
