package com.example.gunesyurdakul.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class LoggedInUser extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gunesyurdakul.myapplication.MESSAGE";
    Singleton singleton =Singleton.getSingleton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }

    public void goToEmployees(View view){
        Intent intent = new Intent(this,Employees.class);
        startActivity(intent);
    }

    public void goToProjects(View view){
        Intent intent = new Intent(this,Projects.class);
        startActivity(intent);
    }

    public void goToProfile(View v){
        Intent intent = new Intent(this,defaultUsers.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(this,mainpage.class);
                startActivity(intent);
                // User chose the "Settings" item, show the app settings UI...
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


    void readFile(){

        try {
            Gson rson=new Gson();
            Reader reader = new FileReader(getFilesDir()+ "/objfile.json");
            rson = new GsonBuilder().create();
            singleton.employeeMap=rson.fromJson(reader,new TypeToken<Map<Integer,Employee>>(){}.getType());
            //String str=gson.toJson(singleton.employeeMap);
            System.out.println(singleton.employeeMap);

            for (Map.Entry<Integer, Employee> pair : singleton.employeeMap.entrySet()) {

                Employee currentEmployee=pair.getValue();
                for (Map.Entry<Integer, Task> a : currentEmployee.tasks.entrySet()) {
                    Task currentTask=a.getValue();
                    singleton.taskMap.put(currentTask.task_id,currentTask);
                }

            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            Gson rsone=new Gson();
            Reader reader = new FileReader(getFilesDir()+ "/objfile1.json");
            rsone = new GsonBuilder().create();
            singleton.Employees=rsone.fromJson(reader,new TypeToken<List<Employee>>(){}.getType());
            //String str=gson.toJson(singleton.employeeMap);
            //System.out.println(singleton.Employees.get(0).tasks.get(0).task_name);
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            Gson rson=new Gson();
            Reader reader = new FileReader(getFilesDir()+ "/objfile2.json");
            rson = new GsonBuilder().create();
            singleton.Projects=rson.fromJson(reader,new TypeToken<List<Project>>(){}.getType());
            //String str=gson.toJson(singleton.employeeMap);
//            System.out.println(singleton.Projects.get(0).name);
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
