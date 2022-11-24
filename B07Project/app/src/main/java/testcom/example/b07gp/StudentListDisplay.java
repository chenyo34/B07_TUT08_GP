package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentListDisplay extends AppCompatActivity implements View.OnClickListener{

    private Button studentTakenCourseDisplayReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_display);

        studentTakenCourseDisplayReturn = (Button) findViewById(R.id.studentTakenCourseDisplayReturn);
        studentTakenCourseDisplayReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.studentTakenCourseDisplayReturn:
                startActivity(new Intent(this, StudentActivity.class));
                break;
        }
    }
}