package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private EditText adminAddCourseName,adminAddCourseCode,adminAddOffering,adminAddPrereq;
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
        adminAddOffering = (EditText) findViewById(R.id.adminAddOffering);
        adminAddPrereq = (EditText) findViewById(R.id.adminAddPrereq);

        // Check the CheckBox
        


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
                Toast.makeText(AdminAddCourses.this,
                        adminAddCourseCode.getText().toString() + "Successfully",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void addCourse() {
        String Coursename = adminAddCourseName.getText().toString().trim();
        String Coursecode = adminAddCourseCode.getText().toString().trim();
        String [] Offerings = adminAddOffering.getText().toString().trim().
                replace(" ", "").split(",");
        String [] Prerequisite = adminAddPrereq.getText().toString().trim().
                replace(" ", "").split(",");

        ArrayList<String> Prercourses = new ArrayList<>(List.of(Prerequisite));
        ArrayList<String> Offersessions = new ArrayList<>(List.of(Offerings));

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


        if (Offersessions.size() == 0) {
            adminAddOffering.setError("Please provided a session!");
            adminAddOffering.requestFocus();
            return;
        }

        if (Offersessions.size() > 3) {
            adminAddOffering.setError("We only have Summer/Winter/Fall sessions!");
            adminAddOffering.requestFocus();
            return;
        }

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("CurrentProvidedCourses");

        Course newCourse = new Course(Coursecode, Coursename, Offersessions, Prercourses);




        myref.setValue(Coursecode).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    myref.child("Course Name").setValue(newCourse.getCourseName());
                    myref.child("Offering Session").setValue(newCourse.getOfferingSessions());
                    myref.child("Prerequisite").setValue(newCourse.getPrecourses());


                    Toast.makeText(AdminAddCourses.this,
                            "Course:" + Coursecode + "Added",
                            Toast.LENGTH_LONG).show();

                    startActivity(new Intent(AdminAddCourses.this, AdminAddCourses.class));
                } else {
                    Toast.makeText(AdminAddCourses.this,
                            "Fail to add current course.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}