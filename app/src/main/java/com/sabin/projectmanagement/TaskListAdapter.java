package com.sabin.projectmanagement;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class TaskListAdapter extends FragmentStateAdapter {

    private ArrayList<ArrayList<Task>> taskListsTasks;
    private ArrayList<TaskList> taskLists;

    public TaskListAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle, ArrayList<ArrayList<Task>> taskListsTasks, ArrayList<TaskList> taskLists) {
        super(fragmentManager, lifecycle);
        this.taskListsTasks = taskListsTasks;
        this.taskLists = taskLists;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        //TaskFragment taskListFragment = new TaskFragment();
       TaskListFragment taskListFragment =  new TaskListFragment();
       // SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(taskListFragment.getContext());
        //ArrayList<Task> listTaskArr;
        //listTaskArr = (ArrayList<Task>) db.getListTasks(1);
        Bundle listTaskBundle = new Bundle();
        if (taskListsTasks.size() > position)
        listTaskBundle.putSerializable("taskArray",taskListsTasks.get(position));
        else
            listTaskBundle.putSerializable("taskArray",new ArrayList<ArrayList<Task>>());
        taskListFragment.setArguments(listTaskBundle);
        return taskListFragment;
    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }
}