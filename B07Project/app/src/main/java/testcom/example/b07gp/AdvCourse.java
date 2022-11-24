package testcom.example.b07gp;

import java.util.HashSet;

public class AdvCourse extends Course{

    HashSet<String> Precourses;

    public AdvCourse(String courseCode, String courseName, HashSet<String> offeringSessions) {
        super(courseCode, courseName, offeringSessions);
    }

    public AdvCourse(String courseCode, String courseName, HashSet<String> offeringSessions, HashSet<String> precourses) {
        super(courseCode, courseName, offeringSessions);
        Precourses = precourses;
    }

    public HashSet<String> getPrecourses() {
        return Precourses;
    }

    public void setPrecourses(HashSet<String> precourses) {
        Precourses = precourses;
    }

}
