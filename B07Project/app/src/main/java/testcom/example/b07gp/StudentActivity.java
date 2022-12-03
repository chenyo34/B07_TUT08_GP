package testcom.example.b07gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stuActTakenCourseDisplayButton,
                    stuActLogOutButton,stuActGenerateTimelineButton;
    private String userID;

    private TextView mailText, nameText;
    private FirebaseAuth mAuth;
    private String adminMail, adminName;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        stuActTakenCourseDisplayButton = (Button) findViewById(R.id.stuActTakenCourseDisplayButton);
        stuActTakenCourseDisplayButton .setOnClickListener(this);

        stuActLogOutButton = (Button) findViewById(R.id.stuActLogOutButton);
        stuActLogOutButton.setOnClickListener(this);

        stuActGenerateTimelineButton = (Button) findViewById(R.id.stuActGenerateTimelineButton);
        stuActGenerateTimelineButton.setOnClickListener(this);

        userID = getIntent().getStringExtra("key");
        getIntent().putExtra("userID", userID);


        mAuth = FirebaseAuth.getInstance();
        mailText = (TextView) findViewById(R.id.adminMail);
        nameText = (TextView) findViewById(R.id.adminName);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminMail = snapshot.child(Uid).child("email").getValue(String.class);
                adminName = snapshot.child(Uid).child("name").getValue(String.class);

                mailText.setText(adminMail);
                nameText.setText(adminName+"   \uD83D\uDC4B");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stuActLogOutButton:{
                startActivity(new Intent(this,MainActivity.class));
                break;
            }
            case R.id.stuActGenerateTimelineButton:{

                Intent intent = new Intent(this,StudentTimelineDisplay2.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                break;
            }
            case R.id.stuActTakenCourseDisplayButton:{
                Intent intent = new Intent(this,StudentListDisplay.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                break;
            }
        }
    }
}
