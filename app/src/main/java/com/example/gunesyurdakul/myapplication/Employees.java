package com.example.gunesyurdakul.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Employees extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        List<String>employee_list=new ArrayList<String>();
        List<Employee>employee_obj_list=new ArrayList<Employee>();
        ListView employee_listView=(ListView)findViewById(R.id.listView);

//        employee_obj_list.add(new Employee(1,"Güneş","Yurdakul","Mobile Devolopment"));
//        employee_obj_list.add(new Employee(1,"Melis","Gülenay","Mobile Devolopment"));
//        employee_obj_list.add(new Employee(1,"Jane","Doe","Analist"));

        employee_list.add("Güneş Yurdakul");
        employee_list.add("Melis Gülenay");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_employees,R.id.Employees_Header,employee_list);
        employee_listView.setAdapter(arrayAdapter);

    }

}
