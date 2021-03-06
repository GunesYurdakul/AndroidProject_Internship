package com.example.gunesyurdakul.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.gunesyurdakul.myapplication.addNewTask.format;


public class taskUpdateAdmin extends Fragment implements View.OnClickListener{


    Singleton singleton =Singleton.getSingleton();
    ListView listView;
    static TextView warning,startDate,dueDate;
    static boolean s=false;
    static boolean s_changed=false;
    static boolean d_changed=false;
    static Task currentTask;
    static Task updatedTask;
    int task;

    public static taskUpdateAdmin newInstance(int param1) {
        taskUpdateAdmin fragment = new taskUpdateAdmin();
        Bundle args = new Bundle();
        //args.putInt("project", param1);
        //args.putInt("task",param2);
        fragment.setArguments(args);
        return fragment;
    }

    public taskUpdateAdmin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //project = getArguments().getInt("project");
            task= getArguments().getInt("task");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Info", "hey");
        View view = inflater.inflate(R.layout.fragment_task_update_admin, container, false);
        currentTask=singleton.taskMap.get(task);
        TableLayout table;
        final ListView listView;
        updatedTask=new Task();
        updatedTask.assigned_person_id=-1;
        updatedTask.start_date=currentTask.start_date;
        updatedTask.due_date=currentTask.due_date;
        TextView id = (TextView) view.findViewById(R.id.taskId);
        final EditText name = (EditText) view.findViewById(R.id.taskName);
        startDate= (TextView) view.findViewById(R.id.startDateT);
        dueDate=(TextView)view.findViewById(R.id.dueDateT);
        Button setStartDate = (Button)view.findViewById(R.id.setStartDate);
        Button setDueDate = (Button)view.findViewById(R.id.setDueDate);
        final TextView estimatedCost=(TextView)view.findViewById(R.id.estimatedCost);
        warning=(TextView)view.findViewById(R.id.warning);
        final FloatingActionButton update=(FloatingActionButton) view.findViewById(R.id.updateTask);
        FloatingActionButton  remove=(FloatingActionButton) view.findViewById(R.id.removeTask);
        Spinner asignee =(Spinner)view.findViewById(R.id.assignee);

        final DateFormat formatter=DateFormat.getDateInstance();

        name.setText(currentTask.task_name);
        Log.d("INFO",currentTask.task_name);
        id.setText(Integer.toString(currentTask.task_id));
        estimatedCost.setText(Float.toString(currentTask.estimated_cost));
        startDate.setText(formatter.format(currentTask.start_date));
        dueDate.setText(formatter.format(currentTask.due_date));
        name.setText(currentTask.task_name);
        asignee.setSelection(currentTask.assigned_person_id);
        //Employees drop down list

        List<String> employee_names=new ArrayList<String>();
        final List<Integer>ids=new ArrayList<Integer>();
        Iterator<Map.Entry<Integer, Employee>> it = singleton.employeeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Employee> pair = it.next();
            employee_names.add(pair.getValue().name+" "+pair.getValue().surname);
            ids.add(pair.getValue().person_id);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, employee_names);
        asignee.setAdapter( adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        asignee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                updatedTask.assigned_person_id=singleton.employeeMap.get(ids.get(position)).person_id;
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

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("k","clicked");
                if(name.getText().toString().trim().length()>0&&estimatedCost.getText().toString().trim().length()>0)
                {
                    singleton.taskMap.remove(currentTask.task_id);
                    Log.d("k","clicked1");
                    currentTask.estimated_cost=Float.parseFloat(estimatedCost.getText().toString());
                    currentTask.task_name=name.getText().toString();
                    singleton.employeeMap.get(currentTask.assigned_person_id).tasks.remove(currentTask.task_id);
                    currentTask.assigned_person_id=updatedTask.assigned_person_id;
                    singleton.employeeMap.get(currentTask.assigned_person_id).tasks.put(currentTask.task_id,currentTask);
                    if(s_changed)
                        currentTask.start_date=updatedTask.start_date;
                    if(d_changed) {
                        currentTask.due_date = updatedTask.due_date;
                        currentTask.remaining_cost+=(Math.abs(currentTask.start_date.getTime() - updatedTask.due_date.getTime())*8)/(60*60*1000*24);
                    }
                    Log.d("INFO","addTask");
                    ProjectDetails detailsFragment = new ProjectDetails();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    singleton.taskMap.put(currentTask.task_id,currentTask);
                    Log.d("asdf",Integer.toString(currentTask.assigned_person_id));
                    //************change it if you switch to map structure
                    args.putInt("position",currentTask.related_project_id);
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
                else if(updatedTask.assigned_person_id==-1){
                    Log.d("k","clicked5");
                    warning.setText("An Assignee should be chosen!");
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
        remove.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("asd",Integer.toString(currentTask.related_project_id));
                singleton.projectMap.get(currentTask.related_project_id).removeTask(currentTask);
                ProjectFragment addProject = new ProjectFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_layout,addProject);
                ft.commit();
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
            String date = Integer.toString(day)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year);
            final DateFormat formatter=DateFormat.getDateInstance();
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
