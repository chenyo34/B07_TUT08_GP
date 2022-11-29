package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StudentTimelineDisplay extends AppCompatActivity implements View.OnClickListener {

    private Button stuAddReturn, stuAddPlanCourse;
    private EditText plannCourse;
    private Model model;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline_display);

        stuAddReturn = (Button) findViewById(R.id.Return_button);
        stuAddReturn.setOnClickListener(this);


        stuAddPlanCourse = (Button) findViewById(R.id.timeline_add_button);
        stuAddPlanCourse.setOnClickListener(this);

        plannCourse = (EditText) findViewById(R.id.plannCourse);

        mAuth = FirebaseAuth.getInstance();
        model = new Model();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Return_button:
                startActivity(new Intent(this, StudentActivity.class));
                break;
            case R.id.timeline_add_button:
                System.out.println(mAuth.signInWithEmailAndPassword(""));
                model.getCourse(plannCourse, );
        }
    }
}