package com.example.gunesyurdakul.myapplication;

import java.util.Date;

/**
 * Created by melisgulenay on 18/07/2017.
 */

public class Task {
    static int counter=0;
    int task_id;
    String task_name;
    Date start_date;
    Date end_date;
    Date due_date;
    Employee assigned_person;
    Project related_project;
    float completed_ratio;
    float estimated_cost;
    float remaining_cost;

    Task(){};

    Task(String name_in,Employee assigned, Date start_date_in,Date due_date_in,float estimated_cost_in){

        task_name=name_in;
        assigned_person=assigned;
        task_id=++counter;
        start_date=start_date_in;
        due_date=due_date_in;
        estimated_cost=estimated_cost_in;
        remaining_cost=estimated_cost;
        completed_ratio=0;

    };
}
