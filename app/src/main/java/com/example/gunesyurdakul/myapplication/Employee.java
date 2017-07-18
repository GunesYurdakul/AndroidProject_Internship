package com.example.gunesyurdakul.myapplication;

/**
 * Created by melisgulenay on 18/07/2017.
 */

public class Employee {
    String name;
    String surname;
    Task [] tasks;
    Project[]projects;

    Employee(String name_in,String surname_in){//constructorwith inputs
        this.name=name_in;
        this.surname=surname_in;
    }
    Employee(){}
}
