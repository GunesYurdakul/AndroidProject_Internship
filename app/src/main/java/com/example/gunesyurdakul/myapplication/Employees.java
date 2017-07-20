package com.example.gunesyurdakul.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class Employees extends AppCompatActivity {
    Singleton singleton =Singleton.getSingleton();
    TableLayout table;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        listView = (ListView) findViewById(R.id.listView);
        addHeader();

        if(singleton.Employees.size()==0) {
            singleton.Employees.add(new Employee("Güneş", "Yurdakul", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Melis", "Gülenay", "Mobile Devolopment"));
            singleton.Employees.add(new Employee("Jane", "Doe", "Analist"));
        }


        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if(singleton.Employees == null) {
                    return 0;
                }else {
                    return singleton.Employees.size();
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
                    view = inflater.inflate(R.layout.view_employee_cell,viewGroup,false);
                    Employee_View mymodel = new Employee_View();
                    mymodel.id = (TextView) view.findViewById(R.id.id);
                    mymodel.name = (TextView) view.findViewById(R.id.name);
                    mymodel.department = (TextView) view.findViewById(R.id.department);

                    view.setTag(mymodel);

                }

                Employee_View mymodel = (Employee_View) view.getTag();
                Employee employee = singleton.Employees.get(i);
                mymodel.id.setText(Integer.toString(i+1));
                mymodel.name.setText(employee.name);
                mymodel.department.setText(employee.department);

                return view;
            }
        });


    }

    public void addHeader(){
        View view;
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService( getBaseContext().LAYOUT_INFLATER_SERVICE );
        view = inflater.inflate(R.layout.view_employee_cell,null);
        Employee_View mymodel = new Employee_View();
        mymodel.id = (TextView) view.findViewById(R.id.id);
        mymodel.name = (TextView) view.findViewById(R.id.name);
        mymodel.department = (TextView) view.findViewById(R.id.startDate);

        view.setTag(mymodel);
        listView.addHeaderView(view);
    }

    public class Employee_View {
        TextView id;
        TextView name;
        TextView department;
    }
}
