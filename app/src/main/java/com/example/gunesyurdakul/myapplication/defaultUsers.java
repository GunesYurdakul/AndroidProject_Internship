package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class defaultUsers extends AppCompatActivity {
    Singleton singleton =Singleton.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project);
        if(singleton.currentUser.admin==false) {
            readFile();
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        setSupportActionBar(myToolbar);

            defaultUserProfilePage fragment = new defaultUserProfilePage();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, fragment).commit();
            transaction.commit();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                ft.replace(R.id.fragment_layout, detailsFragment);
                ft.commit();
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



    }
    void readFile(){

        try {
            Gson rson=new Gson();
            Reader reader = new FileReader(getFilesDir()+ "/objfile.json");
            rson = new GsonBuilder().create();
            singleton.employeeMap=rson.fromJson(reader,new TypeToken<Map<Integer,Employee>>(){}.getType());
            //String str=gson.toJson(singleton.employeeMap);
            System.out.println(singleton.employeeMap);

            for (Map.Entry<Integer, Employee> pair : singleton.employeeMap.entrySet()) {

                Employee currentEmployee = pair.getValue();
                for (Map.Entry<Integer, Task> a : currentEmployee.tasks.entrySet()) {
                    Task currentTask = a.getValue();
                    singleton.taskMap.put(currentTask.task_id, currentTask);
                }

            }

            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }


        try {
            Gson rson=new Gson();
            Reader reader = new FileReader(getFilesDir()+ "/objfilet.json");
            rson = new GsonBuilder().create();
            singleton.taskMap=rson.fromJson(reader,new TypeToken<Map<Integer,Task>>(){}.getType());
            //String str=gson.toJson(singleton.employeeMap);
            System.out.println(singleton.taskMap);
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            Gson rson=new Gson();
            Reader reader = new FileReader(getFilesDir()+ "/objfilep.json");
            rson = new GsonBuilder().create();
            singleton.projectMap=rson.fromJson(reader,new TypeToken<Map<Integer,Project>>(){}.getType());
            //String str=gson.toJson(singleton.employeeMap);
            System.out.println(singleton.projectMap);
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
