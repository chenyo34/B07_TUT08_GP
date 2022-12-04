package testcom.example.b07gp;

import java.util.ArrayList;
import java.util.List;

public class Semester {
    int year;
    String session;


    @Override
    public String toString() {
        return year + " " + session;
    }

    public Semester() {
        this.year = 2022;
        this.session = "Fall";
    }

    public Semester(int year, String session) {
        this.year = year;
        this.session = session;
    }

    public Semester next() {

        List<String> sessions = new ArrayList<>();
        sessions.add("Fall"); sessions.add("Winter"); sessions.add("Summer");
        int next = (sessions.indexOf(this.session)+1) % 3;

        //Return the next semester by checking the current session
        if (this.session.equals("Fall")) {
            return new Semester(this.year+1,sessions.get(next));
        }
        return new Semester(this.year,sessions.get(next));

    }


}
