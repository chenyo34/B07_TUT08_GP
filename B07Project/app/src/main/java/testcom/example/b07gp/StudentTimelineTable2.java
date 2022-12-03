package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class StudentTimelineTable2 extends AppCompatActivity {

    private Model model;
    private String userID;
    private List<String> wantToTake;
    private ListView listView;

    private FirebaseAuth mAuth;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline_table2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        model = Model.getInstance();
        // Get the wantToTake courses from the preivous page
        wantToTake = getIntent().getStringArrayListExtra("????");
        //Get the useKey
        userID = getIntent().getStringExtra("???");

        model.getCourses((Map<String, Course> allCourses) -> {
            model.getStudent(userID, (Student student) -> {

//                List<String> taken = new ArrayList<>();
//                taken.addAll(student.getTakenCourses());
                List<String> fakeTaken = new ArrayList<>(student.getTakenCourses());
                HashSet<Course> coursePath = new HashSet<>();
                Map<String, ArrayList<String>> Timeline = new HashMap<>();
                Semester curSem = student.semester;

                // Get all the courses maybe needed
                for (String courseCode: wantToTake) {
                    coursePath.add((Course) model.getCoursePath(allCourses,courseCode));
                }

                // Remove the courses has been taken from the CouresPath
                for (Course c: coursePath) {
                    if (!student.getTakenCourses().contains(c.CourseCode)) continue;
                    coursePath.remove(c);
                }


                while(coursePath.size() != 0) {

                    // Condition check:
                    // 1) Can I take this course
                    // & 2) Does the Institution offer this course?
                    ArrayList<String> courses = new ArrayList<>();
                    for (Course c: coursePath) {

                        if(Student.canTake(fakeTaken, c) //Cond 1
                                && c.hasOffer(curSem)){  //Cond 2

                            // If so
                            // Add it to current seesion courses
                            courses.add(c.CourseCode);

                            // Add it to fakeTake
                            fakeTaken.add(c.CourseCode);

                            // and remove from the coursePath
                            coursePath.remove(c);
                        }
                    }

                    Timeline.put(curSem.toString(), courses);

                    curSem = curSem.next();

                }
            });
        });

//        model.getCourses((Map<String, Course> allCourses) -> {
//            model.getStudent(userID, (Student student) -> {

//                List<String> taken = new ArrayList<>();
//                taken.addAll(student.getTakenCourses());
//                List<String> fakeTaken = new ArrayList<>(student.getTakenCourses());


//                for (String courseCode: wantToTake) {
//                    List<Course> coursePath = model.getCoursePath(allCourses, courseCode);
//                    Collections.reverse(coursePath);

//                    // TODO: refacgtor
//                    List<String> sessions = new ArrayList<>();
//                    sessions.add("Fall"); sessions.add("Winter"); sessions.add("Summer");
//                    int year = student.year;
//                    int s = sessions.indexOf(student.session);
//                    Semester curSem = student.semester;

//
//                    for (int i = 0; i < coursePath.size(); i++) {
//                        Course course = coursePath.get(i);
//                        if (fakeTaken.contains(course.getCourseCode())) continue;
//
//                        // Adding process
//                        boolean added = false; // initialize the added to false
//
//                        while (!added) {
//                            for(int j = i; j < coursePath.size(); j++) {
//
//                                // j-precourses
//                                Course curr = coursePath.get(j);
//
//                                // Check if the course(s) is available for the student to take in current session
//                                if (Student.canTake(fakeTaken, curr) && curr.hasOffer(curSem)) {
//                                    fakeTaken.add(curr.getCourseCode());
//
//                                    // TODO: keep track the year and session of this newly added course
//
//                                    added = true;
//                                }
//                            }
//                            if (added) break;
//                            // didn't add any course in this session, go check the next
//                            s = (s+1) % 3;
//                            if (s == 1) year += 1;
//
//                        }

//                    while () {
//
//                    }
//
//
//                    }
//                }
//
//            });
//        });
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
        Intent intent = getIntent();
        ArrayList<String> arrayList = intent.getStringArrayListExtra("Wanted_course");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentTimelineTable2.this, android.R.layout.simple_list_item_1, arrayList);

       //listView = (ListView) findViewById(R.id.listview_table);
       listView.setAdapter(arrayAdapter);
//        listView.setAdapter(arrayAdapter);
    }
}