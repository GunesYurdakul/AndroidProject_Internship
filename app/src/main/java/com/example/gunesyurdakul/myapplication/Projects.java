package com.example.gunesyurdakul.myapplication;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Projects extends AppCompatActivity {

    TableRow row;
    TableLayout table;
    List<String> projects_list=new ArrayList<String>();
    Singleton singleton =Singleton.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        table = (TableLayout)findViewById(R.id.table);
        addHeaders();
        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        if(singleton.Projects.isEmpty()) {
            singleton.Projects.add(new Project("Kurumsal", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Bireysel", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Ticari", today.getTime(), today.getTime()));
            singleton.Projects.get(0).addTaskToProject(new Task("Main page design", singleton.Employees.get(0), today.getTime(), today.getTime(), 76));
            singleton.Projects.get(0).addTaskToProject(new Task("back end", singleton.Employees.get(1), today.getTime(), today.getTime(), 3));
            singleton.Projects.get(0).addTaskToProject(new Task("group management", singleton.Employees.get(2), today.getTime(), today.getTime(), 6));

            singleton.Projects.get(1).addTaskToProject(new Task("Main page design", singleton.Employees.get(0), today.getTime(), today.getTime(), 76));
            singleton.Projects.get(1).addTaskToProject(new Task("back end fggf", singleton.Employees.get(2), today.getTime(), today.getTime(), 3));

            singleton.Projects.get(2).addTaskToProject(new Task("bla bla task", singleton.Employees.get(1), today.getTime(), today.getTime(), 76));
        }
        addData();


        DateFormat formatter=DateFormat.getDateInstance();

      /*  projects_list.add("id    " + "Project " + "\t\tStart Date\t" + "\t\tDue Date");
        for (Project i:singleton.Projects) {
            projects_list.add(i.project_id+ "-  " + i.name + "   " + formatter.format(i.start_date)  + "    " +  formatter.format(i.due_date));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_projects,R.id.projects_header,projects_list);
        projects_listView.setAdapter(arrayAdapter);
*/
    }
    public void addHeaders(){
        TextView id,project,startDate,dueDate;
        TableRow t_row=new TableRow(this);

        id=new TextView(this);
        id.setText("id ");
        id.setPadding(5,5,5,0);
        id.setTextColor(Color.RED);
        id.setTextSize(20);
        t_row.addView(id);

        project=new TextView(this);
        project.setText("Project ");
        project.setPadding(5,5,5,0);
        project.setTextColor(Color.RED);
        project.setTextSize(20);
        t_row.addView(project);

        startDate=new TextView(this);
        startDate.setText("Start Date ");
        startDate.setPadding(5,5,5,0);
        startDate.setTextColor(Color.RED);
        startDate.setTextSize(20);
        t_row.addView(startDate);

        dueDate=new TextView(this);
        dueDate.setText("Due Date");
        dueDate.setPadding(5,5,5,0);
        dueDate.setTextColor(Color.RED);
        dueDate.setTextSize(20);
        t_row.addView(dueDate);

        t_row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

        table.addView(t_row,new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }
    public void addData(){
        DateFormat formatter=DateFormat.getDateInstance();
        Log.d("INFO","addData");
        for (Project i:singleton.Projects) {

            TextView id,project,startDate,dueDate;
            TableRow t_row=new TableRow(this);

            id=new TextView(this);
            id.setText(Integer.toString(i.project_id));
            id.setPadding(5,5,5,0);
            id.setTextSize(17);
            t_row.addView(id);

            project=new TextView(this);
            project.setText(i.name);
            project.setPadding(5,5,5,0);
            project.setTextSize(17);
            t_row.addView(project);

            startDate=new TextView(this);
            startDate.setText(formatter.format(i.start_date));
            startDate.setPadding(5,5,5,0);
            startDate.setTextSize(17);
            t_row.addView(startDate);

            dueDate=new TextView(this);
            dueDate.setText(formatter.format(i.due_date));
            dueDate.setPadding(5,5,5,0);
            dueDate.setTextSize(17);
            t_row.addView(dueDate);


            t_row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            t_row.setId(i.project_id);
            t_row.setOnClickListener(
                    new View.OnClickListener(){
                public void onClick(View v) {
                    Log.d("INFO","onclick works");
                }});
            table.addView(t_row,new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}

