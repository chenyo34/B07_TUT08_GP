package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StudentTimelineDisplay extends AppCompatActivity implements View.OnClickListener {

    private Button stuAddSumbit;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stuAddSumbit = (Button) findViewById(R.id.timeline_submit_button);
        stuAddSumbit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.timeline_submit_button:
                startActivity(new Intent(this, StudentTimelineTable.class));
                break;

        }
    }
}