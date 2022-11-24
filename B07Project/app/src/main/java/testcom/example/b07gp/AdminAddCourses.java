package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.List;

public class AdminAddCourses extends AppCompatActivity implements View.OnClickListener {

    private EditText adminAddCourseName,adminAddCourseCode,adminAddOffering,adminAddPrereq;
    private Button adminAddCourseButton, adminAddCoursePrevious;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_courses);

        adminAddCourseCode = (EditText) findViewById(R.id.adminAddCourseCode);
        adminAddCourseName = (EditText) findViewById(R.id.adminAddCourseName);
        adminAddOffering = (EditText) findViewById(R.id.adminAddOffering);
        adminAddPrereq = (EditText) findViewById(R.id.adminAddPrereq);

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

        HashSet<String> Prercourses = new HashSet<>(List.of(Prerequisite));
        HashSet<String> Offersessions = new HashSet<>(List.of(Offerings));

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


    }
}