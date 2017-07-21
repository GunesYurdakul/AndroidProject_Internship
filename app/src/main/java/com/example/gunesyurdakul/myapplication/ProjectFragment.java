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
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectFragment newInstance} factory method to
 * create an instance of this fragment.
 */
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


        if(singleton.Projects.isEmpty()) {
            singleton.Projects.add(new Project("Kurumsal", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Bireysel", today.getTime(), today.getTime()));
            singleton.Projects.add(new Project("Ticari", today.getTime(), today.getTime()));
            singleton.Projects.get(0).addTaskToProject(new Task("Main page design", singleton.Employees.get(0), today.getTime(), today.getTime(), 76));
            singleton.Projects.get(0).addTaskToProject(new Task("back end", singleton.Employees.get(1), today.getTime(), today.getTime(), 3));
            singleton.Projects.get(0).addTaskToProject(new Task("group management", singleton.Employees.get(2), today.getTime(), today.getTime(), 6));

            singleton.Projects.get(1).addTaskToProject(new Task("Main page design", singleton.Employees.get(0), today.getTime(), today.getTime(), 76));
            singleton.Projects.get(1).addTaskToProject(new Task("back end fggf", singleton.Employees.get(2), today.getTime(), today.getTime(), 3));

            singleton.Projects.get(2).addTaskToProject(new Task("bla bla task", singleton.Employees.get(1), today.getTime(), today.getTime(), 76));
        }

        View v;
        v = inflater.inflate(R.layout.view_project_cell,null);
        MyViewElements mymodel = new MyViewElements();
        mymodel.id = (TextView) v.findViewById(R.id.id);
        mymodel.name = (TextView) v.findViewById(R.id.name);
        mymodel.startDate = (TextView) v.findViewById(R.id.startDate);
        mymodel.dueDate = (TextView) v.findViewById(R.id.dueDate);

        v.setTag(mymodel);
        listView.addHeaderView(v);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if(singleton.Projects == null) {
                    return 0;
                }else {
                    return singleton.Projects.size();
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
                Project project = singleton.Projects.get(i);
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

                if(position>0){
                ProjectDetails detailsFragment = new ProjectDetails();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putInt("position",position);
                detailsFragment.setArguments(args);
                ft.replace(R.id.fragment_layout, detailsFragment);
                ft.addToBackStack("pdetails");
                ft.commit();
                }
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
