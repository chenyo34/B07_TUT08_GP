package testcom.example.b07gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{


    private TextView signup;
    private Button btnRegister;
    private EditText TextName, TextEmail, TextPassWord;

    private ProgressBar progressBar;


    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        btnRegister = (Button) findViewById(R.id.register_user);
        btnRegister.setOnClickListener(this);

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
        }
    }

    private void register() {
        String name = TextName.getText().toString().trim();
        String email = TextPassWord.getText().toString().trim();
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

        if(password.isEmpty()) {
            TextPassWord.setError("Password is required");
            TextPassWord.requestFocus();
            return;
        }


    }
}