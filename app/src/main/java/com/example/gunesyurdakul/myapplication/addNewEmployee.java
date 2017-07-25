package com.example.gunesyurdakul.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class addNewEmployee extends Fragment implements View.OnClickListener{


    View view;
    Singleton singleton =Singleton.getSingleton();
    static Employee newEmployee=new Employee();
    Button done;
    Spinner departments;
    RadioButton userType;
    EditText name,surname,password,id;
    static SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
    int position;

    public static ProjectDetails newInstance(int param1) {
        ProjectDetails fragment = new ProjectDetails();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public addNewEmployee() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Info", "hey");
        View view = inflater.inflate(R.layout.fragment_add_new_employee, container, false);
        final TextView warning = view.findViewById(R.id.warning);
        name = view.findViewById(R.id.name);
        surname = view.findViewById(R.id.surname);
        password = view.findViewById(R.id.password);
        done=view.findViewById(R.id.addNewEmployee);
        id=view.findViewById(R.id.id);

        //Employees drop down list
        departments = (Spinner)view.findViewById(R.id.departments);
        userType=(RadioButton)view.findViewById(R.id.userType);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, singleton.Departments);
        departments.setAdapter( adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        departments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                newEmployee.department=singleton.Departments.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        done.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(name.getText().toString().trim().length()>0&&surname.getText().toString().trim().length()>0&&password.getText().toString().trim().length()>=6&&id.getText().toString().trim().length()>0)
                {
                    newEmployee.name=name.getText().toString();
                    newEmployee.surname=surname.getText().toString();
                    newEmployee.password=password.getText().toString();
                    newEmployee.person_id=Integer.parseInt(id.getText().toString());
                    newEmployee.admin=userType.isChecked();
                    Log.d("INFO","addTask");
                    Employee newE=new Employee(newEmployee.name,newEmployee.surname,newEmployee.department,newEmployee.password,newEmployee.person_id,newEmployee.admin);
                    singleton.employeeMap.put(newE.person_id,newE);
                    singleton.Employees.add(newE);
                    EmployeeFragment addEmp = new EmployeeFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_layout,addEmp);
                    ft.addToBackStack("addProject");
                    ft.commit();
                }
                else if(newEmployee.department==null){
                    warning.setText("Department should be chosen!");
                }
                else if(password.getText().toString().trim().length()<6){
                    warning.setText("Password should have at least 6 characters!");
                }
                else if(name.getText().toString().trim().length()>0){
                    warning.setText("Name field is blank!");
                }
                else{
                    warning.setText("Surname field is blank!");
                }

            };
        });



        return view;
    }

    @Override
    public void onClick(View v) {}

}
