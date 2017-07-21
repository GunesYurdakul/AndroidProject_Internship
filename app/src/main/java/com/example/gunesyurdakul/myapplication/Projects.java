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
        setContentView(R.layout.fragment_project);
                    ProjectFragment fragment = new ProjectFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, fragment).commit();
                    transaction.commit();
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

