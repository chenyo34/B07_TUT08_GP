package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StudentTimelineTable extends AppCompatActivity {

    private Button stuTableReturn;

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timeline_table);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        stuTableReturn = (Button) findViewById(R.id.Timeline_table_return);
//        stuTableReturn.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.Timeline_table_return:
//                startActivity(new Intent(this, StudentTimelineDisplay.class));
//                break;
//        }
//    }
}