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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addNewProjectFragment extends Fragment implements View.OnClickListener{

    View view;
    Singleton singleton =Singleton.getSingleton();
    static Project newProject=new Project();
    Button setStartDate,setDueDate,done;
    static TextView startDate,dueDate;
    static String date;
    static Calendar dateInput;
    static boolean s=false;
    ListView listView;
    EditText projectName;
    static SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_project, container, false);

        final TextView warning = view.findViewById(R.id.warning);
        setStartDate = view.findViewById(R.id.setStartDate);
        setDueDate = view.findViewById(R.id.setDueDate);
        startDate = view.findViewById(R.id.startDate);
        dueDate = view.findViewById(R.id.dueDate);
        projectName=view.findViewById(R.id.projectName);
        done=view.findViewById(R.id.done);


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
                if(projectName.getText().toString().trim().length()>0)
                {
                    newProject.name=projectName.getText().toString();
                    Log.d("INFO","addTask");
                    singleton.Projects.add(new Project(newProject.name,newProject.start_date,newProject.due_date));
                    ProjectFragment addProject = new ProjectFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_layout,addProject);
                    ft.addToBackStack("addProject");
                    ft.commit();
                }
                else if(newProject.start_date.compareTo(newProject.due_date)>0){
                    warning.setText("Due date of the project should be later than its starting date!");
                }
                else if(newProject.start_date==null){
                    warning.setText("Starting date can not be left blank!");
                }
                else if(newProject.due_date==null){
                    warning.setText("Due date can not be left blank!");
                }
                else{
                    warning.setText("Project name can not be left blank!");
                }

            };
        });

        return view;
    }

    @Override
        public void onClick (View v){
        }
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
                    newProject.start_date = format.parse(date);
                }catch (ParseException e){
                    e.printStackTrace();
                }
                startDate.setText(date);
            }
            else
            {
                try {
                    newProject.due_date = format.parse(date);
                }catch (ParseException e){
                    e.printStackTrace();
            }
                dueDate.setText(date);
            }

        }
    }

}
