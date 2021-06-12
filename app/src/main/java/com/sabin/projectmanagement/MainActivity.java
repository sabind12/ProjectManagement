package com.sabin.projectmanagement;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TabLayout listTabLayout;
    ViewPager2 listViewPager2;
    TaskListAdapter taskListAdapter;
    //TaskAdapter taskAdapter;

    @Override
    protected void onStart() {
        super.onStart();

        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);
        ArrayList<ArrayList<Task>> taskListsTasks = db.getAllTaskListsTasks(1);
        ArrayList<TaskList> taskLists = db.getAllTaskLists(1);
        listTabLayout = findViewById(R.id.listTabLayout);
        listViewPager2 = findViewById(R.id.listViewPager2);
        FragmentManager fragmentManager = getSupportFragmentManager();
        taskListAdapter = new TaskListAdapter(fragmentManager, getLifecycle(), taskListsTasks, taskLists);

        //taskListAdapter.createFragment(listViewPager2.getCurrentItem());
        listViewPager2.setAdapter(taskListAdapter);
        for (int i = 0; i < taskLists.size(); i++) {
            listTabLayout.addTab(listTabLayout.newTab().setText(taskLists.get(i).name));
        }

        listTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        listViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                listTabLayout.selectTab(listTabLayout.getTabAt(position));
            }
        });

        LinearLayout listTabStrip = (LinearLayout) listTabLayout.getChildAt(0);
        for (int i = 0; i < listTabStrip.getChildCount(); i++) {
            View currentChild = listTabStrip.getChildAt(i);
            // Set LongClick listener to each Tab
            currentChild.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(MainActivity.this, "Tab clicked: " + currentChild.getTooltipText(), Toast.LENGTH_SHORT).show();
                    for (int j = 0; j < taskLists.size(); j++) {
                        String currentChildName = currentChild.getTooltipText().toString();
                        if (taskLists.get(j).getName().equals(currentChildName))
                            openTaskListFragment(taskLists.get(j).getId());
                    }
                    return true;
                }
            });
        }

        findViewById(R.id.buttonMoveTaskLeft).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if((listTabLayout.getSelectedTabPosition()-1) >= 0){
                String prevTabName = listTabStrip.getChildAt(listTabLayout.getSelectedTabPosition()-1).getTooltipText().toString();
                    SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(MainActivity.this);
                    int nextListId = db.getTaskListIdByName(prevTabName);
                    Task task = TaskAdapter.selectedTask;
                    task.setList_id(nextListId);
                    db.editTask(task);
                    Toast.makeText(MainActivity.this, task.getName(), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(MainActivity.this, "No List to the Left", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.buttonNewList).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String selectedTabName = listTabStrip.getChildAt(listTabLayout.getSelectedTabPosition()).getTooltipText().toString();
                Toast.makeText(MainActivity.this, selectedTabName, Toast.LENGTH_SHORT).show();
                SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(MainActivity.this);
                int selectedListId = db.getTaskListIdByName(selectedTabName);
                TaskList newTaskList = new TaskList("Task List Name", "Task List Description", "Task List Icon", 1);
                int newTaskListId = (int)db.insertTaskList(selectedListId, newTaskList);
                openTaskListFragment(newTaskListId);
            }
        });
        findViewById(R.id.buttonNewTask).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String selectedTabName = listTabStrip.getChildAt(listTabLayout.getSelectedTabPosition()).getTooltipText().toString();
                Toast.makeText(MainActivity.this, selectedTabName, Toast.LENGTH_SHORT).show();
                SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(MainActivity.this);
                int selectedListId = db.getTaskListIdByName(selectedTabName);
                Task newTask = new Task("Task name", "Task Description", selectedListId);
                int newTaskId = (int)db.createTask(newTask);

                openTaskFragment(db.getTask(newTaskId));
                //String selectedTabName = listTabStrip.getChildAt(listTabLayout.getSelectedTabPosition()).getTooltipText().toString();
                // int selectedListId = db.getTaskListIdByName(selectedTabName);
            }
        });
        findViewById(R.id.buttonMoveTaskRight).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if ((listTabLayout.getSelectedTabPosition() + 1) < (listTabStrip.getChildCount())){
                String nextTabName = listTabStrip.getChildAt(listTabLayout.getSelectedTabPosition() + 1).getTooltipText().toString();
                    SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(MainActivity.this);
                    int nextListId = db.getTaskListIdByName(nextTabName);
                    Task task = TaskAdapter.selectedTask;
                    task.setList_id(nextListId);
                    db.editTask(task);

                    Toast.makeText(MainActivity.this, task.getName(), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(MainActivity.this, "No List to the Right", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");




        User user = new User("qwe","qwe","qwe","admin");
        if(db.getAllUsers().isEmpty()) initTasklist();

        if (email == null){
            Intent intentlogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentlogin);
        }




        //getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.flFragmentTask, taskFragment, null).commit();
/*
        findViewById(R.id.btnFragment1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                TaskListFragment fragmentTaskList = new TaskListFragment();
                ArrayList<Task> tasklist =tasks;
                Bundle taskFragmentBundle = new Bundle();
                taskFragmentBundle.putSerializable("taskArray", tasklist);
                fragmentTaskList.setArguments(taskFragmentBundle);
                fragmentTransaction.replace(R.id.flFragmentTask, fragmentTaskList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTask, taskFragment).commit();
            }
        });

        Role admin = new Role("admin", true, true, true, true, true, true, true, true, true);

        findViewById(R.id.btnFragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTask, taskFragment2).addToBackStack(null).commit();
                SQLiteDatabaseHelper dbHelperRead = new SQLiteDatabaseHelper(MainActivity.this);
                //List<Role> allRoleList = dbHelperRead.getAllRoles();
                //Role returnRole = dbHelperRead.getRole("admin");
                //Toast.makeText(MainActivity.this, allRoleList.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this, returnRole.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, String.valueOf(dbHelperRead.editRole(admin)), Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this, String.valueOf(dbHelperRead.checkIsRole("admin")), Toast.LENGTH_LONG).show();

            }
        });



            SQLiteDatabaseHelper dbHelper = new SQLiteDatabaseHelper(MainActivity.this);
        //boolean role = dbHelper.createRole(admin);
        //Toast.makeText(MainActivity.this, "Role written to db successfully", Toast.LENGTH_LONG).show();
*/

    }
    private void initTasklist(){
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);
        db.createTask(new Task("Task 1", "Desc 1", 1));
        db.createTask(new Task("Task 2", "Desc 2", 2));
        db.createTask(new Task("Task 3", "Desc 3", 3));
        db.createTask(new Task("Task 4", "Desc 4", 4));

        db.createTaskList(new TaskList("asdas1","asdqwe1","qweasd1", 1 ));
        db.createTaskList(new TaskList("asdas2","asdqwe2","qweasd2", 1 ));
        db.createTaskList(new TaskList("asdas3","asdqwe3","qweasd3", 1 ));
        db.createTaskList(new TaskList("asdas4","asdqwe4","qweasd4", 1 ));

        User user = new User("qwe","qwe","qwe","admin");
        db.createUser(user);
    }
    public void openTaskFragment (Task task){ //Functie pentru deschiderea unui fragment editabil de detaliu al taskului
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        TaskFragmentDetail fragmentTaskDetail = new TaskFragmentDetail();
        Bundle taskFragmentBundle = new Bundle();
        taskFragmentBundle.putSerializable("task", task);
        fragmentTaskDetail.setArguments(taskFragmentBundle);
        fragmentTransaction.replace(R.id.flFragmentTask, fragmentTaskDetail);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void openTaskListFragment (int taskListId){ //Functie pentru deschiderea unui fragment editabil de detaliu al taskului
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        TaskListDetailFragment fragmentTaskListDetail = TaskListDetailFragment.newInstance(taskListId);
        fragmentTransaction.replace(R.id.flFragmentTask, fragmentTaskListDetail);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void refreshTaskLists (int projectId){
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);
        ArrayList<TaskList> taskLists;
        taskLists = db.getAllTaskLists(projectId);
        ArrayList<ArrayList<Task>> taskListsTasks;
        taskListsTasks = db.getAllTaskListsTasks(projectId);
        taskListAdapter.updateLists(taskListsTasks, taskLists);
    }

//    public void refreshViewPager (){
//        Objects.requireNonNull(this.listViewPager2.getAdapter()).notifyDataSetChanged();
//
//    }

}