package com.sabin.projectmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private RecyclerView tasks;
    //private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);
        User user = null;
        Fragment taskFragment = new TaskListFragment();
        Fragment taskFragment2 = new TaskFragment2();

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        ArrayList<Task> tasks = initTasklist();

        if (email == null){
            Intent intentlogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentlogin);
        }





        //getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.flFragmentTask, taskFragment, null).commit();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTask, taskFragment2).commit();
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


    }

    private ArrayList<Task> initTasklist(){
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task("Task 1", "Desc 1", 1));
        taskList.add(new Task("Task 2", "Desc 2", 2));
        taskList.add(new Task("Task 3", "Desc 3", 3));
        taskList.add(new Task("Task 4", "Desc 4", 4));
        return taskList;
    }

}