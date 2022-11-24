package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    private Button adminActLogOutButton, adminActCourseListDisplayButton,
            adminActEditDisplayButton, adminActAddCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminActLogOutButton = (Button) findViewById(R.id.adminActLogOutButton);
        adminActLogOutButton.setOnClickListener(this);

        adminActCourseListDisplayButton = (Button) findViewById(R.id.adminActCourseListDisplayButton);
        adminActCourseListDisplayButton.setOnClickListener(this);

        adminActEditDisplayButton = (Button) findViewById(R.id.adminActEditDisplayButton);
        adminActEditDisplayButton.setOnClickListener(this);

        adminActAddCourseButton = (Button) findViewById(R.id.adminActAddCourseButton);
        adminActAddCourseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.adminActLogOutButton:{
                startActivity(new Intent(this,MainActivity.class));
                break;
            }
            case R.id.adminActCourseListDisplayButton:{
                startActivity(new Intent(this,AdminListDisplay.class));
                break;
            }
            case R.id.adminActEditDisplayButton:{
                startActivity(new Intent(this,AdminEditDisplay.class));
                break;
            }
            case R.id.adminActAddCourseButton:{
                startActivity(new Intent(this,AdminAddCourses.class));
                break;
            }
        }
    }
}