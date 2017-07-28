package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class mainpage extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gunesyurdakul.myapplication.MESSAGE";
    Singleton singleton =Singleton.getSingleton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        readFile();
//        if(singleton.Employees.size()==0) {
//            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment","123456","kdlw@kfşl.com",1,true));
//            singleton.employeeMap.put(1,singleton.Employees.get(0));
//            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment","asdfgh","kfşdk@lkrş.com",2,false));
//            singleton.employeeMap.put(2,singleton.Employees.get(1));
//            singleton.Employees.add(new Employee("Jane", "Doe", "Analist","46677880","jsdhlaksh@kdlsk.com",3,false));
//            singleton.employeeMap.put(3,singleton.Employees.get(2));
//            singleton.Employees.add(new Employee("Fırat", "Yurdakul", "Mobile Devolopment","6798787","ruklskş@kjlks.com",4,false));
//            singleton.employeeMap.put(4,singleton.Employees.get(3));
//        }
//
//        Calendar today=Calendar.getInstance();
//        today.set(Calendar.HOUR_OF_DAY, 0);
//        final DateFormat formatter=DateFormat.getDateInstance();
//
//        if(singleton.Projects.isEmpty()) {
//            singleton.Projects.add(new Project("Kurumsal", today.getTime(), today.getTime()));
//            singleton.Projects.add(new Project("Bireysel", today.getTime(), today.getTime()));
//            singleton.Projects.add(new Project("Ticari", today.getTime(), today.getTime()));
//            singleton.Projects.get(0).addTaskToProject(new Task("Main page design", singleton.Employees.get(0).person_id, today.getTime(), today.getTime(), 76));
//            singleton.Projects.get(0).addTaskToProject(new Task("back end", singleton.Employees.get(1).person_id, today.getTime(), today.getTime(), 3));
//            singleton.Projects.get(0).addTaskToProject(new Task("group management", singleton.Employees.get(2).person_id, today.getTime(), today.getTime(), 6));
//
//            singleton.Projects.get(1).addTaskToProject(new Task("Main page design", singleton.Employees.get(0).person_id, today.getTime(), today.getTime(), 76));
//            singleton.Projects.get(1).addTaskToProject(new Task("back end fggf", singleton.Employees.get(2).person_id, today.getTime(), today.getTime(), 3));
//
//            singleton.Projects.get(2).addTaskToProject(new Task("bla bla task", singleton.Employees.get(1).person_id, today.getTime(), today.getTime(), 76));
//        }
//        singleton.Departments.add("Mobile Development");
//        singleton.Departments.add("Analist");
//        singleton.Departments.add("asdas");
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
        singleton.Departments.add("Yazılım");
        singleton.Departments.add("Analiz");
        singleton.Departments.add("Yönetim");
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
        System.out.println(singleton.Projects.get(0).name);
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

}

}
