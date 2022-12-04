package testcom.example.b07gp;

import android.se.omapi.Session;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    ArrayList<String> TakenCourses;
//    public String session;
//    public int year;
//    Semester semester = new Semester();



    public Student() {
        TakenCourses = new ArrayList<>();
//        semester = new Semester();
    }

    @Override
    public String toString() {
        return "Student{" +
                "TakenCourses=" + TakenCourses +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Student(String name,
                   String email,
                   String password,
                   String type,
                   String UTROid,
                   Session semester) {

        super(name, email, password, type, UTROid);
        this.TakenCourses = new ArrayList<>();
//        this.semester = new Semester();
    }

    public ArrayList<String> getTakenCourses() {
        return TakenCourses;
    }

    public void setTakenCourses(ArrayList<String> takenCourses) {
        this.TakenCourses = takenCourses;
    }

    public void addTakenCourses(String course) {
        this.TakenCourses.add(course);
    }

    public boolean hasTaken(String courseCode) {
        return this.TakenCourses.contains(courseCode);
    }
    public boolean hasTaken(Course course) {
        return this.TakenCourses.contains(course.getCourseCode());
    }

    public static boolean hasTaken(List<String> taken, Course course) {
        return taken.contains(course.getCourseCode());
    }

    public static boolean canTake(List<String> fakeTaken, Course course) {
        for (String pre: course.getPrecourses()) {
            if (pre.isEmpty()) continue;
            if (!fakeTaken.contains(pre))
                return false;
        }
        return true;
    }

    public void removeTaken(String code){
            TakenCourses.remove(code);
    }

}
