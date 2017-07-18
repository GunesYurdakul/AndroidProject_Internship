package com.example.gunesyurdakul.myapplication;

import java.util.Date;

/**
 * Created by melisgulenay on 18/07/2017.
 */

public class Task {
    int task_id;
    String task_name;
    String belonged_project;
    Date start_date;
    Date end_date;
    Date due_date;
    Employee assigned_person;
    Project related_project;
    float completed_ratio;
    float estimated_cost;
    float remaining_cost;

    Task(){};

    Task(int t_id, String name_in,String project_name_in,Date start_date_in,Date due_date_in,float estimated_cost_in){

        task_name=name_in;
        belonged_project=project_name_in;
        task_id=t_id;
        start_date=start_date_in;
        due_date=due_date_in;
        estimated_cost=estimated_cost_in;
        remaining_cost=estimated_cost;

    };
}
