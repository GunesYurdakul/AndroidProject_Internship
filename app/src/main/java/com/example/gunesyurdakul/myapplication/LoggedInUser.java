package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class LoggedInUser extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gunesyurdakul.myapplication.MESSAGE";
    Singleton singleton =Singleton.getSingleton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);

    }

    public void goToEmployees(View view){
        Intent intent = new Intent(this,Employees.class);
        startActivity(intent);
    }

    public void goToProjects(View view){
        Intent intent = new Intent(this,Projects.class);
        startActivity(intent);
    }
}
