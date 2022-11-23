package testcom.example.b07gp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editPassword;
    private SharedPreferences.Editor editor;
    private Button btnLogIn;
    private CheckBox boxRememberMe;
    private TextView loginDescription;

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

//        mAuth = FirebaseAuth.getInstance();

//        presenter = new Presenter(new Model(), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginDescription:
                System.out.println("HERE_0");
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.btnLogin:
                logIn();
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
}