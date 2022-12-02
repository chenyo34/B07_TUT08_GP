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
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editPassword;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button btnLogIn;
    private Button showHide;
    private TextView loginDescription;
    private FirebaseAuth mAuth;

    private Model model;
    private Presenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogIn = (Button) findViewById(R.id.btnLogin);
        //showHide = (Button) findViewById(R.id.showHideBtn);
        loginDescription = (TextView) findViewById(R.id.loginDescription);
        preferences = getSharedPreferences("b07GroupProject", Context.MODE_PRIVATE);
        editor = preferences.edit();
        checkSharedPreference();
        btnLogIn.setOnClickListener(this);
        loginDescription.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        model = Model.getInstance();
        presenter = new Presenter(new Model(), this);
    }

    private void checkSharedPreference() {
        String remember = preferences.getString(getString(R.string.remember_me), "False");
        String email = preferences.getString(getString(R.string.email_address), "");
        String password = preferences.getString(getString(R.string.password), "");
        editEmail.setText(email);
        editPassword.setText(password);
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
        //set the initial value for user input
        String email = "";
        String password = "";

        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        //editor.putBoolean("rememberBox", rememberBox);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();

        //call presenter
        presenter.login(email, password);
    }

    //redirect to student page
    public void ToStudentPages(String UTORid) {
        Intent intent = new Intent(MainActivity.this, StudentActivity.class);
        intent.putExtra(getString(R.string.UserKey),UTORid);
        startActivity(intent);
    }

    //redirect to admin page
    public void ToAdminPages(String UTORid) {
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        intent.putExtra(getString(R.string.UserKey),UTORid);
        startActivity(intent);
    }

    //failed to log in
    public void failedToLogin() {
        CharSequence text = "Failed to log in";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}