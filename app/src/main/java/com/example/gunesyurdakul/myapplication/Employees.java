package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Employees extends AppCompatActivity {
    Singleton singleton =Singleton.getSingleton();
    TableLayout table;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(myToolbar);

        EmployeeFragment fragment = new EmployeeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, fragment).commit();
        transaction.commit();

    }

    public class Employee_View {
        TextView id;
        TextView name;
        TextView department;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                writeJSON();
                Intent intent = new Intent(this,mainpage.class);
                startActivity(intent);
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.password:
                changePassword detailsFragment = new changePassword();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                detailsFragment.setArguments(args);
                ft.addToBackStack("dsf");
                ft.replace(R.id.fragment_layout, detailsFragment);
                ft.commit();
            case R.id.EN:
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                startActivity(new Intent(Employees.this,Employees.class));
                finish();
                return true;
            case R.id.TR:
                String language = "tr"; // your language
                Locale locale_tr = new Locale(language);
                Locale.setDefault(locale_tr);
                Configuration config_tr = new Configuration();
                config_tr.locale = locale_tr;
                getBaseContext().getResources().updateConfiguration(config_tr,
                        getBaseContext().getResources().getDisplayMetrics());
                startActivity(new Intent(Employees.this,Employees.class));
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void writeJSON(){
        Log.d("write","write json");
        JSONArray employees=new JSONArray();
        JSONObject obj;

        //for(Employee i:singleton.Employees){
        Gson gson = new Gson();
        File file = new File(getFilesDir()+ "/objfile.json");
        Log.d("Path",file.getAbsolutePath());


        try {
            Writer writer = new FileWriter(getFilesDir()+ "/objfile.json");
            gson = new GsonBuilder().create();
            gson.toJson(singleton.employeeMap, writer);
            String str=gson.toJson(singleton.employeeMap);
            System.out.println(str);
            writer.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }

        try {
            Writer writer = new FileWriter(getFilesDir()+ "/objfilep.json");
            gson = new GsonBuilder().create();
            gson.toJson(singleton.projectMap, writer);
            String str=gson.toJson(singleton.projectMap);
            System.out.println(str);
            writer.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }


        try {
            Writer writer = new FileWriter(getFilesDir()+ "/objfilet.json");
            gson = new GsonBuilder().create();
            gson.toJson(singleton.taskMap, writer);
            String str=gson.toJson(singleton.taskMap);
            System.out.println(str);
            writer.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }





    }
}
