package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class StudentTimelineTable2 extends AppCompatActivity {

    private ListView listView;

    private FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_student_timeline_table2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ArrayList<String> arrayList = intent.getStringArrayListExtra("Wanted_course");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentTimelineTable2.this, android.R.layout.simple_list_item_1, arrayList);

       listView = (ListView) findViewById(R.id.listview_table);
       listView.setAdapter(arrayAdapter);
//        listView.setAdapter(arrayAdapter);
    }
}