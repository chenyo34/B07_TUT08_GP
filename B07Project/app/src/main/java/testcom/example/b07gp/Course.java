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

        String strOfferSession = "";
        for (String offsession: OfferingSessions ) {
            System.out.println(offsession);
            strOfferSession += offsession + " ";
        }


        String strPrecourses = "";
        if ((Precourses.size() == 1) && (Precourses.get(0) == "")) {
            strPrecourses = "No Prerequisites are needed. ";
        } else {
            System.out.println("here");
            strPrecourses += "Those are precourses:\n";
            for (String precourse: Precourses) {
                strPrecourses += precourse + " ";
            }
        }

        return "Course Code is:  \n" + CourseCode + "\n" +
                "                \n" +
                "Course Name is: \n" + CourseName + "\n" +
                "                \n" +
                "It will be offered in " + strOfferSession + "\n" +
                "                \n" +
                strPrecourses ;
    }
}
