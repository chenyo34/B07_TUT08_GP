package testcom.example.b07gp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import testcom.example.b07gp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editPassword;
    private SharedPreferences.Editor editor;
    private Button btnLogIn;
    private CheckBox boxRememberMe;
    private TextView loginDescription;
    private FirebaseAuth mAuth;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogIn = (Button) findViewById(R.id.btnLogin);
        loginDescription = (TextView) findViewById(R.id.loginDescription);

//        checkSharedPreference();
        btnLogIn.setOnClickListener(this);
        loginDescription.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
//        presenter = new Presenter(new Model(), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginDescription:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.btnLogin:
                logIn();
                break;
        }
    }

    private void logIn(){
//        boolean rememberBox = false;
//        String email = "";
//        String password = "";
//
//        if(boxRememberMe.isChecked()){
//            rememberBox = true;
//            email = editEmail.getText().toString();
//            password = editPassword.getText().toString();
//        }

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();


        editor.putBoolean("rememberBox", boxRememberMe.isChecked());
        editor.putString("email", boxRememberMe.isChecked() ? email : "");
        editor.putString("password", boxRememberMe.isChecked() ? password : "");
        editor.apply();

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editPassword.setError("Minimum Password length is 6 characters");
            editPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //TO : DO
                    //startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}