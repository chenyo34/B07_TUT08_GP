package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminListDisplay extends AppCompatActivity implements View.OnClickListener {
    private Button previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_display);

        previous = (Button) findViewById(R.id.admitCourseListDisplayPreviousButton);
        previous.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.admitCourseListDisplayPreviousButton:
                startActivity(new Intent(this, AdminActivity.class));
                break;
        }
    }
}