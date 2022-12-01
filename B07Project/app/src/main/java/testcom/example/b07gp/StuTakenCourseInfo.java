package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StuTakenCourseInfo extends AppCompatActivity {

    private TextView codeText, nameText, prerequisiteText, sessionText;
    String code;
    FirebaseDatabase database;
    DatabaseReference myref;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stu_taken_course_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codeText = findViewById(R.id.TakenCourseCodeTextView);
        nameText = findViewById(R.id.TakenCourseNameTextView);
        prerequisiteText = findViewById(R.id.TakenCoursePrerequisite);
        sessionText = findViewById(R.id.TakenCourseSession);

        code = getIntent().getStringExtra("code").trim();

        codeText.setText("  " + code);

        database = FirebaseDatabase.getInstance();
        myref = database.getReference("CurrentProvidedCourses");

        myref.orderByKey().equalTo(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Model model = new Model();
                    model.getCourseByCode(code, (Course c0) -> {
                        System.out.println("1");
                        String nameStr = "Course Name:\n" + c0.CourseName;
                        nameText.setText(nameStr);
                        System.out.println("2");
                        String preStr = "Prerequisite:\n" + c0.getPrecourses().toString()
                                .replace("[", "")
                                .replace("]", "");
                        System.out.println(c0.getPrecourses().toString()
                                .replace("[", "")
                                .replace("]", ""));
                        if(preStr.equals("Prerequisite:\n")){
                            prerequisiteText.setText("Prerequisite:\nNone");
                        }
                        else{
                            prerequisiteText.setText(preStr);
                        }
                        System.out.println("3");
                        String sesStr = "Offering Session:\n" + c0.getOfferingSessions().toString()
                                .replace("[", " ")
                                .replace("]", "");
                        sessionText.setText(sesStr);
                    });
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){

            }
        });


    }
}