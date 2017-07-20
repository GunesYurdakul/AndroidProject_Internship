package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
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

    TableLayout table;
    Singleton singleton =Singleton.getSingleton();

    private ListView listView;
    private ViewGroup viewGroup;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        final DateFormat formatter=DateFormat.getDateInstance();


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

        listView = (ListView) findViewById(R.id.listView);
        addHeader();


        listView.setAdapter(new BaseAdapter() {
           @Override
           public int getCount() {
               if(singleton.Projects == null) {
                   return 0;
               }else {
                   return singleton.Projects.size();
               }
           }

           @Override
           public Object getItem(int i) {
               return null;
           }

           @Override
           public long getItemId(int i) {
               return 0;
           }

           @Override
           public View getView(int i, View view, ViewGroup viewGroup) {
               if(view == null) {
                   LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService( getBaseContext().LAYOUT_INFLATER_SERVICE );
                   view = inflater.inflate(R.layout.view_project_cell,viewGroup,false);
                   MyViewElements  mymodel = new MyViewElements();
                   mymodel.id = (TextView) view.findViewById(R.id.id);
                   mymodel.name = (TextView) view.findViewById(R.id.name);
                   mymodel.startDate = (TextView) view.findViewById(R.id.startDate);
                   mymodel.dueDate = (TextView) view.findViewById(R.id.dueDate);

                   view.setTag(mymodel);

               }

               MyViewElements mymodel = (MyViewElements) view.getTag();
               Project project = singleton.Projects.get(i);
               mymodel.id.setText(Integer.toString(i+1));
               mymodel.name.setText(project.name);
               mymodel.startDate.setText(formatter.format(project.start_date));
               mymodel.dueDate.setText(formatter.format(project.due_date));

               return view;
           }
       });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id){
                Log.d("INFO","Clicked on a Project");
                    ProjectFragment fragment = new ProjectFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    getSupportFragmentManager().beginTransaction().add(R.id.listView, fragment).commit();
                    transaction.commit();
                    //openDetails();
                }


        });
        //notify all gerekebilir
    }


    public class MyViewElements {
        TextView id;
        TextView name;
        TextView startDate;
        TextView dueDate;
        TextView endDate;

    }

    public void addHeader(){
        View view;
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService( getBaseContext().LAYOUT_INFLATER_SERVICE );
        view = inflater.inflate(R.layout.view_project_cell,null);
        MyViewElements  mymodel = new MyViewElements();
        mymodel.id = (TextView) view.findViewById(R.id.id);
        mymodel.name = (TextView) view.findViewById(R.id.name);
        mymodel.startDate = (TextView) view.findViewById(R.id.startDate);
        mymodel.dueDate = (TextView) view.findViewById(R.id.dueDate);

        view.setTag(mymodel);
        listView.addHeaderView(view);
    }

//    public void openDetails(){
//        Intent intent = new Intent(this,HavingFragment.class);
//        startActivity(intent);
//    }
}

