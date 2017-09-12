package com.example.gunesyurdakul.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LoggedInUser extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gunesyurdakul.myapplication.MESSAGE";
    Singleton singleton =Singleton.getSingleton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        readFile();
        setNotifications();
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

    public void setNotifications(){
        Iterator<Map.Entry<Integer, Task>> it = singleton.currentUser.tasks.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Task> pair = it.next();
            SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
            Date today=new Date();
            float leftTime=(Math.abs(pair.getValue().due_date.getTime() - today.getTime())*8/(60*60*1000*24));

            if(leftTime<=16&&pair.getValue().remaining_cost>0&&leftTime>=0){
                Intent intent = new Intent(this, LoggedInUser.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
                Notification myNotify  = new Notification.Builder(this)
                        .setContentTitle("İşler Güçler")
                        .setContentText("Task "+pair.getValue().task_name+ " ends in " + Integer.toString(Math.round(leftTime)) + " work hours!!!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, myNotify);
            }
            else if(leftTime<0&&pair.getValue().remaining_cost>0){
                Intent intent = new Intent(this, LoggedInUser.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
                Notification myNotify  = new Notification.Builder(this)
                        .setContentTitle("İşler Güçler")
                        .setContentText("Task "+pair.getValue().task_name+ " ended in " + Integer.toString(Math.round(leftTime)) + " work hours ago, but is not completed yet!!!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, myNotify);
            }

        }

    }
}
