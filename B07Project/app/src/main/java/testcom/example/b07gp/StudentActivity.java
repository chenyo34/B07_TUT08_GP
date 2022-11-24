package testcom.example.b07gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stuActivityAddCourseButton,stuActTakenCourseDisplayButton,
                    stuActLogOutButton,stuActGenerateTimelineButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        stuActivityAddCourseButton = (Button) findViewById(R.id.stuActAddCourseButton);
        stuActivityAddCourseButton.setOnClickListener(this);

        stuActTakenCourseDisplayButton = (Button) findViewById(R.id.stuActTakenCourseDisplayButton);
        stuActTakenCourseDisplayButton .setOnClickListener(this);

        stuActLogOutButton = (Button) findViewById(R.id.stuActLogOutButton);
        stuActLogOutButton.setOnClickListener(this);

        stuActGenerateTimelineButton = (Button) findViewById(R.id.stuActGenerateTimelineButton);
        stuActGenerateTimelineButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stuActLogOutButton:{
                startActivity(new Intent(this,MainActivity.class));
                break;
            }
            case R.id.stuActGenerateTimelineButton:{
                startActivity(new Intent(this,StudentTimelineDisplay.class));
                break;
            }
            case R.id.stuActTakenCourseDisplayButton:{
                startActivity(new Intent(this,StudentListDisplay.class));
                break;
            }
            case R.id.stuActAddCourseButton:{
                startActivity(new Intent(this,Student_Add_TakenCourses.class));
                break;
            }
        }
    }
}
