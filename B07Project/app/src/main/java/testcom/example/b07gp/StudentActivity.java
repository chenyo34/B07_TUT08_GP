package testcom.example.b07gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stuActTakenCourseDisplayButton,
                    stuActLogOutButton,stuActGenerateTimelineButton;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        stuActTakenCourseDisplayButton = (Button) findViewById(R.id.stuActTakenCourseDisplayButton);
        stuActTakenCourseDisplayButton .setOnClickListener(this);

        stuActLogOutButton = (Button) findViewById(R.id.stuActLogOutButton);
        stuActLogOutButton.setOnClickListener(this);

        stuActGenerateTimelineButton = (Button) findViewById(R.id.stuActGenerateTimelineButton);
        stuActGenerateTimelineButton.setOnClickListener(this);

        userID = getIntent().getStringExtra("key");
        getIntent().putExtra("userID", userID);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stuActLogOutButton:{
                startActivity(new Intent(this,MainActivity.class));
                break;
            }
            case R.id.stuActGenerateTimelineButton:{

                Intent intent = new Intent(this,StudentTimelineDisplay2.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                break;
            }
            case R.id.stuActTakenCourseDisplayButton:{
                Intent intent = new Intent(this,StudentListDisplay.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                break;
            }
        }
    }
}
