package testcom.example.b07gp;

import java.util.HashSet;
import java.util.Objects;

public class Course {
    String CourseCode;
    String CourseName;
    HashSet<String> OfferingSessions;


    public Course(String courseCode, String courseName, HashSet<String> offeringSessions) {
        CourseCode = courseCode;
        CourseName = courseName;
        OfferingSessions = offeringSessions;
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

    public HashSet<String> getOfferingSessions() {
        return OfferingSessions;
    }

    public void setOfferingSessions(HashSet<String> offeringSessions) {
        OfferingSessions = offeringSessions;
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
                '}';
    }
}
