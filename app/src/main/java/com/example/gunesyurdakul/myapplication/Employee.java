package com.example.gunesyurdakul.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Güneş Yurdakul on 18/07/2017.
 */

public class Employee {
    static int counter=1;
    int person_id;
    String name;
    String surname;
    String department;
    String password;
    List<Task> Tasks=new ArrayList<Task>();
    List<Project> projects=new ArrayList<Project>();

    Employee(String name_in,String surname_in,String department_in){//constructorwith inputs
        person_id=counter++;
        this.name=name_in;
        this.surname=surname_in;
        this.department=department_in;
    }
    Employee(){}
}
