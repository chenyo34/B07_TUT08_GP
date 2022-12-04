package testcom.example.b07gp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail, editPassword;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText) findViewById(R.id.editTextEmail);

        editPassword = (EditText) findViewById(R.id.editTextPassword);

        Button btnLogIn = (Button) findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(this);

        CheckBox showPassword = (CheckBox) findViewById(R.id.showPassword);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        TextView loginDescription = (TextView) findViewById(R.id.loginDescription);
        loginDescription.setOnClickListener(this);

        preferences = getSharedPreferences("b07GroupProject", Context.MODE_PRIVATE);
        editor = preferences.edit();
        checkSharedPreference();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Model model = Model.getInstance();

        presenter = new Presenter(new Model(), this);
    }

    private void checkSharedPreference() {
        String email = preferences.getString(getString(R.string.email_address), "");
        String password = preferences.getString(getString(R.string.password), "");
        editEmail.setText(email);
        editPassword.setText(password);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginDescription:
                startActivity(new Intent(this, RegisterActivity.class));
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

        //violation
        if(email.isEmpty() && password.isEmpty()) {
            editEmail.setError("email is required!");
            editPassword.setError("password is required!");
            editEmail.requestFocus();
            editPassword.requestFocus();
        }

        if (email.isEmpty()) {
            editEmail.setError("email is required!");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("password is required!");
            editPassword.requestFocus();
            return;
        }

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