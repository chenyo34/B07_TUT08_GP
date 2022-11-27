package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{


    private TextView signup;
    private Button btnRegister;
    private EditText TextName, TextEmail, TextPassWord;
    private ProgressBar progressBar;
    private CheckBox adminOrNot;
    private FirebaseAuth mAuth;

    boolean ornot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        checkBoxAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                editTextBirthOrSpecialty.setText("");
//            }
//        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.register_user);
        btnRegister.setOnClickListener(this);

        adminOrNot = (CheckBox) findViewById(R.id.checkboxRemember);
        adminOrNot.setOnClickListener(this);

        TextName = (EditText) findViewById(R.id.editTextFullName);
        TextEmail = (EditText) findViewById(R.id.editTextEmail);
        TextPassWord = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.register_user:
                register();
                break;
            case R.id.checkboxRemember:
                if(ornot == true) ornot = false;
                else ornot = true;
                break;
        }
    }

    private void register() {
        //get user's input
        String name = TextName.getText().toString().trim();
        String email = TextEmail.getText().toString().trim();
        String password = TextPassWord.getText().toString().trim();

        // validate
        if (name.isEmpty()) {
            TextName.setError("Name is required");
            TextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            TextEmail.setError("Email is required");
            TextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            TextPassWord.setError("Password is required");
            TextPassWord.requestFocus();
            return;
        }

        if (password.length() < 6) {
            TextPassWord.setError("Minimum Password length is 6 characters");
            TextPassWord.requestFocus();
            return;
        }
        //end of validate
        progressBar.setVisibility(View.VISIBLE);



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if it is successful
                        if (task.isSuccessful()) {
                            String type = "";
                            type = (ornot) ? ("admin") : ("student");
                            User user = new User(name, email, password, type);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity2.this, "user has been registered successfully", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility((View.GONE));

                                                //redirect to the login page
                                                startActivity(new Intent(MainActivity2.this, MainActivity.class));
                                            } else {
                                                Toast.makeText(MainActivity2.this, "Fail to create a user", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility((View.GONE));
                                                startActivity(new Intent(MainActivity2.this, MainActivity2.class));
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(MainActivity2.this, "Fail to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
//    private void register() {
//
//        FirebaseDatabase.getInstance().getReference("1123-1046").setValue("test");
//
//        String name = TextName.getText().toString().trim();
//        String email = TextPassWord.getText().toString().trim();
//        String password = TextPassWord.getText().toString().trim();
//
//        // validate
//        if (name.isEmpty()) {
//            TextName.setError("Name is required");
//            TextName.requestFocus();
//            return;
//        }
//
//        if (email.isEmpty()) {
//            TextEmail.setError("Email is required");
//            TextEmail.requestFocus();
//            return;
//        }
//
//        if(password.isEmpty()) {
//            TextPassWord.setError("Password is required");
//            TextPassWord.requestFocus();
//            return;
//        }
//
////        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
////            TextEmail.setError("Please provide valid email!");
////            TextEmail.requestFocus();
////            return;
////        }
//
//        if (password.isEmpty()) {
//            TextPassWord.setError("Password is required!");
//            TextPassWord.requestFocus();
//            return;
//        }
//
//        if (password.length() < 6) {
//            TextPassWord.setError("Min password length should be 6 characters!");
//            TextPassWord.requestFocus();
//            return;
//        }
//
//        progressBar.setVisibility(View.VISIBLE);

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (!task.isSuccessful()){
//                            Toast.makeText(MainActivity2.this, "Failed to register!", Toast.LENGTH_LONG).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                        else{
//
//                            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//
//
//                            FirebaseDatabase.getInstance()
//                                    .getReference("UserTypes").child(email)
//                                    .child("userType")
//                                    .setValue(checkBoxAdmin.isChecked() ? "Admin" : "Student");
////                            User user;
////                            String ref;
//                            User user;
//                            String ref;
//
//
//                            if (!checkBoxAdmin.isChecked()){
//                                ref = "Student";
//                                user = new Student(email, name);
//
//                            }
//                            else{
//                                ref = "Admin";
//                                user = new Admin(email, name);
//
//                            }
//
//                            DatabaseReference reference = FirebaseDatabase.getInstance()
//                                    .getReference(ref).child(email);
//
//                            reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (!task.isSuccessful()) {
//                                        Toast.makeText(MainActivity2.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                    else {
//
//                                        Toast.makeText(MainActivity2.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//                                        startActivity(new Intent(MainActivity2.this, MainActivity.class));
//
//                                    }
//                                }
//                            });

//                        }
//                    }
//                });
}