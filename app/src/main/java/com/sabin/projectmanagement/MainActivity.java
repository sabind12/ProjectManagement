package com.sabin.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment taskFragment = new TaskFragment();
        Fragment taskFragment2 = new TaskFragment2();

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.flFragmentTask, taskFragment, null).commit();

        findViewById(R.id.btnFragment1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTask, taskFragment).commit();
            }
        });

        findViewById(R.id.btnFragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTask, taskFragment2).commit();
                SQLiteDatabaseHelper dbHelperRead = new SQLiteDatabaseHelper(MainActivity.this);
                List<Role> allRoleList = dbHelperRead.getAllRoles();
                Toast.makeText(MainActivity.this, allRoleList.toString(), Toast.LENGTH_LONG).show();
            }
        });

            Role admin = new Role("admin", true, true, true, true, true, true, true, true, true);

            SQLiteDatabaseHelper dbHelper = new SQLiteDatabaseHelper(MainActivity.this);
        boolean role = dbHelper.createRole(admin);
        Toast.makeText(MainActivity.this, "Role written to db successfully", Toast.LENGTH_LONG).show();


    }
}