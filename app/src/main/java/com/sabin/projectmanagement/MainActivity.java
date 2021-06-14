package com.sabin.projectmanagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {           //Activitatea principala a aplicatiei

    TabLayout listTabLayout;
    ViewPager2 listViewPager2;
    TaskListAdapter taskListAdapter;
    //TaskAdapter taskAdapter;

    @Override
    protected void onStart() {
        super.onStart();

        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);               //Citirea Task-urilor pe liste si a listelor
        ArrayList<ArrayList<Task>> taskListsTasks = db.getAllTaskListsTasks(1);
        ArrayList<TaskList> taskLists = db.getAllTaskLists(1);
        listTabLayout = findViewById(R.id.listTabLayout);                               //Configurarea ViewPager-ului
        listViewPager2 = findViewById(R.id.listViewPager2);
        FragmentManager fragmentManager = getSupportFragmentManager();
        taskListAdapter = new TaskListAdapter(fragmentManager, getLifecycle(), taskListsTasks, taskLists);      //crearea ViewPager-ului
        listViewPager2.setAdapter(taskListAdapter);
        for (int i = 0; i < taskLists.size(); i++) {                                    //popularea Tab-urilor de deasupra ViewPager-ului
            listTabLayout.addTab(listTabLayout.newTab().setText(taskLists.get(i).name));
        }

        listTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {      //Selectarea paginii corespunzatoare la apasarea pe Tab
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
            public void onPageSelected(int position) {          //selectarea Tab-ului corespunzator la schimbarea paginii
                listTabLayout.selectTab(listTabLayout.getTabAt(position));
            }
        });

        LinearLayout listTabStrip = (LinearLayout) listTabLayout.getChildAt(0);     //setarea OnLongClickListener pe fiecare Tab pentru editarea listei
        for (int i = 0; i < listTabStrip.getChildCount(); i++) {
            View currentChild = listTabStrip.getChildAt(i);
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

        findViewById(R.id.buttonMoveTaskLeft).setOnClickListener(new View.OnClickListener() {       //buton pentru mutarea unui Task in lista de la stanga
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
        findViewById(R.id.buttonNewList).setOnClickListener(new View.OnClickListener() {            //buton pentru creerea si editarea unei liste noi
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
        findViewById(R.id.buttonNewTask).setOnClickListener(new View.OnClickListener() {            //buton pentru creerea si editarea unui Task nou
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
        findViewById(R.id.buttonMoveTaskRight).setOnClickListener(new View.OnClickListener() {      //buton pentru mutarea unui Task in lista de la dreapta
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
        Intent intent = getIntent();                        //primirea utilizatorului autentificat din activitatile de autentificare sau inregistrare
        String email = intent.getStringExtra("email");

        if(db.getAllUsers().isEmpty()) initTasklist();      //popularea utilizator liste si Task-uri cu date de proba daca nu exista utilizatori

        if (email == null){                                 //lansarea activitatii de autentificare daca nu exista utilizator autentificat
            Intent intentlogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentlogin);
        }


    }
    private void initTasklist(){        //functie pentru popularea utilizator liste si Task-uri cu date de proba daca nu exista utilizatori
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);
        db.createTask(new Task("Task 1", "Desc 1", 1));
        db.createTask(new Task("Task 2", "Desc 2", 2));
        db.createTask(new Task("Task 3", "Desc 3", 3));
        db.createTask(new Task("Task 4", "Desc 4", 4));

        db.createTaskList(new TaskList("asdas1","asdqwe1","qweasd1", 1 ));
        db.createTaskList(new TaskList("asdas2","asdqwe2","qweasd2", 1 ));
        db.createTaskList(new TaskList("asdas3","asdqwe3","qweasd3", 1 ));
        db.createTaskList(new TaskList("asdas4","asdqwe4","qweasd4", 1 ));

        User user = new User("qwe","qwe","qwe","admin");            //crearea unui utilizator admin
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

    public void refreshTaskLists (int projectId){       //functie pentru recitirea listelor de Task-uri
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