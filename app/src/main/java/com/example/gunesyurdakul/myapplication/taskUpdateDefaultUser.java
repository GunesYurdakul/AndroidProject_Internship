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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.gunesyurdakul.myapplication.addNewTask.format;


public class taskUpdateDefaultUser extends Fragment implements View.OnClickListener{


    Singleton singleton =Singleton.getSingleton();
    ListView listView;
    static TextView warning,startDate,dueDate;
    static boolean s=false;
    static boolean s_changed=false;
    static boolean d_changed=false;
    static Task currentTask;
    static Task updatedTask;
    int project,task;

    public static taskUpdateDefaultUser newInstance(int param1) {
        taskUpdateDefaultUser fragment = new taskUpdateDefaultUser();
        Bundle args = new Bundle();
        //args.putInt("project", param1);
        //args.putInt("task",param2);
        fragment.setArguments(args);
        return fragment;
    }

    public taskUpdateDefaultUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // project = getArguments().getInt("project");
            task= getArguments().getInt("task");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Info", "hey");
        View view = inflater.inflate(R.layout.fragment_task_update_default_user, container, false);
        currentTask=singleton.taskMap.get(task);
        TableLayout table;
        final ListView listView;
        updatedTask=new Task();
        updatedTask.start_date=currentTask.start_date;
        updatedTask.due_date=currentTask.due_date;
        TextView id = (TextView) view.findViewById(R.id.taskId);
        final EditText name = (EditText) view.findViewById(R.id.taskName);
        startDate= (TextView) view.findViewById(R.id.startDateT);
        dueDate=(TextView)view.findViewById(R.id.dueDateT);
        //Button setStartDate = (Button)view.findViewById(R.id.setStartDate);
        //Button setDueDate = (Button)view.findViewById(R.id.setDueDate);
        final TextView estimatedCost=(TextView)view.findViewById(R.id.estimatedCost);
        warning=(TextView)view.findViewById(R.id.warning);
        final Button  update=(Button) view.findViewById(R.id.updateTask);
        //Button  remove=(Button) view.findViewById(R.id.removeTask);
        Spinner asignee =(Spinner)view.findViewById(R.id.assignee);

        final DateFormat formatter=DateFormat.getDateInstance();

        name.setText(currentTask.task_name);
        Log.d("INFO",currentTask.task_name);
        id.setText(Integer.toString(currentTask.task_id));
        estimatedCost.setText(Float.toString(currentTask.estimated_cost));
        startDate.setText(formatter.format(currentTask.start_date));
        dueDate.setText(formatter.format(currentTask.due_date));
        name.setText(currentTask.task_name);
        asignee.setSelection(currentTask.assigned_person.person_id);
        //Employees drop down list

        List<String> employee_names=new ArrayList<String>();
        for (Employee i:singleton.Employees){
            employee_names.add(i.name+" "+i.surname);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, employee_names);
        asignee.setAdapter( adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        asignee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                updatedTask.assigned_person=singleton.Employees.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("k","clicked");
                if(name.getText().toString().trim().length()>0&&estimatedCost.getText().toString().trim().length()>0&& TextUtils.isDigitsOnly(estimatedCost.getText()))
                {
                    Log.d("k","clicked1");
                    currentTask.estimated_cost=Integer.parseInt(estimatedCost.getText().toString());
                    currentTask.task_name=name.getText().toString();
                    currentTask.assigned_person=updatedTask.assigned_person;
                    if(s_changed)
                        currentTask.start_date=updatedTask.start_date;
                    if(d_changed)
                        currentTask.due_date=updatedTask.due_date;
                    Log.d("INFO","addTask");
                    ProjectDetails detailsFragment = new ProjectDetails();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    //************change it if you switch to map structure
                    args.putInt("position",currentTask.related_project.project_id-1);
                    detailsFragment.setArguments(args);
                    ft.replace(R.id.fragment_layout, detailsFragment);
                    ft.commit();
                }
                else if(updatedTask.start_date.compareTo(updatedTask.due_date)>0){
                    Log.d("k","clicked2");
                    warning.setText("Due date of the task should be later than its starting date!");
                }
                else if(updatedTask.start_date==null){
                    Log.d("k","clicked3");
                    warning.setText("Starting date can not be left blank!");
                }
                else if(updatedTask.due_date==null){
                    Log.d("k","clicked4");
                    warning.setText("Due date can not be left blank!");
                }
                else if(updatedTask.assigned_person==null){
                    Log.d("k","clicked5");
                    warning.setText("An Assignee should be chosen!");
                }
                else if(!TextUtils.isDigitsOnly(estimatedCost.getText())){
                    Log.d("k","clicked6");
                    warning.setText("Estimated cost should be a numeric value!");
                }
                else if(estimatedCost.getText().toString().trim().length()==0){
                    Log.d("k","clicked7");
                    warning.setText("Estimated cost field can not be left blank!");
                }
                else{
                    Log.d("k","clicked8");
                    warning.setText("Task name can not be left blank!");
                }

            };
        });
//        remove.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//
//                singleton.Projects.get(project).removeTask(currentTask);
//                ProjectFragment addProject = new ProjectFragment();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.fragment_layout,addProject);
//                ft.commit();};
//        });
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
            String date = Integer.toString(day)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year);
            if (s) {
                try {
                    updatedTask.start_date = format.parse(date);
                }catch (ParseException e){
                    e.printStackTrace();
                }
                s_changed=true;
                startDate.setText(date);
            }
            else
            {
                try {
                    updatedTask.due_date = format.parse(date);
                }catch (ParseException e){
                    e.printStackTrace();
                }
                d_changed=true;
                dueDate.setText(date);
            }

        }
    }


}
