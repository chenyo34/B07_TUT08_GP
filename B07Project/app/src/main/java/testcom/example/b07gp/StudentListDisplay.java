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
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentListDisplay extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    //private Button stuAddTakenCourseButton;
    private ListView stuTakenCoursesLV;
    private ArrayList<String> myTakenCourses = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Model model;
//    Student student;
    String userID;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this,StudentActivity.class));
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
        setContentView(R.layout.activity_student_list_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

//        userID = getIntent().getStringExtra("userID"); // If you use the inherient from last page
        userID = mAuth.getCurrentUser().getUid();
        System.out.println(userID);

//        model.getStudent(userID, (Student student) -> {
//            System.out.println("I am getting the student by userID");
//            myTakenCourses = student.getTakenCourses();
//        });

        ;
//        System.out.println(test);

        model = Model.getInstance();
        stuTakenCoursesLV = (ListView) findViewById(R.id.studentTakenCourseListView);

        //stuAddTakenCourseButton = (Button) findViewById(R.id.studentToAddTakenCourseButton);;
        //stuAddTakenCourseButton.setOnClickListener(this);

        //courseListView();

        stuTakenCoursesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code = myTakenCourses.get(position);
                redirectToCourseInfo(code);
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myTakenCourses);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(userID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((Collection<? extends String>) snapshot.child("takenCourses").getValue() == null){
                    myTakenCourses = new ArrayList<>();
                } else {
                    adapter.addAll((Collection<? extends String>) snapshot.child("takenCourses").getValue());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//
//                myTakenCourses.add();
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                System.out.println("========================");
//                System.out.println(snapshot.child("takenCourses").getValue(String.class));
//                myTakenCourses.add(snapshot.child("takenCourses").getValue(String.class));
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
        });
        stuTakenCoursesLV.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.studentToAddTakenCourseButton:
                startActivity(new Intent(this, StuTakenCourseInfo.class));
                break;
        }*/
    }

    public void redirectToCourseInfo(String code) {
        Intent intent = new Intent(this, StuTakenCourseInfo.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }

    /*private void courseListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myTakenCourses);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Taken Courses");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myTakenCourses.add(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
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
        stuTakenCoursesLV.setAdapter(adapter);
    }*/

    public void FloatOnClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                Intent intent = new Intent(this, Student_Add_TakenCourses.class);
                intent.putExtra("userID", mAuth.getCurrentUser().getUid());
                startActivity(intent);
                break;
        }
    }
}