package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminListDisplay extends AppCompatActivity implements View.OnClickListener {
    private Button addCourse;

    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseRefer;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this,AdminActivity.class));;
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        addCourse = (Button) findViewById(R.id.adminListDisplayAddCourseButton);
//        addCourse.setOnClickListener(this);
//
//        addCourse = (Button) findViewById(R.id.adminListDisplayAddCourseButton);

        listView = (ListView) findViewById(R.id.adminListDisplayListview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code = arrayList.get(position);
                redirectToAdminEdit(code);
            }
        });


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayList);

        database = FirebaseDatabase.getInstance();
        databaseRefer = database.getReference("CurrentProvidedCourses");

        databaseRefer.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                System.out.println("here");
//                System.out.println(snapshot.getKey());
                arrayList.add("    " + snapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //arrayList.add(snapshot.getValue(String.class));
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                //arrayList.add(snapshot.getValue(String.class));
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        listView.setAdapter(adapter);


    }

    public void redirectToAdminEdit(String code) {
        Intent intent = new Intent(this, AdminEditDisplay.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.adminListDisplayAddCourseButton:
//                startActivity(new Intent(this,AdminAddCourses.class));
//                break;
//        }
    }

    public void onFabClick(View view) {
        switch (view.getId()) {
            case R.id.test:
                startActivity(new Intent(this,AdminAddCourses.class));
                break;
        }

    }
}