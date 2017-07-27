package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class mainpage extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gunesyurdakul.myapplication.MESSAGE";
    Singleton singleton =Singleton.getSingleton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        if(singleton.Employees.size()==0) {
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment","123456","kdlw@kfşl.com",1,true));
            singleton.employeeMap.put(1,singleton.Employees.get(0));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment","asdfgh","kfşdk@lkrş.com",2,false));
            singleton.employeeMap.put(2,singleton.Employees.get(1));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist","46677880","jsdhlaksh@kdlsk.com",3,false));
            singleton.employeeMap.put(3,singleton.Employees.get(2));
            singleton.Employees.add(new Employee("Fırat", "Yurdakul", "Mobile Devolopment","6798787","ruklskş@kjlks.com",4,false));
            singleton.employeeMap.put(4,singleton.Employees.get(3));
        }

        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        final DateFormat formatter=DateFormat.getDateInstance();

        if(singleton.Projects.isEmpty()) {
            singleton.Projects.add(new Project("Kurumsal", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Bireysel", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Ticari", today.getTime(), today.getTime()));
            singleton.Projects.get(0).addTaskToProject(new Task("Main page design", singleton.Employees.get(0).person_id, today.getTime(), today.getTime(), 76));
            singleton.Projects.get(0).addTaskToProject(new Task("back end", singleton.Employees.get(1).person_id, today.getTime(), today.getTime(), 3));
            singleton.Projects.get(0).addTaskToProject(new Task("group management", singleton.Employees.get(2).person_id, today.getTime(), today.getTime(), 6));

            singleton.Projects.get(1).addTaskToProject(new Task("Main page design", singleton.Employees.get(0).person_id, today.getTime(), today.getTime(), 76));
            singleton.Projects.get(1).addTaskToProject(new Task("back end fggf", singleton.Employees.get(2).person_id, today.getTime(), today.getTime(), 3));

            singleton.Projects.get(2).addTaskToProject(new Task("bla bla task", singleton.Employees.get(1).person_id, today.getTime(), today.getTime(), 76));
        }
        singleton.Departments.add("Mobile Development");
        singleton.Departments.add("Analist");
        singleton.Departments.add("asdas");
        writeJSON();
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
    }

    public void writeJSON(){

        JSONArray employees=new JSONArray();
        JSONObject obj;

        //for(Employee i:singleton.Employees){
            Gson gson = new Gson();
            File file = new File(getFilesDir(), "objfile.json");

            try{
                Gson g = new Gson();
                String json = g.toJson(singleton.Employees);
                System.out.println(json);
                FileWriter writer=new FileWriter(file);
                gson.toJson(singleton.Employees, writer);
            }catch(IOException e){
                e.printStackTrace();
            }
        //}
        File file1 = new File(getFilesDir(), "objfile1.json");
//        file1.getParentFile().mkdirs();
            try{
                Gson g = new Gson();
                String json = g.toJson(singleton.Projects);
                System.out.println(json);
                FileWriter writer=new FileWriter(file1);
                gson.toJson(singleton.Projects, writer);
            }catch(IOException e){
                e.printStackTrace();
            }
            }


//        JSONArray projects=new JSONArray();
//        for(Project i:singleton.Projects){
//            obj=new JSONObject();
//            try {
//                obj.put("project_id",i.project_id);
//                obj.put("name",i.name);
//                obj.put("start_date",i.start_date);
//                obj.put("due_date",i.due_date);
//
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//            projects.put(obj);
//            System.out.println(obj);
//        }
//
//        JSONArray tasks=new JSONArray();
//        Iterator<Map.Entry<Integer,Task>> it= singleton.taskMap.entrySet().iterator();
//        while (it.hasNext()){
//            Task i=it.next().getValue();
//            obj=new JSONObject();
//            try {
//                obj.put("task_id",i.task_id);
//                obj.put("task_name",i.task_name);
//                obj.put("start_date",i.start_date);
//                obj.put("due_date",i.due_date);
//                obj.put("assigned_person",i.assigned_person.person_id);
//                obj.put("related_project",i.related_project.project_id);
//                obj.put("estimated_cost",i.estimated_cost);
//                obj.put("remaining_cost",i.remaining_cost);
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//            tasks.put(obj);
//            System.out.println(obj);
//        }
//    }


}
