package com.example.gunesyurdakul.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class defaultUsers extends AppCompatActivity {
    Singleton singleton =Singleton.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project);
        if(singleton.currentUser.admin==false) {
            readFile();
            setNotifications();
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));

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
                ft.addToBackStack("dfs");
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
                startActivity(new Intent(defaultUsers.this,defaultUsers.class));
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
                startActivity(new Intent(defaultUsers.this,defaultUsers.class));
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
    public void setNotifications(){
        Iterator<Map.Entry<Integer, Task>> it = singleton.currentUser.tasks.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Task> pair = it.next();
            SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
            Date today=new Date();
            float leftTime=(Math.abs(pair.getValue().due_date.getTime() - today.getTime())*8/(60*60*1000*24));

            if(leftTime<=16&&pair.getValue().remaining_cost>0&&leftTime>=0){
                Intent intent = new Intent(this, defaultUsers.class);
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
                Intent intent = new Intent(this, defaultUsers.class);
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
