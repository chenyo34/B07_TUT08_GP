package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AdminAddCourses extends AppCompatActivity implements View.OnClickListener {

    private EditText adminAddCourseName,adminAddCourseCode,adminAddPrereq;
    private Button adminAddCourseButton, adminAddCoursePrevious;
    private FirebaseAuth mAuth;
    private CheckBox checkWinter, checkSummer, checkFall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_courses);

        // EditText Fields Input-Extraction
        adminAddCourseCode = (EditText) findViewById(R.id.adminAddCourseCode);
        adminAddCourseName = (EditText) findViewById(R.id.adminAddCourseName);
        adminAddPrereq = (EditText) findViewById(R.id.adminAddPrereq);

        // Check the CheckBox
        checkFall = (CheckBox) findViewById(R.id.checkBoxFall);
        checkSummer =  (CheckBox) findViewById(R.id.checkBoxSummer);
        checkWinter = (CheckBox) findViewById(R.id.checkBoxWinter);

        mAuth = FirebaseAuth.getInstance();

        // Hold the buttons on current view
        adminAddCourseButton = (Button) findViewById(R.id.adminAddCourseButton);
        adminAddCourseButton.setOnClickListener(this);

        adminAddCoursePrevious = (Button) findViewById(R.id.adminAddCoursePrevious);
        adminAddCoursePrevious.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adminAddCoursePrevious:
                startActivity(new Intent(this, AdminActivity.class));
                break;
            case R.id.adminAddCourseButton:
                addCourse();
                break;
        }
    }

    private void addCourse() {
        String Coursename = adminAddCourseName.getText().toString().trim();
        String Coursecode = adminAddCourseCode.getText().toString().trim();
        String [] Prerequisite = adminAddPrereq.getText().toString().trim().
                replace(" ", "").split(",");

        // Check the Required for the new added Courses
        if(Coursename.isEmpty()) {
            adminAddCourseName.setError("CourseCode is required");
            adminAddCourseName.requestFocus();
            return;
        }

        if(Coursecode.isEmpty()){
            adminAddCourseCode.setError("CourseName is required!");
            adminAddCourseCode.requestFocus();
            return;
        }

        //Check and Fill the Offered sessions by checkboxes
        ArrayList<String> Offersessions = new ArrayList<>();

        if (checkFall.isChecked()) {
            Offersessions.add("Fall");
        }
        if (checkSummer.isChecked()) {
            Offersessions.add("Summer");
        }
        if (checkWinter.isChecked()) {
            Offersessions.add("Winter");
        }


        if (Offersessions.size() == 0) {

            checkFall.setError("");
            checkFall.requestFocus();

            checkSummer.setError("");
            checkSummer.requestFocus();

            checkWinter.setError("");
            checkWinter.requestFocus();

            Toast.makeText(AdminAddCourses.this,
                    "Please choose at least one session",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Collect the Precourses list
        ArrayList<String> Prercourses = new ArrayList<>(List.of(Prerequisite));


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("CurrentProvidedCourses");

        Course newCourse = new Course(Coursecode, Coursename, Offersessions, Prercourses);



        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddCourses.this);

        builder.setCancelable(true);
        builder.setTitle("Confirm information of New Course");
        builder.setMessage(newCourse.toString());
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myref.child(Coursecode).setValue(newCourse).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    // Actual Action after user's double-confirmed
                                    Toast.makeText(AdminAddCourses.this,
                                    "Course:" + Coursecode + "Added",
                                    Toast.LENGTH_LONG).show();
//
//                                    startActivity(new Intent(AdminAddCourses.this, AdminAddCourses.class));
                                } else {
                                    Toast.makeText(AdminAddCourses.this,
                                    "Fail to add current course.",
                                    Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });


        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AdminAddCourses.this,
                        "The operation has been cancelled.",
                        Toast.LENGTH_LONG
                        ).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();



//        myref.setValue(newCourse).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddCourses.this);
//
//                    builder.setCancelable(true);
//                    builder.setTitle("Title");
//                    builder.setMessage("Message");
//                    builder.setPositiveButton("Confirm",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Actual Action after user's double-confirmed
//                                    Toast.makeText(AdminAddCourses.this,
//                                            "Course:" + Coursecode + "Added",
//                                            Toast.LENGTH_LONG).show();
//
//                                    startActivity(new Intent(AdminAddCourses.this, AdminAddCourses.class));
//                                }
//                            });
//
//                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                } else {
//                    Toast.makeText(AdminAddCourses.this,
//                            "Fail to add current course.",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        });





    }
}