package com.example.gunesyurdakul.myapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Güneş Yurdakul on 18/07/2017.
 */

public class Project{
    String name;
    static int counter=0;
    int project_id;
    Date start_date;
    Date due_date;
    //Date end_date;
    //List<Task> Tasks=new ArrayList<Task>();
    List<Integer>tasks=new ArrayList<Integer>();
    Project(){
        project_id=counter++;
    };

    Project(String name_in,Date start_date_in,Date due_date_in,int id){
        Singleton singleton =Singleton.getSingleton();
        name=name_in;
        project_id=id;
        start_date=start_date_in;
        due_date=due_date_in;
    };

    public void addTaskToProject(Task newtask){
        Singleton singleton =Singleton.getSingleton();
        singleton.taskMap.put(newtask.task_id,newtask);

        //singleton.employeeMap.get(newtask.assigned_person_id).Tasks.add(newtask);
        singleton.employeeMap.get(newtask.assigned_person_id).tasks.put(newtask.task_id,newtask);

       // newtask.assigned_person.projects.add(this);
        //this.Tasks.add(newtask);
        this.tasks.add(newtask.task_id);
        newtask.related_project_id=this.project_id;
    }

    public void removeTask(Task removeTask) {
        this.tasks.remove(new Integer(removeTask.task_id));
        Singleton singleton =Singleton.getSingleton();
        singleton.taskMap.remove(removeTask.task_id);
        singleton.employeeMap.get(removeTask.assigned_person_id).tasks.remove(removeTask.task_id);
    }

}
