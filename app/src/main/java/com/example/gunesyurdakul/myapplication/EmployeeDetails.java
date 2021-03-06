package com.example.gunesyurdakul.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class EmployeeDetails extends Fragment implements View.OnClickListener{

    Iterator<Map.Entry<Integer, Task>> it;
    Singleton singleton =Singleton.getSingleton();
    ListView listView;
    int p_id;
    int position;

    public static EmployeeDetails newInstance(int param1) {
        EmployeeDetails fragment = new EmployeeDetails();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public EmployeeDetails() {
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
        // Inflate the layout for this fragment
        Log.d("Info", "hey");
        View view = inflater.inflate(R.layout.employee_details, container, false);
        //View card = inflater.inflate(R.layout.profile_card,null,false);
        Log.d("Info", "hey");
        TableLayout table;
        final ListView listView;

        FloatingActionButton sendMail=(FloatingActionButton)view.findViewById(R.id.sendMail);
        //TextView name = (TextView) view.findViewById(R.id.employee_name);
        TextView department = (TextView) view.findViewById(R.id.department);
        TextView id = (TextView) view.findViewById(R.id.id);
        final DateFormat formatter = DateFormat.getDateInstance();
        final Employee currentEmployee=singleton.employeeMap.get(position);
        final ImageView pp=(ImageView) view.findViewById(R.id.profilePicture);
        final TextView email=(TextView)view.findViewById(R.id.email);
        email.setText(currentEmployee.email);
        //name.setText(currentEmployee.name + " " + currentEmployee.surname);
        department.setText(currentEmployee.department);
        id.setText(Integer.toString(currentEmployee.person_id));
        listView = (ListView) view.findViewById(R.id.tasksList);
        TabLayout tabLayout=view.findViewById(R.id.tab);
        tabLayout.getTabAt(0).setText(currentEmployee.name+" "+currentEmployee.surname);
        if (currentEmployee.profilePicture != null) {
            // pp.setImageDrawable(null); //this should help
            //currentEmployee.profilePicture.recycle();
            try{
                Bitmap src = BitmapFactory.decodeByteArray(currentEmployee.profilePicture, 0, currentEmployee.profilePicture.length);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(),src);
                dr.setCornerRadius(200);
                pp.setImageDrawable(dr);
            }catch (Exception e){
                Log.e("picture","error");
            }
        }

        final List <Task>taskarray=new ArrayList<Task>();
        it = currentEmployee.tasks.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry<Integer, Task> pair = it.next();
            taskarray.add(pair.getValue());
        }


        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                Log.d("task",Integer.toString(currentEmployee.tasks.size()));
                if(taskarray == null) {
                    Log.d("task","null");
                    return 0;
                }else {
                    return currentEmployee.tasks.size();
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
                    mymodel.warning=(ImageView)view.findViewById(R.id.dateWarning);
                    view.setTag(mymodel);

                }

                MyViewElements mymodel = (MyViewElements) view.getTag();
                Log.d("fg",Integer.toString(i));
                //Task task = singleton.Employees.get(position).Tasks.get(i);

                Task task=taskarray.get(i);
                Employee person = singleton.employeeMap.get(task.assigned_person_id);
                Log.d("task",task.task_name);
                mymodel.id.setText(Integer.toString(task.task_id));
                mymodel.name.setText(task.task_name);
                mymodel.startDate.setText(formatter.format(task.start_date));
                mymodel.dueDate.setText(formatter.format(task.due_date));
                mymodel.estimatedCost.setText(Float.toString(task.estimated_cost));
                mymodel.remainingCost.setText(Float.toString(task.remaining_cost));
                mymodel.assignedPerson.setText(person.name+" "+person.surname);
                float ratio=((task.estimated_cost-task.remaining_cost)/task.estimated_cost)*100;
                mymodel.ratio.setProgress((int)ratio);
                Log.d("Info",Integer.toString(Float.floatToIntBits(ratio)));
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
                return view;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,
                                    int task_id, long id){

                Task task = singleton.employeeMap.get(position).tasks.get(taskarray.get(task_id).task_id);
                taskUpdateAdmin detailsFragment = new taskUpdateAdmin();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                Log.d("taskid",Integer.toString(task_id+1));
                Log.d("size",Integer.toString(singleton.taskMap.size()));
                //Task t=singleton.taskMap.get(task_id+1);
                //args.putInt("project",t.related_project.project_id-1);
                args.putInt("task",task.task_id);
                detailsFragment.setArguments(args);
                ft.replace(R.id.fragment_layout, detailsFragment);
                //ft.addToBackStack("tdetails");
                fm.popBackStack();
                ft.commit();
            }
        });
//        listView.notifyAll();
        Log.d("Info", "hey");


        Log.d("Info", "heyssss");

        sendMail.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String mailto = "mailto:" + currentEmployee.email+
                        "?cc=" + "gunesyurdakul@gmail.com" +
                        "&subject=" + Uri.encode("Yapı Kredi") +
                        "&body=" + Uri.encode("Merhaba "+currentEmployee.name+" Hanım/Bey,\n\n\n\n"+"İyi çalışmalar,\n"+singleton.currentUser.name+" "+singleton.currentUser.surname);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    //TODO: Handle case where no email app is available
                }

            }
        });
        if(currentEmployee.tasks.size()==0){
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(this.getActivity());
            tv.setLayoutParams(lparams);
            tv.setTextColor(Color.parseColor("#0e0549"));
            tv.setTextSize(22);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tv.setText("No task!");
            listView.addHeaderView(tv);
        }
//        listView.addHeaderView( card);
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

    @Override
    public void onClick(View v) {}

}
