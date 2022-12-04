package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    private Button adminActLogOutButton, adminActCourseListDisplayButton,
            adminActEditDisplayButton, adminActAddCourseButton;
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
        setContentView(R.layout.activity_admin);

        adminActLogOutButton = (Button) findViewById(R.id.adminActLogOutButton);
        adminActLogOutButton.setOnClickListener(this);

        adminActCourseListDisplayButton = (Button) findViewById(R.id.adminActCourseListDisplayButton);
        adminActCourseListDisplayButton.setOnClickListener(this);

//        adminActEditDisplayButton = (Button) findViewById(R.id.adminActEditDisplayButton);
//        adminActEditDisplayButton.setOnClickListener(this);
//
//        adminActAddCourseButton = (Button) findViewById(R.id.adminActAddCourseButton);
//        adminActAddCourseButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mailText = (TextView) findViewById(R.id.adminMail);
        nameText = (TextView) findViewById(R.id.adminName);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();
       // Uid = "7B4qZVPdbuP0kbEisTgfXcaMdM72";
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
            case R.id.adminActLogOutButton:{
                startActivity(new Intent(this,MainActivity.class));
                break;
            }
            case R.id.adminActCourseListDisplayButton:{
                startActivity(new Intent(this,AdminListDisplay.class));
                break;
            }
//            case R.id.adminActEditDisplayButton:{
//                startActivity(new Intent(this,test1.class));
//                break;
//            }
//            case R.id.adminActAddCourseButton:{
//                startActivity(new Intent(this,AdminAddCourses.class));
//                break;
//            }
        }
    }
}