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


public class changePassword extends Fragment implements View.OnClickListener{


    View view;
    Singleton singleton =Singleton.getSingleton();
    static Employee newEmployee=new Employee();
    Button done;
    Spinner departments;
    RadioButton userType;
    EditText name,surname,password,id,email;
    static SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
    int position;

    public static ProjectDetails newInstance(int param1) {
        ProjectDetails fragment = new ProjectDetails();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public changePassword() {
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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        final EditText newPassword=(EditText)view.findViewById(R.id.newPassword);
        final TextView warning=view.findViewById(R.id.warning);
        final EditText confirm=(EditText) view.findViewById(R.id.confirmPassword);
        final Button change =   (Button)view.findViewById(R.id.change);


        change.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String p1=newPassword.getText().toString();
                String p2=confirm.getText().toString();
                Log.d("p1","fhghkhg");
                Log.d("p2",p2);
                if(!p1.equals(p2)){
                    warning.setText("Passwords do not match!");
                }
                else if (p1.equals(p2)&&p1.length()<6) {
                    Log.d("length",Integer.toString(p1.length()));
                    warning.setText("Password should include at least 6 characters!");
                }
                else{
                    singleton.employeeMap.get(singleton.currentUser.person_id).password=p1;
                    singleton.currentUser.password=p1;
                    warning.setText("CHANGED:)");

                }
            }

        });
        return view;
    }

    @Override
    public void onClick(View v) {}

}
