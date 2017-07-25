package com.example.gunesyurdakul.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class defaultUsers extends AppCompatActivity {
    Singleton singleton =Singleton.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project);

        if(!singleton.currentUser.admin){
            defaultUserProfilePage fragment = new defaultUserProfilePage();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, fragment).commit();
            transaction.commit();
        }

    }

}
