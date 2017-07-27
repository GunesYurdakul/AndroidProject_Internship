package com.example.gunesyurdakul.myapplication;

import java.util.Date;

/**
 * Created by gunesyurdakul on 18/07/2017.
 */

public class Task {
    static int counter=0;
    int task_id;
    String task_name;
    Date start_date;
    Date due_date;
    int assigned_person_id;
    int related_project_id;
    float estimated_cost;
    float remaining_cost;

    Task(){};

    Task(String name_in,int assigned, Date start_date_in,Date due_date_in,float estimated_cost_in){

        task_name=name_in;
        assigned_person_id=assigned;
        task_id=++counter;
        start_date=start_date_in;
        due_date=due_date_in;
        estimated_cost=estimated_cost_in;
        remaining_cost=estimated_cost/2;
    };
}
