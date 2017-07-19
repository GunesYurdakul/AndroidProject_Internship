package com.example.gunesyurdakul.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class Employees extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        List<String>employee_list=new ArrayList<String>();
        ListView employee_listView=(ListView)findViewById(R.id.listView);
        Singleton singleton =Singleton.getSingleton();

        if(singleton.Employees.size()==0) {
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
        }
        for (Employee i:singleton.Employees) {
            employee_list.add(i.person_id + " "+i.name + " " + i.surname + "---" + i.Department);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_employees,R.id.Employees_Header,employee_list);
        employee_listView.setAdapter(arrayAdapter);
    }



}
