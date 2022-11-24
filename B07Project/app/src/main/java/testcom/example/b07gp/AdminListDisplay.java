package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminListDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_display);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            //jump to related Admin class
            case R.id.admitCourseListDisplayPreviousButton:
                startActivity(new Intent(this, AdminActivity.class));
                break;

        }
    }
}