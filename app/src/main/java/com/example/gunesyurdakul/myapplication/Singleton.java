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
    Employee clickedProject;
    List<Employee> Employees=new ArrayList<Employee>();
    List<Project> Projects=new ArrayList<Project>();
    List<String>Departments=new ArrayList<String>();
    Map<Integer,Project> projectMap=new HashMap<Integer,Project>();
    Map<Integer,Employee> employeeMap=new HashMap<Integer,Employee>();
    Map<Integer,Task> taskMap=new HashMap<Integer,Task>();
    //Map<Integer,Map>employeeTaskMap=new HashMap<Integer,Map>();
    //If this method is called, singleton object will be created
    public static Singleton getSingleton() {
        return singleton;
    }

}
