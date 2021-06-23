package com.sabin.projectmanagement;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class TaskListAdapter extends FragmentStateAdapter {             //adaptor pentru fragmentele din ViewPager

    private ArrayList<ArrayList<Task>> taskListsTasks;
    private ArrayList<TaskList> taskLists;
                                                                        //primirea parametrilor cu liste de liste de Task-uri si lista de Task-uri a fiecarei liste
    public TaskListAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle, ArrayList<ArrayList<Task>> taskListsTasks, ArrayList<TaskList> taskLists) {
        super(fragmentManager, lifecycle);
        this.taskListsTasks = taskListsTasks;
        this.taskLists = taskLists;
    }

    public void updateLists(ArrayList<ArrayList<Task>> tasks, ArrayList<TaskList> lists){
        this.taskListsTasks.clear();
        this.taskLists.clear();
        this.taskListsTasks.addAll(tasks);
        this.taskLists.addAll(lists);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {                  //crearea unui fragment
       TaskListFragment taskListFragment =  new TaskListFragment();
        Bundle listTaskBundle = new Bundle();
        if (taskListsTasks.size() > position)                       //trimiterea datelor listei si a listei de Task-uri catre fragment prin argumente
        listTaskBundle.putSerializable("taskArray",taskListsTasks.get(position));
        else
            listTaskBundle.putSerializable("taskArray",new ArrayList<ArrayList<Task>>());
        taskListFragment.setArguments(listTaskBundle);
        return taskListFragment;
    }

/*    public Fragment newFragment(){
        FragmentTransaction fragmentTransaction =
        TaskListFragment returnFragment = new TaskListFragment();
        return returnFragment;
    }*/

    @Override
    public int getItemCount() {         //setarea numarului de liste de Task-uri
        return taskLists.size();
    }
}
