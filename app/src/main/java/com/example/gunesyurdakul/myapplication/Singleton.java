package com.example.gunesyurdakul.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Güneş Yurdakul on 19/07/2017.
 */

public class Singleton {
    private static Singleton singleton = new Singleton();
    private Singleton(){}
    Employee currentUser;

    List<Employee> Employees=new ArrayList<Employee>();
    List<Project> Projects=new ArrayList<Project>();
    List<String>Departments=new ArrayList<String>();
    Map<Integer,Employee> employeeMap=new HashMap<Integer,Employee>();

    //If this method is called, singleton object will be created
    public static Singleton getSingleton() {
        return singleton;
    }

}
