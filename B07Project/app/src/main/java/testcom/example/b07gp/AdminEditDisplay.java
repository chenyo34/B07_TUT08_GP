package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AdminEditDisplay extends AppCompatActivity implements View.OnClickListener{

    private Button changeSave, coursesDelete, ShowInfo;
    EditText edTxtName, edTxtPrep;
    TextView edTxtCode;
    CheckBox Summer, Fall, Winter;
    String code;
    Model model;
    Course oldCourse;


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
        setContentView(R.layout.activity_admin_edit_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edTxtCode = findViewById(R.id.adminEditTargetCourseCode);
        edTxtName = findViewById(R.id.adminEditTargetCourseName);
        edTxtPrep = findViewById(R.id.coursePre);

        changeSave = findViewById(R.id.ChangesSaver);
        changeSave.setOnClickListener(this);

        coursesDelete = findViewById(R.id.adminCourseDelete);
        coursesDelete.setOnClickListener(this);

        ShowInfo = findViewById(R.id.ShowInfoBt);
        ShowInfo.setOnClickListener(this);

        Summer = findViewById(R.id.checkBoxSummer);
        Fall = findViewById(R.id.checkBoxFall);
        Winter = findViewById(R.id.checkBoxWinter);

        code = getIntent().getStringExtra("code").trim();
        edTxtCode.setText(code);
        String oriName = String.valueOf(FirebaseDatabase.getInstance().getReference("CurrentProvidedCourses").child(code).child("courseName").get().toString());
        System.out.println(oriName);
        model = new Model();

        oldCourse = new Course();
        oldCourse.setCourseCode(code);
        model.getCourseByCode(code, (Course oldC) -> {
            oldCourse.setCourseName(oldC.getCourseName());
            System.out.println("Yoooooooo!!!!" + oldCourse.getCourseName());
            oldCourse.setPrecourses(oldC.getPrecourses());
            oldCourse.setOfferingSessions(oldC.getOfferingSessions());
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.adminCourseDelete:
                model.DeletecourseByCode(code, this, (String deletecode) -> {
                    if (deletecode == null) {
                        deteleFailed();
                    } else {
                        deleteCompleted(deletecode);
                        model.DeleteCourseUpdate(deletecode, (String dc) ->{
                        });
                    }
                });
                break;
            case R.id.ShowInfoBt:
                System.out.println("inside previous button");
                model.getCourseByCode(code, (Course c0) -> {
                    System.out.println("1");
                    edTxtName.setText(c0.CourseName);
                    System.out.println("2");
                    System.out.println(c0);
                    edTxtPrep.setText(c0.getPrecourses().toString()
                            .replace("[", " ")
                            .replace("]", ""));
                    System.out.println("3");
                    if (c0.getOfferingSessions().contains("Winter")) {
                        Winter.setChecked(true);
                    }

                    if (c0.getOfferingSessions().contains("Summer")) {
                        Summer.setChecked(true);
                    }

                    if (c0.getOfferingSessions().contains("Fall")) {
                        Fall.setChecked(true);
                    }
                });
                break;
            case R.id.ChangesSaver:
                changeCourseInfo();
                break;
        }
    }

    public void deleteCompleted(String deletecode){
        Intent intent = new Intent(AdminEditDisplay.this,AdminListDisplay.class);
        Toast.makeText(this,
                "Deleted" + deletecode + "Successfully",
                Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void deteleFailed(){
        Intent intent = new Intent(AdminEditDisplay.this,AdminListDisplay.class);
        Toast.makeText(this,
            "Fail to delete",
            Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void changeCourseInfo() {

        //Deal with the Updated OffSessions
        ArrayList<String> offSessions = new ArrayList<>();
        if (Winter.isChecked()) {
            offSessions.add("Winter");
        } else if (Fall.isChecked()) {
            offSessions.add("Fall");
        } else if (Summer.isChecked()) {
            offSessions.add("Summer");
        }


        // Deal with the Updated Precourses
        String[] Prerequisite = edTxtPrep.getText().toString()
                    .toUpperCase()
                    .trim()
                    .replace(" ", "")
                    .split(",");

        System.out.println(Arrays.toString(Prerequisite));

        // Deal with the Udpated Coursename
        String Coursename = edTxtName.getText().toString().toUpperCase().trim();

        // Check the Required for the new added Courses
        if(Coursename.isEmpty()) {
            edTxtName.setError("CourseCode is required");
            edTxtName.requestFocus();
            return;
        }

        //Check and Updated the Offered sessions by checkboxes
        ArrayList<String> Offersessions = new ArrayList<>();

        if (Fall.isChecked()) {
            Offersessions.add("Fall");
        }
        if (Summer.isChecked()) {
            Offersessions.add("Summer");
        }
        if (Winter.isChecked()) {
            Offersessions.add("Winter");
        }


        if (Offersessions.size() == 0) {

            Fall.setError("");
            Fall.requestFocus();

            Summer.setError("");
            Summer.requestFocus();

            Winter.setError("");
            Winter.requestFocus();

            Toast.makeText(AdminEditDisplay.this,
                    "Please choose at least one session",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Collect the Precourses list
        ArrayList<String> Prercourses = new ArrayList<>(List.of(Prerequisite));


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("CurrentProvidedCourses");

        System.out.println(oldCourse);
        Course updatedCourse = new Course(code, Coursename, Offersessions, Prercourses);
        System.out.println(updatedCourse);

        ArrayList<String> changes = new ArrayList<>();
        changes = oldCourse.modify(updatedCourse);
        System.out.println(changes);
        String codeChange = "";
        String nameChange = "";
        String preChange = "";
        String sesChange = "";
        if(!changes.get(0).equals("")){
            codeChange = "Course Code changed: " + "\n" + "<font color = '#E10C0C'>" + changes.get(0) + "</font>"+"\n";
        }
        if(!changes.get(1).equals("")){
            nameChange = "Course Name changed: " + "\n" + "<font color = '#E10C0C'>" + changes.get(1) + "</font>"+"\n";
        }
        if(!changes.get(2).equals("")){
            preChange = "Course Prerequisite changed: " + "\n" + "<font color = '#E10C0C'>" + changes.get(2) + "</font>"+ "\n";
        }
        if(!changes.get(3).equals("")){
            sesChange = "Course Offering Session changed: " + "\n" + "<font color = '#E10C0C'>" + changes.get(3) + "</font>" + "\n";
        }
        String mess = codeChange + nameChange + preChange + sesChange;
        System.out.println(mess);


        AlertDialog.Builder builder = new AlertDialog.Builder(AdminEditDisplay.this);

        String strPrecourses = "";
        if ((updatedCourse.Precourses.size() == 1) && (updatedCourse.Precourses.get(0) == "")) {
            strPrecourses = "No Prerequisites are needed. ";
        } else {
            for (String precourse: updatedCourse.getPrecourses()) {
                strPrecourses += precourse + " ";
            }
        }

        builder.setCancelable(true);
        builder.setTitle("Update Info of "+ code + " Course");
        String confirmInfo =  "Course Name is: "
                + "<font color = '#E10C0C'>" + updatedCourse.getCourseName() + "</font>"
                + "It will be offered in "
                +  "<font color = '#E10C0C'>" + Offersessions + "</font>"
                + "Those are precourses:"
                + "<font color = '#E10C0C'>" + strPrecourses + "</font>";

        builder.setMessage(Html.fromHtml(mess));

        builder.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myref.child(code).setValue(updatedCourse).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            // Actual Action after user's double-confirmed
                                            Toast.makeText(AdminEditDisplay.this,
                                                    "Course:" + code + "Added",
                                                    Toast.LENGTH_LONG).show();
//
                                            startActivity(new Intent(AdminEditDisplay.this, AdminListDisplay.class));
                                        } else {
                                            Toast.makeText(AdminEditDisplay.this,
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
                Toast.makeText(AdminEditDisplay.this,
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