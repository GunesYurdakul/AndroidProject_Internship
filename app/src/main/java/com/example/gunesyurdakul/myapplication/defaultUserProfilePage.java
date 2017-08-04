package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class defaultUserProfilePage extends Fragment implements View.OnClickListener{


    Singleton singleton =Singleton.getSingleton();
    ListView listView;
    Iterator<Map.Entry<Integer, Task>> it;
    View view,card;
    int position,RESULT_LOAD_IMAGE;
    Employee currentEmployee;
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
        view = inflater.inflate(R.layout.fragment_default_user_profile_page, container, false);
        //card = inflater.inflate(R.layout.profile_card,null,false);
        Log.d("Info", "hey");
        TableLayout table;
        final ListView listView;

        //TextView name = (TextView) view.findViewById(R.id.employee_name);
        TextView department= (TextView) view.findViewById(R.id.department);
        TextView id=(TextView)view.findViewById(R.id.id);
        ImageView pp=(ImageView)view.findViewById(R.id.profilePicture);

        final DateFormat formatter=DateFormat.getDateInstance();
        currentEmployee = singleton.employeeMap.get(singleton.currentUser.person_id);
        final TextView email=(TextView)view.findViewById(R.id.email);
        email.setText(currentEmployee.email);
        //name.setText(currentEmployee.name+" "+currentEmployee.surname);
        department.setText(currentEmployee.department);
        id.setText(Integer.toString(currentEmployee.person_id));
        listView = (ListView) view.findViewById(R.id.tasksList);
        TabLayout tabLayout=view.findViewById(R.id.tab);
        tabLayout.getTabAt(0).setText(currentEmployee.name+" "+currentEmployee.surname);

        if (currentEmployee.profilePicture != null) {
            try{
                Bitmap src = BitmapFactory.decodeByteArray(currentEmployee.profilePicture, 0, currentEmployee.profilePicture.length);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(),src);
                dr.setCornerRadius(200);
                pp.setImageDrawable(dr);
            }catch (Exception e){
                Log.e("picture","error");
            }
        }

        final List<Task> taskarray=new ArrayList<Task>();
        it = currentEmployee.tasks.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Integer, Task> pair = it.next();
            taskarray.add(pair.getValue());
        }

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if(currentEmployee.tasks == null) {
                    return 0;
                }else {
//                    if(currentEmployee.tasks.size()==0)
//                        tasks.setText("No task found!");
//                    else
//                        tasks.setText("Tasks");
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
                    mymodel.warning=(ImageView)view.findViewById(R.id.dateWarning);
                    mymodel.estimatedCost = (TextView) view.findViewById(R.id.estimatedCost);
                    mymodel.remainingCost = (TextView) view.findViewById(R.id.remainingCost);
                    mymodel.assignedPerson = (TextView) view.findViewById(R.id.assignedPerson);
                    mymodel.ratio = (ProgressBar)view.findViewById(R.id.progressBar);
                    mymodel.ratio.setMax(100);
                    view.setTag(mymodel);

                }
                MyViewElements mymodel = (MyViewElements) view.getTag();
                Log.d("fg",Integer.toString(i));
                Task task =taskarray.get(i);
                Log.d("task",task.task_name);
                Employee person = singleton.employeeMap.get(task.assigned_person_id);
                mymodel.id.setText(Integer.toString(task.task_id));
                mymodel.name.setText(task.task_name);
                mymodel.startDate.setText(formatter.format(task.start_date));
                mymodel.dueDate.setText(formatter.format(task.due_date));
                mymodel.estimatedCost.setText(Float.toString(task.estimated_cost));
                mymodel.remainingCost.setText(Float.toString(task.remaining_cost));
                mymodel.assignedPerson.setText(person.name+" "+person.surname);
                Date today=new Date();
                float leftTime=(Math.abs(task.due_date.getTime() - today.getTime())*8/(60*60*1000*24));
                if(leftTime<(float)16)
                    mymodel.warning.setBackgroundColor(Color.parseColor("#fd7300"));
                else if(leftTime<0){
                    mymodel.warning.setBackgroundColor(Color.parseColor("#660718"));
                }
                else{
                    mymodel.warning.setBackgroundColor(Color.parseColor("#38872d"));

                }float ratio=((task.estimated_cost-task.remaining_cost)/task.estimated_cost)*100;
                mymodel.ratio.setProgress((int)ratio);
                Log.d("Info",Integer.toString(Float.floatToIntBits(ratio)));
                return view;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){


                final Task taskInfo = taskarray.get(position);
                taskUpdateDefaultUser detailsFragment = new taskUpdateDefaultUser();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                Log.d("size",Integer.toString(singleton.taskMap.size()));
                Task t=singleton.taskMap.get(taskInfo.task_id);
                //args.putInt("project",t.related_project.project_id-1);
                args.putInt("task",taskInfo.task_id);
                detailsFragment.setArguments(args);
                ft.replace(R.id.fragment_layout, detailsFragment);
                ft.addToBackStack("tdetails");
                ft.commit();
            }
        });
        Log.d("Info", "hey");

        listView.setOnScrollListener(new AbsListView.OnScrollListener(){


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

//                    int position_i=card.getBottom()- view.getScrollY();
//                    int diff = 0;
//                    float minAlpha = 0.4f, maxAlpha = 1.f;
//                    float alpha = minAlpha; // min alpha
//                    if (position_i > view.getHeight())
//                        alpha = minAlpha;
//                    else if (position_i + card.getHeight() < view.getHeight())
//                        alpha = maxAlpha;
//                    else {
//                        diff = view.getHeight() - position_i;
//                        alpha += ((diff * 1f) / view.getHeight())* (maxAlpha - minAlpha); // 1f and 0.4f are maximum and min
//                        // alpha
//                        // this will return a number betn 0f and 0.6f
//                    }
//                    Log.d("alpha",Float.toString(alpha));
//                    card.setAlpha(alpha);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub



            }

            private void isScrollCompleted() {
//                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
//                        && this.currentScrollState == SCROLL_STATE_IDLE) {
                    /** To do code here*/


                }
        });
        pp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }

        });
        Log.d("Info", "heyssss");

        //listView.addHeaderView( card);
        return view;
    }





    public class MyViewElements {
        TextView id;
        TextView name;
        TextView startDate;
        TextView dueDate;
        ImageView warning;
        TextView estimatedCost;
        TextView remainingCost;
        TextView assignedPerson;
        ProgressBar ratio;
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("IMAGE","IMAGE");
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) view.findViewById(R.id.profilePicture);

            Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            singleton.employeeMap.get(currentEmployee.person_id).profilePicture =byteArray ;
            Bitmap src = BitmapFactory.decodeByteArray(currentEmployee.profilePicture, 0, currentEmployee.profilePicture.length);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(),src);
            dr.setCornerRadius(200);
            imageView.setImageDrawable(dr);

        }


    }
}

