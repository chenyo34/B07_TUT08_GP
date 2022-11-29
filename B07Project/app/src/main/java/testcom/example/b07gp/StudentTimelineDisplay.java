package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentTimelineDisplay extends AppCompatActivity implements View.OnClickListener {

    private Button stuAddReturn, stuAddSumbit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline_display);


        stuAddReturn = (Button) findViewById(R.id.timeline_return_button);
        stuAddReturn.setOnClickListener(this);

        stuAddSumbit = (Button) findViewById(R.id.timeline_submit_button);
        stuAddSumbit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.timeline_return_button:
                startActivity(new Intent(this, StudentActivity.class));
                break;
            case R.id.timeline_submit_button:
                startActivity(new Intent(this, StudentTimelineTable.class));
                break;

        }
    }
}