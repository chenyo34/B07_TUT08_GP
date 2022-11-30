package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicReference;

public class AdminEditDisplay extends AppCompatActivity implements View.OnClickListener{

    private Button changeSave, coursesDelete;
    EditText edTxtCode, edTxtName, edTxtPrep;
    CheckBox Summer, Fall, Winter;
    String code;
    Model model;


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
        setContentView(R.layout.activity_admin_edit_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edTxtCode = findViewById(R.id.adminEditTargetCourseCode);
        edTxtName = findViewById(R.id.adminEditTargetCourseName);
        edTxtPrep = findViewById(R.id.coursePre);

        changeSave = findViewById(R.id.ChangesSaver);
        changeSave.setOnClickListener(this);

        coursesDelete = findViewById(R.id.adminCourseDelete);
        coursesDelete.setOnClickListener(this);

        Summer = findViewById(R.id.checkBoxSummer);
        Fall = findViewById(R.id.checkBoxFall);
        Winter = findViewById(R.id.checkBoxWinter);

        code = getIntent().getStringExtra("code").trim();

        model = new Model();

        edTxtCode.setText(code);

        /*
        Yong Chen will modify this part soon
         */


//        System.out.println(FirebaseDatabase.getInstance().
//                getReference("CurrentProvidedCourses").child(code).child("CourseName").get());
//        model.getCourseByCode(code, (Course c0) -> {
//            System.out.println("here");
//            edTxtName.setText(c0.CourseName);
//            edTxtPrep.setText(c0.Precourses.toString());
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adminCourseDelete:
                model.DeletecourseByCode(code, this, (String deletecode) -> {
                    if (deletecode == null) {
                        deteleFailed();
                    } else {
                        deleteCompleted(deletecode);
                    }
            });
                break;
            case R.id.ChangesSaver:
                /*
                Working on the progress
                 */
//                model.changeCourseInfo(code, this, (Course c) -> {
//
//                });
                break;
        }
    }

    public void deleteCompleted(String deletecode){
        Intent intent = new Intent(AdminEditDisplay.this,AdminListDisplay.class);
        Toast.makeText(this,
                "Deleted" + deletecode + "Successfully",
                Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void deteleFailed(){
        Intent intent = new Intent(AdminEditDisplay.this,AdminListDisplay.class);
        Toast.makeText(this,
            "Fail to delete",
            Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}