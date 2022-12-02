package testcom.example.b07gp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Course {
    String CourseCode;
    String CourseName;
    ArrayList<String> OfferingSessions;
    ArrayList<String> Precourses;


    public Course(String courseCode, String courseName,
                  ArrayList<String> offeringSessions,
                  ArrayList<String> precourses) {
        CourseCode = courseCode;
        CourseName = courseName;
        OfferingSessions = offeringSessions;
        Precourses = precourses;
    }

    public Course() {

    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public ArrayList<String> getOfferingSessions() {
        return OfferingSessions;
    }

    public void setOfferingSessions(ArrayList<String> offeringSessions) {
        OfferingSessions = offeringSessions;
    }

    public ArrayList<String> getPrecourses() {
        return Precourses;
    }

    public void setPrecourses(ArrayList<String> precourses) {
        Precourses = precourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(CourseCode, course.CourseCode) || Objects.equals(CourseName, course.CourseName);
    }


    @Override
    public String toString() {
        return "Course{" +
                "CourseCode='" + CourseCode + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", OfferingSessions=" + OfferingSessions +
                ", Precourses=" + Precourses +
                '}';
    }


    public boolean hasOffer(Semester semester) {
        return this.OfferingSessions.contains(semester.session);
    }
}
