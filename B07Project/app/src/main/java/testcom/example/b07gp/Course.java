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

    public String compStr(String current, String updated){
        String changes = "";
        if(!current.equals(updated)){
            changes = updated;
        }
        return changes;
    }

    public String compAL(ArrayList<String> current, ArrayList<String> updated){
        String changes;
        String added = "";
        String removed = "";
        int i;
        for(i=0; i<current.size(); i++){
            if(!updated.contains(current.get(i))){
                removed = removed + current.get(i) + " ";
            }
        }
        if(!removed.equals("")){
            removed = "Removed " + removed + "\n";
        }
        for(i=0; i<updated.size(); i++){
            if(!current.contains(updated.get(i))){
                added = added + updated.get(i) + " ";
            }
        }
        if(!added.equals("")){
            added = "Added " + added;
        }
        changes = removed + added;
        return changes;
    }

    public ArrayList<String> modify(Course other){
        ArrayList<String> changes = new ArrayList<>();
        changes.add(compStr(this.CourseCode, other.CourseCode));
        changes.add(compStr(this.CourseName, other.CourseName));
        changes.add(compAL(this.Precourses, other.Precourses));
        changes.add(compAL(this.OfferingSessions, other.OfferingSessions));
        return changes;
    }


    public boolean hasOffer(Semester semester) {
        return this.OfferingSessions.contains(semester.session);
    }

    public Course removePre(String deleteCode){
        if(this.Precourses.size()==1){
            this.Precourses.remove(deleteCode);
            this.Precourses.add("");
        }
        else{this.Precourses.remove(deleteCode);}
        return this;
    }
}
