package testcom.example.b07gp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText TextName, TextEmail, TextPassWord;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    boolean isAdmin;

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

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        Button btnRegister = (Button) findViewById(R.id.register_user);
        btnRegister.setOnClickListener(this);

        CheckBox adminOrNot = (CheckBox) findViewById(R.id.checkboxRemember);
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
                isAdmin = !isAdmin;
                break;
        }
    }

    private void register() {
        //get the user's input
        String name = TextName.getText().toString().trim();
        String email = TextEmail.getText().toString().trim();
        String password = TextPassWord.getText().toString().trim();

        //check the violation
        if(name.isEmpty() && email.isEmpty() && password.isEmpty()) {
            TextName.setError("Name is required!");
            TextPassWord.setError("password is required!");
            TextEmail.setError("Email is required!");
            TextName.requestFocus();
            TextEmail.requestFocus();
            TextPassWord.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            TextName.setError("Name is required!");
            TextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            TextEmail.setError("Email is required!");
            TextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            TextEmail.setError("Please provide a valid email !");
            TextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            TextPassWord.setError("Please provide a password !");
            TextPassWord.requestFocus();
            return;
        }

        if (password.length() < 6) {
            TextPassWord.setError("Length of password should be at least 6 characters!");
            TextPassWord.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //now start to create the user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String type = (isAdmin) ? ("admin") : ("student");
                            User user = new User(name, email, password, type);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //set up the message
                                                CharSequence text = "User has been registered successfully";
                                                //set the duration time
                                                int duration = Toast.LENGTH_LONG;
                                                //get the Context
                                                Context register = RegisterActivity.this;
                                                //set up the toast
                                                Toast toast1 = Toast.makeText(register, text, duration);
                                                //make it show
                                                toast1.show();
                                                progressBar.setVisibility((View.GONE));
                                                Intent success = new Intent(RegisterActivity.this, MainActivity.class);
                                                RegisterActivity.this.startActivity(success);
                                            } else {
                                                //set up the message
                                                CharSequence txt = "Fail to create a user";
                                                //set the duration time
                                                int duration = Toast.LENGTH_LONG;
                                                //get the Context
                                                Context register = RegisterActivity.this;
                                                //set up the toast
                                                Toast toast2 = Toast.makeText(register, txt, duration);
                                                //make it show
                                                toast2.show();
                                                progressBar.setVisibility((View.GONE));
                                                Intent fail = new Intent(RegisterActivity.this, RegisterActivity.class);
                                                RegisterActivity.this.startActivity(fail);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Fail to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}