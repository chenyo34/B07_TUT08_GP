package testcom.example.b07gp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editPassword;
    private SharedPreferences preferences;
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

        boxRememberMe = (CheckBox) findViewById(R.id.checkboxRemember);
        preferences = getSharedPreferences("b07GroupProject", Context.MODE_PRIVATE);
        editor = preferences.edit();

        checkSharedPreference();
        btnLogIn.setOnClickListener(this);
        loginDescription.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

//        presenter = new Presenter(new Model(), this);

    }

    private void checkSharedPreference() {

        String remember = preferences.getString(getString(R.string.remember_me), "False");
        String email = preferences.getString(getString(R.string.email_address), "");
        String password = preferences.getString(getString(R.string.password), "");

        editEmail.setText(email);
        editPassword.setText(password);
        boxRememberMe.setChecked(remember.equals("True"));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginDescription:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.btnLogin:
                startActivity(new Intent(this, AdminActivity.class));
//                startActivity(new Intent(this, StudentActivity.class));
//                logIn();
                break;
        }
    }

    private void logIn(){
        boolean rememberBox = false;
        String email = "";
        String password = "";

        if(boxRememberMe.isChecked()){
            rememberBox = true;
            email = editEmail.getText().toString();
            password = editPassword.getText().toString();
        }

        editor.putBoolean("rememberBox", rememberBox);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    public void ToStudentPages(String UserID) {
        Intent intent = new Intent(MainActivity.this, StudentActivity.class);
        intent.putExtra(getString(R.string.UserKey),UserID);
        startActivity(intent);
    }

    public void ToAdminPages(String UserID) {
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        intent.putExtra(getString(R.string.UserKey),UserID);
        startActivity(intent);
    }
}