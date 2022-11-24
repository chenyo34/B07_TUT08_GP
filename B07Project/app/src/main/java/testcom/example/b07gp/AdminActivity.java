package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //jump to related Admin class
            case R.id.adminActivityAddCourseButton:
                startActivity(new Intent(this, AdminAddCourses.class));
                break;
            case R.id.adminActivityEditDisplayButton:
                startActivity(new Intent(this, AdminEditDisplay.class));
                break;
            case R.id.adminActivityCourseListDisplayButton:
                startActivity(new Intent(this, AdminListDisplay.class));
                break;
            case R.id.adminActivityLogOutButton:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}