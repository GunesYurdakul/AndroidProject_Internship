package com.example.gunesyurdakul.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class EmployeeFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename and change types and number of parameters
//    public static ProjectFragment setArguments(Project p) {
//        ProjectFragment fragment = new ProjectFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
    Singleton singleton =Singleton.getSingleton();
    ListView listView;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Info", "hey");
        View view = inflater.inflate(R.layout.activity_employees, container, false);
        Log.d("Info", "hey");
        TableLayout table;
        ListView listView;
        Log.d("Info", "hey");

        listView = (ListView) view.findViewById(R.id.listView);

        final FloatingActionButton addEmployee = (FloatingActionButton)view.findViewById(R.id.addNewEmployee);

        addEmployee.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("INFO","AddButtonClicked");
                addNewEmployee addEmployee = new addNewEmployee();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_layout,addEmployee );
                ft.addToBackStack("addemployee");
                ft.commit();

            };
        });
        final List<Integer> employee_ids=new ArrayList<Integer>();
        Iterator<Map.Entry<Integer, Employee>> it = singleton.employeeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Employee> pair = it.next();
            if(pair.getValue().person_id!=singleton.currentUser.person_id)
                employee_ids.add(pair.getValue().person_id);
        }
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if(singleton.employeeMap == null) {
                    return 0;
                }else {
                    return singleton.employeeMap.size();
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
                    view = inflater.inflate(R.layout.view_employee_cell,viewGroup,false);
                    Employee_View mymodel = new Employee_View();
//                    mymodel.id = (TextView) view.findViewById(R.id.id);
                    mymodel.name = (TextView) view.findViewById(R.id.name);
                    mymodel.department = (TextView) view.findViewById(R.id.department);
                    mymodel.userType=(TextView)view.findViewById(R.id.admin);
                    mymodel.pp=(ImageView)view.findViewById(R.id.miniPP);
                    view.setTag(mymodel);

                }

                Employee_View mymodel = (Employee_View) view.getTag();
                Employee employee = singleton.employeeMap.get(employee_ids.get(i));
//                mymodel.id.setText(Integer.toString(i+1));
                mymodel.name.setText(employee.name+" "+employee.surname);
                mymodel.department.setText(employee.department);
                if(employee.admin)
                    mymodel.userType.setText("Admin");
                else
                    mymodel.userType.setText("Default");


                if (employee.profilePicture != null) {
                    // pp.setImageDrawable(null); //this should help
                    //currentEmployee.profilePicture.recycle();
                    try{
                        Bitmap src = BitmapFactory.decodeByteArray(employee.profilePicture, 0, employee.profilePicture.length);
                        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(),src);
                        dr.setCornerRadius(200);
                        mymodel.pp.setImageDrawable(dr);
                    }catch (Exception e){
                        Log.e("picture","error");
                    }
                }
                return view;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){
                Log.d("INFO","Clicked on a Employee");
                    EmployeeDetails detailsFragment = new EmployeeDetails();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putInt("position",employee_ids.get(position));
                    detailsFragment.setArguments(args);
                    ft.replace(R.id.fragment_layout, detailsFragment);
                    ft.addToBackStack("pdetails");
                    ft.commit();

                //openDetails();
            }


        });
        Log.d("Info", "hey");

        return view;


    }



    public class Employee_View {
        TextView id;
        TextView name;
        TextView department;
        TextView userType;
        ImageView pp;
    }

    @Override
    public void onClick(View v) {}

}
