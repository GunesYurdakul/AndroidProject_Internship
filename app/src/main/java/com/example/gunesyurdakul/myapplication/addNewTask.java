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
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class addNewTask extends Fragment implements View.OnClickListener{


    View view;
    Singleton singleton =Singleton.getSingleton();
    static Task newTask=new Task();
    Button setStartDate,setDueDate,done;
    static TextView startDate,dueDate;
    static String date;
    Spinner employees;
    static boolean s=false;
    static boolean sc=false;
    static boolean dc=false;

    ListView listView;
    EditText projectName,cost;
    static SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
    int position;

    public static ProjectDetails newInstance(int param1) {
        ProjectDetails fragment = new ProjectDetails();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public addNewTask() {
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
        View view = inflater.inflate(R.layout.fragment_add_new_task, container, false);
        final TextView warning = view.findViewById(R.id.warning);
        setStartDate = view.findViewById(R.id.setStartDate);
        setDueDate = view.findViewById(R.id.setDueDate);
        startDate = view.findViewById(R.id.startDate);
        dueDate = view.findViewById(R.id.dueDate);
        projectName=view.findViewById(R.id.projectName);
        done=view.findViewById(R.id.done);
       // cost=view.findViewById(R.id.estimatedCost);
        newTask.assigned_person_id=-1;

        //Employees drop down list
        employees = (Spinner)view.findViewById(R.id.assignee);

        final List<String> employee_names=new ArrayList<String>();
        final List<Integer>ids=new ArrayList<Integer>();
        Iterator<Map.Entry<Integer, Employee>> it = singleton.employeeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Employee> pair = it.next();
            employee_names.add(pair.getValue().name+" "+pair.getValue().surname);
            ids.add(pair.getValue().person_id);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, employee_names);
        employees.setAdapter( adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        employees.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            newTask.assigned_person_id=singleton.employeeMap.get(ids.get(position)).person_id;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //get start date
        setStartDate.setOnClickListener(new View.OnClickListener(){


            public void onClick(View v){
                s=true;
                Log.d("INFO","SetStartButtonClicked");
                DialogFragment sDate = new DatePickerFragment();
                sDate.show(getFragmentManager(),"datePicker");
            };


        });

        //get end date
        setDueDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                s=false;
                Log.d("INFO","SetEndButtonClicked");
                DialogFragment dDate = new DatePickerFragment();
                dDate.show(getFragmentManager(),"datePicker");
            };
        });

        done.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(projectName.getText().toString().trim().length()>0&&!(newTask.start_date==null)&&!(newTask.due_date==null))
                {
                    Log.d("d","1");

                    newTask.estimated_cost=(Math.abs(newTask.start_date.getTime() - newTask.due_date.getTime())*8)/(60*60*1000*24);
                    newTask.task_name=projectName.getText().toString();
                    Log.d("INFO","addTask");
                    //singleton.Projects.get(position).addTaskToProject();
                    singleton.projectMap.get(position).addTaskToProject(new Task(newTask.task_name,newTask.assigned_person_id, newTask.start_date,newTask.due_date,newTask.estimated_cost));
                    ProjectDetails detailsFragment = new ProjectDetails();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putInt("position",position);
                    detailsFragment.setArguments(args);
                    ft.replace(R.id.fragment_layout, detailsFragment);
                    ft.commit();
                }
                else if(newTask.start_date==null){
                    Log.d("d","3");
                    warning.setText("Starting date can not be left blank!");
                }
                else if(newTask.due_date==null){
                    Log.d("d","4");
                    warning.setText("Due date can not be left blank!");
                }
                else if(newTask.start_date.compareTo(newTask.due_date)>0){
                    Log.d("d","2");
                    warning.setText("Due date of the task should be later than its starting date!");
                }
                else if(newTask.assigned_person_id==-1){
                    Log.d("d","5");
                    warning.setText("An Assignee should be chosen!");
                }
                else{
                    Log.d("d","4");
                    warning.setText("Task name can not be left blank!");
                }

            };
        });



        return view;
    }

    @Override
    public void onClick(View v) {}
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return  dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date = Integer.toString(day)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year);
            if (s) {
                try {
                    newTask.start_date = format.parse(date);
                }catch (ParseException e){
                    e.printStackTrace();
                }
                startDate.setText(date);
                sc=true;
            }
            else
            {
                try {
                    newTask.due_date = format.parse(date);
                }catch (ParseException e){
                    e.printStackTrace();
                }
                dueDate.setText(date);
                dc=true;
            }

        }
    }

}
