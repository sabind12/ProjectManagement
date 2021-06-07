package com.sabin.projectmanagement;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment implements TaskAdapter.itemClickListener {

    private TaskAdapter mTaskAdapter;


    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(String param1, String param2) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        ArrayList<Task> taskArrayList = (ArrayList<Task>) getArguments().getSerializable("taskArray");
        mTaskAdapter = new TaskAdapter( getContext(), taskArrayList, this);
        taskRecyclerView.setAdapter(mTaskAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClick(Task task) {
        ((MainActivity) getActivity()).openTaskFragment(task);

    }
}