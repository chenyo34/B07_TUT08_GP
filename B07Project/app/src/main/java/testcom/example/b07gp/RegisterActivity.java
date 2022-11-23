package testcom.example.b07gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private TextView signup;
    private Button btnRegister;
    private EditText TextName, TextEmail, TextPassWord;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("HERE_1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        signup = (TextView) findViewById(R.id.signup);
//        signup.setOnClickListener(this);

//        btnRegister = (Button) findViewById(R.id.register_user);
//        btnRegister.setOnClickListener(this);
//
//        TextName = (EditText) findViewById(R.id.editTextFullName);
//        TextEmail = (EditText) findViewById(R.id.editTextEmail);
//        TextPassWord = (EditText) findViewById(R.id.password);

//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);

    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.signup:
//                startActivity(new Intent(this, MainActivity.class));
//                break;
//            case R.id.register_user:
//                register();
//                break;
//        }
//    }

//    public void register(){
//        String full_name = TextName.getText().toString().trim();
//        String email = TextEmail.getText().toString().trim();
//        String password = TextPassWord.getText().toString().trim();
//
//        //validate
//        if(full_name.isEmpty()){
//            TextName.setError("Name is required");
//            TextName.requestFocus();
//            return;
//        }
//    }

//    public void redirectToPatientDashboard(String userID) {
//        Intent intent = new Intent(MainActivity.this, PatientDashboardActivity.class);
//        intent.putExtra(getString(R.string.user_key), userID);
//        startActivity(intent);
//    }
//
//    public void redirectToDoctorDashboard(String userID) {
//        Intent intent = new Intent(MainActivity.this, DoctorDashboardActivity.class);
//        intent.putExtra(getString(R.string.user_key), userID);
//        startActivity(intent);
//    }
}
