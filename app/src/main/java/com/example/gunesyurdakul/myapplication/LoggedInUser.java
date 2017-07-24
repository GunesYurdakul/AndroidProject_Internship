package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
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

        if(singleton.Employees.size()==0) {
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
        }

        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        final DateFormat formatter=DateFormat.getDateInstance();

        if(singleton.Projects.isEmpty()) {
            singleton.Projects.add(new Project("Kurumsal", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Bireysel", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Ticari", today.getTime(), today.getTime()));
            singleton.Projects.get(0).addTaskToProject(new Task("Main page design", singleton.Employees.get(0), today.getTime(), today.getTime(), 76));
            singleton.Projects.get(0).addTaskToProject(new Task("back end", singleton.Employees.get(1), today.getTime(), today.getTime(), 3));
            singleton.Projects.get(0).addTaskToProject(new Task("group management", singleton.Employees.get(2), today.getTime(), today.getTime(), 6));

            singleton.Projects.get(1).addTaskToProject(new Task("Main page design", singleton.Employees.get(0), today.getTime(), today.getTime(), 76));
            singleton.Projects.get(1).addTaskToProject(new Task("back end fggf", singleton.Employees.get(2), today.getTime(), today.getTime(), 3));

            singleton.Projects.get(2).addTaskToProject(new Task("bla bla task", singleton.Employees.get(1), today.getTime(), today.getTime(), 76));
        }

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
