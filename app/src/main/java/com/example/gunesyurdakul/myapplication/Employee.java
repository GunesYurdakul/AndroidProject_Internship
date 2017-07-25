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
    Boolean admin;
    List<Task> Tasks=new ArrayList<Task>();
    List<Project> projects=new ArrayList<Project>();

    Employee(String name_in,String surname_in,String department_in,String password,int id,boolean admin){//constructorwith inputs
        this.admin=admin;
        person_id=id;
        this.name=name_in;
        this.surname=surname_in;
        this.department=department_in;
        this.password=password;
    }
    Employee(){}
}
