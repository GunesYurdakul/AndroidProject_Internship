package com.example.gunesyurdakul.myapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Güneş Yurdakul on 18/07/2017.
 */

public class Project{
    String name;
    static int counter=1;
    int project_id;
    Date start_date;
    Date due_date;
    Date end_date;
    List<Task> Tasks=new ArrayList<Task>();
    Project(){
        project_id=counter++;
    };

    Project(String name_in,Date start_date_in,Date due_date_in){
        name=name_in;
        project_id=counter++;
        start_date=start_date_in;
        due_date=due_date_in;
    };

    public void addTaskToProject(Task newtask){
        Singleton singleton =Singleton.getSingleton();
        singleton.taskMap.put(newtask.task_id,newtask);

        newtask.assigned_person.Tasks.add(newtask);
        newtask.assigned_person.tasks.put(newtask.task_id,newtask);

        newtask.assigned_person.projects.add(this);
        this.Tasks.add(newtask);
        newtask.related_project=this;
    }

    public void removeTask(Task removeTask) {
        Singleton singleton =Singleton.getSingleton();
        singleton.taskMap.remove(removeTask.task_id);
        removeTask.assigned_person.tasks.remove(removeTask.task_id);
    }

}
