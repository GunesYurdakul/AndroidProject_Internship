package com.example.gunesyurdakul.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


public class defaultUserProfilePage extends Fragment implements View.OnClickListener{


    Singleton singleton =Singleton.getSingleton();
    ListView listView;

    int position;

    public static EmployeeDetails newInstance() {
        EmployeeDetails fragment = new EmployeeDetails();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public defaultUserProfilePage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Info", "hey");
        View view = inflater.inflate(R.layout.fragment_default_user_profile_page, container, false);
        Log.d("Info", "hey");
        TableLayout table;
        final ListView listView;

        TextView name = (TextView) view.findViewById(R.id.employee_name);
        TextView department= (TextView) view.findViewById(R.id.department);
        TextView id=(TextView)view.findViewById(R.id.id);
        final TextView tasks=(TextView)view.findViewById(R.id.tasksHeader);

        final DateFormat formatter=DateFormat.getDateInstance();
        final Employee currentEmployee = singleton.currentUser;
        name.setText(currentEmployee.name+" "+currentEmployee.surname);
        department.setText(currentEmployee.department);
        id.setText(Integer.toString(currentEmployee.person_id));
        listView = (ListView) view.findViewById(R.id.tasksList);



        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if(currentEmployee.Tasks == null) {
                    return 0;
                }else {
                    if(currentEmployee.Tasks.size()==0)
                        tasks.setText("No task found!");
                    else
                        tasks.setText("Tasks");
                    return currentEmployee.Tasks.size();
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
                    view = inflater.inflate(R.layout.view_task_cell,null);
                    MyViewElements mymodel = new MyViewElements();
                    mymodel.id = (TextView) view.findViewById(R.id.id);
                    mymodel.name = (TextView) view.findViewById(R.id.projectName);
                    mymodel.startDate = (TextView) view.findViewById(R.id.startDate);
                    mymodel.dueDate = (TextView) view.findViewById(R.id.dueDate);
                    mymodel.estimatedCost = (TextView) view.findViewById(R.id.estimatedCost);
                    mymodel.remainingCost = (TextView) view.findViewById(R.id.remainingCost);
                    mymodel.assignedPerson = (TextView) view.findViewById(R.id.assignedPerson);
                    mymodel.ratio = (ProgressBar)view.findViewById(R.id.progressBar);
                    mymodel.ratio.setMax(100);
                    view.setTag(mymodel);

                }

                MyViewElements mymodel = (MyViewElements) view.getTag();
                Log.d("fg",Integer.toString(i));
                Task task = currentEmployee.Tasks.get(i);

                mymodel.id.setText(Integer.toString(task.task_id));
                mymodel.name.setText(task.task_name);
                mymodel.startDate.setText(formatter.format(task.start_date));
                mymodel.dueDate.setText(formatter.format(task.due_date));
                mymodel.estimatedCost.setText(Float.toString(task.estimated_cost));
                mymodel.remainingCost.setText(Float.toString(task.remaining_cost));
                mymodel.assignedPerson.setText(task.assigned_person.name+" "+task.assigned_person.surname);
                float ratio=((task.estimated_cost-task.remaining_cost)/task.estimated_cost)*100;
                mymodel.ratio.setProgress((int)ratio);
                Log.d("Info",Integer.toString(Float.floatToIntBits(ratio)));
                return view;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){


                final Task taskInfo = singleton.currentUser.Tasks.get(position);
//                ProjectDetails detailsFragment = new ProjectDetails();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Bundle args = new Bundle();
//                args.putInt("position",position);
//                detailsFragment.setArguments(args);
//                ft.replace(R.id.fragment_layout, detailsFragment);
//                ft.addToBackStack("pdetails");
//                ft.commit();

            }
        });
        Log.d("Info", "hey");


        Log.d("Info", "heyssss");

        return view;
    }



    public class MyViewElements {
        TextView id;
        TextView name;
        TextView startDate;
        TextView dueDate;
        TextView estimatedCost;
        TextView remainingCost;
        TextView assignedPerson;
        ProgressBar ratio;
    }



    //    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
    @Override
    public void onClick(View v) {}

}