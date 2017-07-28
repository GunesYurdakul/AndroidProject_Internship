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
        TextView name = (TextView) view.findViewById(R.id.taskName);
        startDate= (TextView) view.findViewById(R.id.startDate);
        dueDate=(TextView)view.findViewById(R.id.dueDate);
        TextView estimatedCost=(TextView)view.findViewById(R.id.estimatedCost);
        final TextView remainingCost=(TextView)view.findViewById(R.id.remainingCost);
        warning=(TextView)view.findViewById(R.id.warning);
        final Button  update=(Button) view.findViewById(R.id.add);
        TextView asignee =(TextView) view.findViewById(R.id.assignee);
        final ProgressBar progress=(ProgressBar)view.findViewById(R.id.progressBar);
        final EditText completed = (EditText)view.findViewById(R.id.completedCost);

        final DateFormat formatter=DateFormat.getDateInstance();

        remainingCost.setText(Float.toString(currentTask.remaining_cost));
        name.setText(currentTask.task_name);
        Log.d("INFO",currentTask.task_name);
        id.setText(Integer.toString(currentTask.task_id));
        estimatedCost.setText(Float.toString(currentTask.estimated_cost));
        startDate.setText(formatter.format(currentTask.start_date));
        dueDate.setText(formatter.format(currentTask.due_date));
        name.setText(currentTask.task_name);
        Employee person=singleton.employeeMap.get(currentTask.assigned_person_id);
        asignee.setText(person.name+" "+person.surname);
        remainingCost.setText(Float.toString(currentTask.remaining_cost));
        float ratio=((currentTask.estimated_cost-currentTask.remaining_cost)/currentTask.estimated_cost)*100;
        progress.setProgress((int)ratio);

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String add=completed.getText().toString();

                if(add.trim().length()==0){
                    warning.setText("Field is empty!");
                }
                else{
                    Float c=Float.valueOf(add);

                    if(currentTask.remaining_cost-c<0)
                        currentTask.remaining_cost=0;
                    else{
                        singleton.employeeMap.get(currentTask.assigned_person_id).tasks.remove(currentTask.task_id);
                        currentTask.remaining_cost-=c;
                        remainingCost.setText(Float.toString(currentTask.remaining_cost));
                        float ratio=((currentTask.estimated_cost-currentTask.remaining_cost)/currentTask.estimated_cost)*100;
                        progress.setProgress((int)ratio);
                        singleton.employeeMap.get(currentTask.assigned_person_id).tasks.put(currentTask.task_id,currentTask);
                    }
                }

            }

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
