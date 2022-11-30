package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminEditDisplay extends AppCompatActivity implements View.OnClickListener{

    private Button previous;
    EditText edTxtCode, edTxtName;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_display);
        edTxtCode = findViewById(R.id.adminEditTargetCourseCode);
        edTxtName = findViewById(R.id.adminEditTargetCourseName);

        code = getIntent().getStringExtra("code");
        // model.getCourseByCode(code, (Course c) -> {
        //      edTxtName.setText(c.name);
        // });

        edTxtCode.setText(code);

        previous = (Button) findViewById(R.id.admitCourseListDisplayPreviousButton);
        previous.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.admitCourseListDisplayPreviousButton:
                startActivity(new Intent(this, AdminActivity.class));
        }
    }
}