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
import android.widget.Button;
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


public class ProjectFragment extends Fragment implements View.OnClickListener{

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
        View view = inflater.inflate(R.layout.activity_projects, container, false);
        Log.d("Info", "hey");
        TableLayout table;
        final ListView listView;

        listView = (ListView) view.findViewById(R.id.listView);

        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        final DateFormat formatter=DateFormat.getDateInstance();


        final FloatingActionButton addProject = (FloatingActionButton)view.findViewById(R.id.addNewProject);

        addProject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("INFO","AddButtonClicked");
                addNewProjectFragment addProject = new addNewProjectFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_layout,addProject );
                //ft.addToBackStack("pdetails");
                ft.commit();

            };
        });
        final List<Integer> idArray= new ArrayList<Integer>();
        long i = 0;
        Iterator<Map.Entry<Integer, Project>> it = singleton.projectMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Project> pair = it.next();
            idArray.add(pair.getValue().project_id);
        }

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if(singleton.projectMap == null) {
                    return 0;
                }else {
                    return singleton.projectMap.size();
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
                    view = inflater.inflate(R.layout.view_project_cell,viewGroup,false);
                    MyViewElements mymodel = new MyViewElements();
                    mymodel.id = (TextView) view.findViewById(R.id.id);
                    mymodel.name = (TextView) view.findViewById(R.id.name);
                    mymodel.startDate = (TextView) view.findViewById(R.id.startDate);
                    mymodel.dueDate = (TextView) view.findViewById(R.id.dueDate);

                    view.setTag(mymodel);

                }

                MyViewElements mymodel = (MyViewElements) view.getTag();
                Project project = singleton.projectMap.get(idArray.get(i));
                mymodel.id.setText(Integer.toString(i+1));
                mymodel.name.setText(project.name);
                mymodel.startDate.setText(formatter.format(project.start_date));
                mymodel.dueDate.setText(formatter.format(project.due_date));

                return view;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){


                ProjectDetails detailsFragment = new ProjectDetails();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putInt("position",singleton.projectMap.get(idArray.get(position)).project_id);
                detailsFragment.setArguments(args);
                ft.replace(R.id.fragment_layout, detailsFragment);
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
        TextView endDate;

    }


@Override
public void onClick(View v) {}

}
