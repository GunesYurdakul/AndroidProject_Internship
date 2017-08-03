package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        setSupportActionBar(myToolbar);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                writeJSON();
                Intent intent = new Intent(this,mainpage.class);
                startActivity(intent);
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.password:
                changePassword detailsFragment = new changePassword();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                detailsFragment.setArguments(args);
                ft.replace(R.id.fragment_layout, detailsFragment);
                ft.commit();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void writeJSON(){
        Log.d("write","write json");
        JSONArray employees=new JSONArray();
        JSONObject obj;

        //for(Employee i:singleton.Employees){
        Gson gson = new Gson();
        File file = new File(getFilesDir()+ "/objfile.json");
        Log.d("Path",file.getAbsolutePath());


        try {
            Writer writer = new FileWriter(getFilesDir()+ "/objfile.json");
            gson = new GsonBuilder().create();
            gson.toJson(singleton.employeeMap, writer);
            String str=gson.toJson(singleton.employeeMap);
            System.out.println(str);
            writer.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }

        try {
            Writer writer = new FileWriter(getFilesDir()+ "/objfilet.json");
            gson = new GsonBuilder().create();
            gson.toJson(singleton.taskMap, writer);
            String str=gson.toJson(singleton.taskMap);
            System.out.println(str);
            writer.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }


        try {
            Writer writer = new FileWriter(getFilesDir()+ "/objfilep.json");
            gson = new GsonBuilder().create();
            gson.toJson(singleton.projectMap, writer);
            String str=gson.toJson(singleton.projectMap);
            System.out.println(str);
            writer.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }




    }

/*
    public void changePassword(){
        changePassword.setText("ENTER YOUR NEW PASSWORD");
        View popupView = inflater.inflate(R.layout.change_password_popup, null,false);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView newPassword=(TextView)popupView.findViewById(R.id.newPassword);
        final TextView confirm=(TextView)popupView.findViewById(R.id.confirmPassword);
        final Button change =   (Button)popupView.findViewById(R.id.change);
        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        changePassword.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(changePassword, Gravity.NO_GRAVITY,
                location[0], location[1] + changePassword.getHeight());


        change.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String p1=newPassword.getText().toString();
                String p2=confirm.getText().toString();
                Log.d("p1","fhghkhg");
                Log.d("p2",p2);
                if(!p1.equals(p2)){
                    changePassword.setText("Passwords do not match!");
                }
                else if (p1.equals(p2)&&p1.length()<6) {
                    Log.d("length",Integer.toString(p1.length()));
                    changePassword.setText("Password should include at least 6 characters!");
                }
                else{
                    singleton.employeeMap.get(currentEmployee.person_id).password=p1;
                    currentEmployee.password=p1;
                    changePassword.setText("CHANGED:)");
                    popupWindow.dismiss();
                }
            }

        });
    }
*/
}

