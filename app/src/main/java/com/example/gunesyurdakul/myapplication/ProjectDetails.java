package com.example.gunesyurdakul.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProjectDetails extends Fragment implements View.OnClickListener{


    Singleton singleton =Singleton.getSingleton();
    ListView listView;

    int position_project;

    public static ProjectDetails newInstance(int param1) {
        ProjectDetails fragment = new ProjectDetails();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ProjectDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position_project = getArguments().getInt("position");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Info", "hey");
        View view = inflater.inflate(R.layout.project_details, container, false);
        Log.d("Info", "hey");
        TableLayout table;
        final ListView listView;


        FloatingActionButton addTask=(FloatingActionButton) view.findViewById(R.id.addTask);
        FloatingActionButton deleteProject=(FloatingActionButton) view.findViewById(R.id.deleteProject);
        TextView projectName = (TextView) view.findViewById(R.id.projectName);
        TextView startDate= (TextView) view.findViewById(R.id.startDate);
        TextView dueDate= (TextView) view.findViewById(R.id.dueDate);
        TextView id=(TextView)view.findViewById(R.id.id);
        TextView tasks=(TextView)view.findViewById(R.id.tasks);
        final DateFormat formatter=DateFormat.getDateInstance();

//        id.setText(singleton.Projects.get(position).project_id);
        final Project k=singleton.projectMap.get(position_project);
        projectName.setText(k.name);
        startDate.setText(formatter.format(k.start_date));
        dueDate.setText(formatter.format(k.due_date));
        listView = (ListView) view.findViewById(R.id.tasksList);

        if(k.tasks.size()==0)
        {
            tasks.setText("No task found!");
        }

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if(k.tasks == null) {
                    return 0;
                }else {
                    return k.tasks.size();
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
                    mymodel.name = (TextView) view.findViewById(R.id.taskName);
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
                Task task = singleton.taskMap.get(k.tasks.get(i));
                Employee person=singleton.employeeMap.get(task.assigned_person_id);
                mymodel.id.setText(Integer.toString(task.task_id));
                mymodel.name.setText(task.task_name);
                mymodel.startDate.setText(formatter.format(task.start_date));
                mymodel.dueDate.setText(formatter.format(task.due_date));
                mymodel.estimatedCost.setText(Float.toString(task.estimated_cost));
                mymodel.remainingCost.setText(Float.toString(task.remaining_cost));
                mymodel.assignedPerson.setText(person.name+" "+person.surname);
                mymodel.warning=(ImageView)view.findViewById(R.id.dateWarning);
                float ratio=((task.estimated_cost-task.remaining_cost)/task.estimated_cost)*100;
                mymodel.ratio.setProgress((int)ratio);
                SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
                Date today=new Date();
                float leftTime=(Math.abs(task.due_date.getTime() - today.getTime())*8/(60*60*1000*24));
                Drawable dr=getResources().getDrawable(R.drawable.circle_green);
                Drawable dr1=getResources().getDrawable(R.drawable.circle_red);
                Drawable dr2=getResources().getDrawable(R.drawable.circle_orange);

                if(leftTime<(float)16) {
                    mymodel.warning.setBackground(dr2);
                }else if(leftTime<0){
                    mymodel.warning.setBackground(dr1);
//                    mymodel.warning.setBackgroundColor(Color.parseColor("#660718"));
                }
                else{
                    mymodel.warning.setBackground(dr);
                    //                  mymodel.warning.setBackgroundColor(Color.parseColor("#38872d"));

                }
                Log.d("Info",Integer.toString(Float.floatToIntBits(ratio)));
                return view;
            }
        });

        addTask.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addNewTask detailsFragment = new addNewTask();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putInt("position",position_project);
                detailsFragment.setArguments(args);
                ft.replace(R.id.fragment_layout, detailsFragment);
                //ft.addToBackStack("pdetails");
                ft.commit();
            };
        });

        deleteProject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int size=k.tasks.size();
                Log.d("size",Integer.toString(size));
                for (int i=0;i<size;i++) {
                    Log.d("bla",Integer.toString(i));
                    singleton.taskMap.remove(k.tasks.get(i));
                }
                singleton.projectMap.remove(position_project);
                ProjectFragment addProject = new ProjectFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_layout,addProject);
                ft.commit();
            };
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){


                final Task projectInfo = singleton.taskMap.get(k.tasks.get(position));

                taskUpdateAdmin detailsFragment = new taskUpdateAdmin();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                //args.putInt("project",position_project);
                args.putInt("task",projectInfo.task_id);
                detailsFragment.setArguments(args);
                ft.replace(R.id.fragment_layout, detailsFragment);
                //ft.addToBackStack("tdetails");
                ft.commit();
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
        ImageView warning;
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
