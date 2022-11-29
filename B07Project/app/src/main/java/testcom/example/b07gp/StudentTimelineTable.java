package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentTimelineTable extends AppCompatActivity implements View.OnClickListener{

    private Button stuTableReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline_table);

        stuTableReturn = (Button) findViewById(R.id.Timeline_table_return);
        stuTableReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Timeline_table_return:
                startActivity(new Intent(this, StudentTimelineDisplay.class));
                break;
        }
    }
}