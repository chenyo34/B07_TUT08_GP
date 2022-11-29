package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentListDisplay extends AppCompatActivity implements View.OnClickListener{

    private Button stuTakenCourseReturn;
    private Button stuAddTakenCourseButton;
    private ListView stuTakenCoursesLV;
    private FirebaseAuth mAuth;
    private ArrayList<String> myTakenCourses;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_display);

        mAuth = FirebaseAuth.getInstance();

        stuTakenCoursesLV = (ListView) findViewById(R.id.studentTakenCourseListView);

        stuAddTakenCourseButton = (Button) findViewById(R.id.studentToAddTakenCourseButton);;
        stuAddTakenCourseButton.setOnClickListener(this);

        stuTakenCourseReturn = (Button) findViewById(R.id.studentTakenCourseReturnButton);
        stuTakenCourseReturn.setOnClickListener(this);

        myTakenCourses = new ArrayList<>();

        courseListView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.studentTakenCourseReturnButton:
                startActivity(new Intent(this, StudentActivity.class));
                break;
            case R.id.studentToAddTakenCourseButton:
                startActivity(new Intent(this, Student_Add_TakenCourses.class));
                break;
        }
    }

    private void courseListView(){
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myTakenCourses);

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
    }
}