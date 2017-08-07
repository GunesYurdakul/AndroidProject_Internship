package com.example.gunesyurdakul.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class mainpage extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gunesyurdakul.myapplication.MESSAGE";
    Singleton singleton =Singleton.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        readFile();

        if(singleton.currentUser!=null){
            EditText idText = (EditText) findViewById(R.id.id_edit);
            TextView warning = (TextView) findViewById(R.id.warning_login);
            EditText pass = (EditText) findViewById(R.id.password);
            idText.setText(Integer.toString(singleton.currentUser.person_id));
            pass.setText(singleton.currentUser.password);
        }
        if(singleton.employeeMap.size()==0) {
            singleton.employeeMap.put(1,new Employee("Güneş", "Yurdakul", "Mobile Devolopment","123456","kdlw@kfşl.com",1,true));
        }

        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        final DateFormat formatter=DateFormat.getDateInstance();

       if(singleton.projectMap.isEmpty()) {
            int id=singleton.projectMap.size()+1;
           singleton.projectMap.put(id,new Project("Test Projesi", today.getTime(), today.getTime(),id));}

        if(singleton.Departments.size()==0){
        singleton.Departments.add("Mobile Development");
        singleton.Departments.add("Analist");
        singleton.Departments.add("asdas");
        }

        TextView english=(TextView)findViewById(R.id.english);
        TextView turkish=(TextView)findViewById(R.id.turkish);

        english.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String languageToLoad = "en"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        startActivity(new Intent(mainpage.this,mainpage.class));
                        finish();
                    }
                }
        );

        turkish.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String languageToLoad = "tr"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        startActivity(new Intent(mainpage.this,mainpage.class));
                        finish();
                    }
                }
        );
    }


    public void login(View view) {
        EditText idText = (EditText) findViewById(R.id.id_edit);
        TextView warning = (TextView) findViewById(R.id.warning_login);
        EditText pass = (EditText) findViewById(R.id.password);
        String id=idText.getText().toString();

         if(id.length()==0){
             warning.setText("Id is blank!");
        }
        else{
             Employee logging_in = singleton.employeeMap.get(Integer.parseInt(id));
             if(pass.getText().toString().trim().length()==0){
                warning.setText("Password is blank!");
            }
            else if(logging_in==null){
                 warning.setText("No such Id exists!");
            }
            else if(logging_in.admin&&logging_in.password.equals(pass.getText().toString())){
                 singleton.currentUser=singleton.employeeMap.get(Integer.parseInt(id));
                 Iterator<Map.Entry<Integer, Task>> it = singleton.currentUser.tasks.entrySet().iterator();
                 while (it.hasNext()) {
                     Map.Entry<Integer, Task> pair = it.next();
                     SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
                     Date today=new Date();
                     float leftTime=(Math.abs(pair.getValue().due_date.getTime() - today.getTime())*8/(60*60*1000*24));
                     if(leftTime<=16&&pair.getValue().remaining_cost>0&&leftTime>=0){
//                         AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//                         Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//                         notificationIntent.addCategory("android.intent.category.DEFAULT");
//
//                         PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                         Calendar cal = Calendar.getInstance();
//                         cal.add(Calendar.SECOND, 1);
//                         alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
                         Intent intent = new Intent(this, mainpage.class);
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
                         Intent intent = new Intent(this, mainpage.class);
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
                 String message = idText.getText().toString();
                Intent intent = new Intent(this, LoggedInUser.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
            else if(!logging_in.admin&&logging_in.password.equals(pass.getText().toString())){
                 singleton.currentUser=singleton.employeeMap.get(Integer.parseInt(id));
                 String message = idText.getText().toString();
                 Intent intent = new Intent(this, defaultUsers.class);
                 intent.putExtra(EXTRA_MESSAGE, message);
                 startActivity(intent);
            }
            else if(!logging_in.password.equals(pass.getText().toString())){
                warning.setText("Password and Id do not match!");
            }
        }

        if(singleton.Departments.size()==0){
        singleton.Departments.add("Yazılım");
        singleton.Departments.add("Analiz");
        singleton.Departments.add("Yönetim");
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
        Reader reader = new FileReader(getFilesDir()+ "/objfilet.json");
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
