package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentTimelineDisplay2 extends AppCompatActivity implements View.OnClickListener{

   private ListView listView;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> selected_courses = new ArrayList<>();

    private DatabaseReference ref;

    private Button timeline_submit;

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
        setContentView(R.layout.activity_student_timeline_display2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentTimelineDisplay2.this, android.R.layout.simple_list_item_multiple_choice, arrayList);
        //  listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView = (ListView) findViewById(R.id.listview_data);
        listView.setAdapter(arrayAdapter);
        timeline_submit = (Button) findViewById(R.id.timeline_submit_button2);
        timeline_submit.setOnClickListener(this);

        ref = FirebaseDatabase.getInstance().getReference().child("CurrentProvidedCourses");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.child("courseCode").getValue(String.class);
             //   System.out.println(value);
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    public void onFabClick(View view) {
//        switch (view.getId()) {
//            case R.id.timeline_submit_button2:t:
//                startActivity(new Intent(this,StudentTimelineTable2.class));
//                break;
//        }
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timeline_submit_button2:t:
                for  (int i=0; i<listView.getCount(); i++){
                    String course = listView.getItemAtPosition(i).toString();
                    if (listView.isItemChecked(i) &&
                            !(selected_courses.contains(course))){
                        selected_courses.add(course);
                    }
                    else {
                        selected_courses.remove(listView.getItemAtPosition(i).toString());
                    }
                }
                Intent intent = new Intent(this,StudentTimelineTable2.class);
                intent.putStringArrayListExtra("Wanted_course", selected_courses);
                startActivity(intent);
                selected_courses.clear();
                break;
        }
    }
}