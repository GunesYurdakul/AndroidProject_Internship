package com.example.gunesyurdakul.myapplication;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Güneş Yurdakul on 18/07/2017.
 */

public class Employee{
    static int counter=1;
    int person_id;
    String name;
    String surname;
    String department;
    String password;
    Boolean admin;
    String email;
    byte[] profilePicture;
    //List<Task> Tasks=new ArrayList<Task>();
    Map<Integer,Task> tasks=new HashMap<Integer, Task>();

    Employee(String name_in,String surname_in,String department_in,String password,String email,int id,boolean admin){//constructorwith inputs
        this.admin=admin;
        this.email=email;
        person_id=id;
        this.name=name_in;
        this.surname=surname_in;
        this.department=department_in;
        this.password=password;
    }

//    public void deleteTask(Task){
//    Tasks.remove()
//    }
    Employee(){}
}
