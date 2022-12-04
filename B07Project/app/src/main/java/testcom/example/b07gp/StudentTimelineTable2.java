package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.SQLOutput;
import java.util.ArrayList;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentTimelineTable2 extends AppCompatActivity {

    private Model model;
    private String userID;
    private List<String> wantToTake;
    private ListView listView;
    private FirebaseAuth mAuth;
    Student student;

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
        wantToTake = getIntent().getStringArrayListExtra("Wanted_course");

        //Get the useKey
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

//        model.getStudent(userID, (Student student) -> {this.student = student; });

        System.out.println("Print the timeline");
//        createTimeline();

        model.getCourses((HashMap<String, Course> allCourses) -> {
            model.getStudent(userID, (Student student) -> {


                List<String> fakeTaken = new ArrayList<>(student.getTakenCourses());
                HashSet<Course> coursePath = new HashSet<>();
                Map<String, ArrayList<String>> Timeline = new HashMap<>();
                Semester curSem = new Semester();

//                 Get all the courses maybe needed
//                for (String courseCode: wantToTake) {
//                    coursePath.addAll(model.getCoursePath(allCourses,courseCode));
//                }

//                 Remove the courses has been taken from the CouresPath
//                for (Course c: coursePath) {
////                    if (!student.getTakenCourses().contains(c.CourseCode)) continue;
////                    coursePath.remove(c);
//                    System.out.println(c.CourseCode);
//                    System.out.println(student.TakenCourses);
//                    if (student.TakenCourses.contains(c.CourseCode)){
//                        coursePath.remove(c);
//                    }
//                }

                for (String courseCode: wantToTake) {
                    for (Course c: model.getCoursePath(allCourses,courseCode)){
                        if (!student.TakenCourses.contains(c.CourseCode) ) {
                            coursePath.add(c);
                        }
                    }
                }

                while( coursePath.size() > 0) {


                    // Condition check:
                    // 1) Can I take this course
                    // & 2) Does the Institution offer this course?
                    ArrayList<String> newcourses = new ArrayList<>();

                    for (Course c: coursePath) {

                        boolean cond1 = Student.canTake(fakeTaken, c);
                        boolean cond2 = c.hasOffer(curSem);

//                        if(Student.canTake(fakeTaken, c) //Cond 1
//                                && c.hasOffer(curSem)){  //Cond 2
                        if (cond1 && cond2){
                            // If so
                            // Add it to current seesion courses
                            newcourses.add(c.CourseCode);


                            // and remove from the coursePath
//                            coursePath.remove(c);
                        }

//                        break;
                    }

                    for(String deleteC: newcourses) {
                        coursePath.remove(allCourses.get(deleteC));
                    }

                    // Add it to fakeTake
                    fakeTaken.addAll(newcourses);
                    System.out.println(newcourses);

                    Timeline.put(curSem.toString(), (ArrayList<String>) newcourses.clone());

                    // clean the new courses
                    newcourses.clear();

                    //Go to next session
                    curSem = curSem.next();

                }
                System.out.println("timeline: ");
                System.out.println(Timeline);
            });
        });
//
//
//        // For the createTimeLine
//
//        model.getStudent(userID, (Student student) -> {
//            this.student = student;
//        });


    }




}