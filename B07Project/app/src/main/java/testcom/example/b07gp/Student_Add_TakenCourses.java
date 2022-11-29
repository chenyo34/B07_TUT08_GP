package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Student_Add_TakenCourses extends AppCompatActivity implements View.OnClickListener {

    private Button previous;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_taken_courses);

        previous = (Button) findViewById(R.id.timeline_return_button);
        previous.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeline_return_button:
                startActivity(new Intent(this, StudentActivity.class));
                break;
        }
    }
}