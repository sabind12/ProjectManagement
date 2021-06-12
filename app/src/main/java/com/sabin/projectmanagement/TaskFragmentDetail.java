package com.sabin.projectmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class TaskFragmentDetail extends Fragment {          //fragment pentru editarea unui Task

    public TaskFragmentDetail() {
        // Required empty public constructor
    }


    public static TaskFragmentDetail newInstance(String param1, String param2) {
        TaskFragmentDetail fragment = new TaskFragmentDetail();
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
        Task task = (Task) getArguments().getSerializable("task");
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        view.<EditText>findViewById(R.id.taskDetailEditTextName).setText(task.name);
        view.<EditText>findViewById(R.id.taskDetailEditTextDescription).setText(task.description);
        view.findViewById(R.id.taskDetailSaveButton).setOnClickListener(new View.OnClickListener() {        //buton pentru salvarea Task-ului editat
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getContext());
                Task saveTask = new Task();
                saveTask.setName(view.<EditText>findViewById(R.id.taskDetailEditTextName).getText().toString());
                saveTask.setDescription(view.<EditText>findViewById(R.id.taskDetailEditTextDescription).getText().toString());
                saveTask.setId(task.getId());
                saveTask.setList_id(task.getList_id());
                db.editTask(saveTask);
                //((MainActivity) getActivity()).refreshTaskLists(1);
                //((MainActivity) getActivity()).taskListAdapter.createFragment(((MainActivity) getActivity()).listTabLayout.getSelectedTabPosition());


                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.taskDetailDeleteButton).setOnClickListener(new View.OnClickListener() {      //buton pentru stergerea Task-ului editat
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getContext());
                db.deleteTask(task.id);
                ((MainActivity) getActivity()).refreshTaskLists(1);
                getActivity().onBackPressed();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


}