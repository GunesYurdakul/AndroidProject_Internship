package com.example.gunesyurdakul.myapplication;

/**
 * Created by melisgulenay on 18/07/2017.
 */

public class Employee {
    int person_id;
    String name;
    String surname;
    String Department;
    Task [] tasks;
    Project[]projects;

    Employee(int id, String name_in,String surname_in,String department_in){//constructorwith inputs
        person_id=id;
        this.name=name_in;
        this.surname=surname_in;
        this.Department=department_in;
    }
    Employee(){}
}
