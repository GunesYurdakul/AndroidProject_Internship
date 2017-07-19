package com.example.gunesyurdakul.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Güneş Yurdakul on 19/07/2017.
 */

public class Singleton {
    private static Singleton singleton = new Singleton();
    private Singleton(){}

    List<Employee> Employees=new ArrayList<Employee>();
    List<Project> Projects=new ArrayList<Project>();

    //If this method is called, singleton object will be created
    public static Singleton getSingleton() {
        return singleton;
    }

}
