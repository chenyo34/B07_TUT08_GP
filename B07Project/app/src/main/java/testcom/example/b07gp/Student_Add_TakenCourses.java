package testcom.example.b07gp;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student_Add_TakenCourses extends AppCompatActivity implements View.OnClickListener {

    private Button stuAddTakenCourseButton, returnButton;
    private EditText stuAddTakenCourseET;
    private String userID;
    private Model model;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this,StudentListDisplay.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_taken_courses);
        model = Model.getInstance();
        userID = getIntent().getStringExtra("userID");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stuAddTakenCourseButton = (Button) findViewById(R.id.studentAddTakenCourseButton);
        stuAddTakenCourseButton.setOnClickListener(this);

        // Insert Taken CourseCode
        stuAddTakenCourseET = (EditText) findViewById(R.id.studentAddCompletedCoursesEditText);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.studentAddTakenCourseButton:
                addTakenCourse();
        }
    }

    private void addTakenCourse(){
        //get user's input
        String courseCode = stuAddTakenCourseET.getText().toString().trim().replace(" ","").toUpperCase();

        System.out.println(courseCode);

        //check user input if it is empty
        if(courseCode.isEmpty()) {
            stuAddTakenCourseET.setError("CourseCode is required");
            stuAddTakenCourseET.requestFocus();
            return;
        }

        //now try to get the course
        model.getCourses((HashMap<String, Course> allCourses) -> {

            System.out.println("Try to get the course");
            //find the class corresponding to this course
            List<Course> coursePath = model.getCoursePath(allCourses, courseCode);
            System.out.println("woguole");

            //this course does not exist in the all courses
            if (coursePath == null) {
                stuAddTakenCourseET.setError("CourseCode does not exist");
                stuAddTakenCourseET.requestFocus();
                return;
            }

            //check the student if have already taken this course
            model.getStudent(userID, (Student student) -> {
                System.out.println("Check the whether can take?");
                if (student.getTakenCourses().contains(courseCode)) {
                    stuAddTakenCourseET.setError("already taken");
                    stuAddTakenCourseET.requestFocus();
                    return;
                }

                // check if all the prerequisite have taken
                for (int i = 1; i < coursePath.size(); i++) {
                    System.out.println("Check the prerequisite");
                    if (!student.getTakenCourses().contains(coursePath.get(i).getCourseCode())) {
                        stuAddTakenCourseET.setError("Missing pre-course");
                        stuAddTakenCourseET.requestFocus();
                        return;
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Student_Add_TakenCourses.this);

                builder.setCancelable(true);
                builder.setTitle("Confirm the name of the New Course");
                String colored = "<font color = '#E10C0C'>" + courseCode + "</font>";
                builder.setMessage(Html.fromHtml(colored));


                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //satisfy above conditions then we just add it
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                                model.getStudent(userID, (Student curS) -> {
                                    curS.addTakenCourses(courseCode);
                                    model.saveStudent(curS ,userID, (Boolean success) -> {
                                        if (!success) {
                                            stuAddTakenCourseET.setError("failed to save info");
                                            stuAddTakenCourseET.requestFocus();
                                        } else {
//                                        Context context = getApplicationContext();
//                                        CharSequence text = "Successfully add this course!";
//                                        int duration = Toast.LENGTH_SHORT;
//                                        Toast toast = Toast.makeText(context, text, duration);
//                                        toast.show();
//                                        startActivity(new Intent(Student_Add_TakenCourses.this,
//                                                Student_Add_TakenCourses.class));
                                            Toast.makeText(Student_Add_TakenCourses.this,
                                                    "Course Added",
                                                    Toast.LENGTH_LONG).show();
                                        }


                                        Toast.makeText(Student_Add_TakenCourses.this,
                                                "Course Added",
                                                Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Student_Add_TakenCourses.this,
                                                StudentListDisplay.class));
                                    });
                                });
                            }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Student_Add_TakenCourses.this, "Fail to add course", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Student_Add_TakenCourses.this, Student_Add_TakenCourses.class));
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();
            });
        });



        /*
        System.out.println(mAuth.getCurrentUser().getUid());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());

        AlertDialog.Builder builder = new AlertDialog.Builder(Student_Add_TakenCourses.this);

        builder.setCancelable(true);
        builder.setTitle("Confirm the name of the New Course");
        String colored = "<font color = '#E10C0C'>" + CourseCode + "</font>";
        builder.setMessage(Html.fromHtml(colored));


        String finalCourseCode = CourseCode;
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ref.child("Taken Courses").child(finalCourseCode).setValue(finalCourseCode).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Student_Add_TakenCourses.this,
                                            "Course Added",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Student_Add_TakenCourses.this, Student_Add_TakenCourses.class));

                                } else {
                                    Toast.makeText(Student_Add_TakenCourses.this,
                                            "Fail to add course",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Student_Add_TakenCourses.this, Student_Add_TakenCourses.class));
                                }
                            }
                        });

                    }
                });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Student_Add_TakenCourses.this, "Fail to add course", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Student_Add_TakenCourses.this, Student_Add_TakenCourses.class));
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
        */

    }
}