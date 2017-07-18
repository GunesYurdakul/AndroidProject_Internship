package com.example.gunesyurdakul.myapplication;

import java.util.Date;

/**
 * Created by melisgulenay on 18/07/2017.
 */

public class Project{
    String name;
    int project_id;
    Date start_date;
    Date due_date;
    Date end_date;
    Task[]tasks;
    float process_completion_ratio;
    Project(){};
    Project(String name_in,Date start_date_in,Date due_date_in,int p_id){
        name=name_in;
        project_id=p_id;
        start_date=start_date_in;
        due_date=due_date_in;
    };
}
