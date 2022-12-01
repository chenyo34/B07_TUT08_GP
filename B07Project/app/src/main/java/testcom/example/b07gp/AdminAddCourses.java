package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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
    private Button adminAddCourseButton;
    private FirebaseAuth mAuth;
    private CheckBox checkWinter, checkSummer, checkFall;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this,AdminListDisplay.class));
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
        setContentView(R.layout.activity_admin_add_courses);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adminAddCourseButton:
                addCourse();
                break;
        }
    }

    private void addCourse() {
        String Coursename = adminAddCourseName.getText().toString().toUpperCase().trim();
        String Coursecode = adminAddCourseCode.getText().toString().toUpperCase().trim();
        String [] Prerequisite = adminAddPrereq.getText().toString().toUpperCase().trim().
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


        String strOfferSession = "";
        for (String offsession: newCourse.OfferingSessions ) {
            System.out.println(offsession);
            strOfferSession += offsession + " ";
        }


        String strPrecourses = "";
        if ((newCourse.Precourses.size() == 1) && (newCourse.Precourses.get(0) == "")) {
            strPrecourses = "No Prerequisites are needed. ";
        } else {
            for (String precourse: newCourse.getPrecourses()) {
                strPrecourses += precourse + " ";
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddCourses.this);

        builder.setCancelable(true);
        builder.setTitle("Confirm information of New Course");
        String confirmInfo = "Course Code is: " + "<font color = '#E10C0C'>" + newCourse.getCourseCode() + "</font>" +
                "Course Name is: " + "<font color = '#E10C0C'>" + newCourse.getCourseName() + "</font>" +
                "It will be offered in " + "<font color = '#E10C0C'>" + strOfferSession + "</font>" + "Those are precourses:" +
                "<font color = '#E10C0C'>" + strPrecourses + "</font>";
        builder.setMessage(Html.fromHtml(confirmInfo));

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
                                    startActivity(new Intent(AdminAddCourses.this, AdminAddCourses.class));
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
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.DKGRAY);
    }
}