package com.example.courseplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.Preference;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.courseplanner.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

//    private AppBarConfiguration appBarConfiguration;
//    private ActivityMainBinding binding;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText editEmail, editPassword;

    private Button btnLogIn;

    private CheckBox boxRememberMe;
    private TextView tvRegister;
    private TextView tvAdminLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        //setSupportActionBar(binding.toolbar);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogIn = (Button) findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(this);

        boxRememberMe = (CheckBox) findViewById(R.id.checkboxRemember);
        tvRegister = (TextView) findViewById(R.id.loginDescription);
        tvRegister.setOnClickListener(this);
        tvAdminLogin = (TextView) findViewById(R.id.adminLogin);
        tvAdminLogin.setOnClickListener(this);

        preferences = getSharedPreferences("b07", Context.MODE_PRIVATE);
        editor = preferences.edit();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        checkSharedPreferences();
    }

    private void checkSharedPreferences() {
        boolean remember = preferences.getBoolean("remember", false);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        editEmail.setText(email);
        editPassword.setText(password);
        boxRememberMe.setChecked(remember);
    }

    @Override
    public void onClick(View view) {
        //choose which button I click by using switch case Edior: Haoqian
        switch (view.getId()){
            case R.id.btnLogin:
                logIn();
                break;
            case R.id.loginDescription:
                startActivity(new Intent(this, RegisterActivity.class));
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

    //useless given by default I think editor:Haoqian
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}