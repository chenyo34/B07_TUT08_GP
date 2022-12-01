package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


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

import java.util.ArrayList;
import java.util.List;

public class Student_Add_TakenCourses extends AppCompatActivity implements View.OnClickListener {

    private Button stuAddTakenCourseButton, returnButton;
    private EditText stuAddTakenCourseET;
    private FirebaseAuth mAuth;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

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
            //case R.id.studentAddTakenCourseReturn:
                //startActivity(new Intent(this, StudentListDisplay.class));
                //break;
        }
    }

    private void addTakenCourse(){
        String CourseCode = stuAddTakenCourseET.getText().toString().trim().replace(" ","");
        CourseCode = CourseCode.toUpperCase();

        if(CourseCode.isEmpty()) {
            stuAddTakenCourseET.setError("CourseCode is required");
            stuAddTakenCourseET.requestFocus();
            return;
        }
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


    }
}