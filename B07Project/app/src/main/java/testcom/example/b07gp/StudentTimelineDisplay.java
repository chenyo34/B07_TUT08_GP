package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentTimelineDisplay extends AppCompatActivity implements View.OnClickListener {

    private Button stuAddReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline_display);


        stuAddReturn = (Button) findViewById(R.id.Return_button);
        stuAddReturn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Return_button:
                startActivity(new Intent(this, StudentActivity.class));
                break;

        }
    }
}