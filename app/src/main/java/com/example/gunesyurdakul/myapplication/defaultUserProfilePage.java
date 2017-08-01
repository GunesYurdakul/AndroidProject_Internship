package com.example.gunesyurdakul.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class defaultUserProfilePage extends Fragment implements View.OnClickListener{


    Singleton singleton =Singleton.getSingleton();
    ListView listView;
    Iterator<Map.Entry<Integer, Task>> it;
    View view;
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
        Log.d("Info", "hey");
        TableLayout table;
        final ListView listView;

        TextView name = (TextView) view.findViewById(R.id.employee_name);
        TextView department= (TextView) view.findViewById(R.id.department);
        TextView id=(TextView)view.findViewById(R.id.id);
        EditText newPassword=(EditText)view.findViewById(R.id.newPassword);
        final Button changePassword =(Button)view.findViewById(R.id.changePassword);
        final TextView tasks=(TextView)view.findViewById(R.id.tasksHeader);
        ImageView pp=(ImageView)view.findViewById(R.id.profilePicture);

        final DateFormat formatter=DateFormat.getDateInstance();
        currentEmployee = singleton.employeeMap.get(singleton.currentUser.person_id);
        final TextView email=(TextView)view.findViewById(R.id.email);
        email.setText(currentEmployee.email);
        name.setText(currentEmployee.name+" "+currentEmployee.surname);
        department.setText(currentEmployee.department);
        id.setText(Integer.toString(currentEmployee.person_id));
        listView = (ListView) view.findViewById(R.id.tasksList);


        if (currentEmployee.profilePicture != null) {
           // pp.setImageDrawable(null); //this should help
            //currentEmployee.profilePicture.recycle();
            try{
                Bitmap bmp = BitmapFactory.decodeByteArray(currentEmployee.profilePicture, 0, currentEmployee.profilePicture.length);
                pp.setImageBitmap(bmp);
            }catch (Exception e){
                Log.e("picture","error");
            }
        }

        changePassword.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
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

        });
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
                    if(currentEmployee.tasks.size()==0)
                        tasks.setText("No task found!");
                    else
                        tasks.setText("Tasks");
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
                    view.setTag(mymodel);

                }

                MyViewElements mymodel = (MyViewElements) view.getTag();
                Log.d("fg",Integer.toString(i));
                Task task =taskarray.get(i);
                Employee person = singleton.employeeMap.get(task.assigned_person_id);
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


        pp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }

        });
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
            imageView.setImageURI(selectedImage);

            Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            singleton.employeeMap.get(currentEmployee.person_id).profilePicture =byteArray ;
        }


    }
}

