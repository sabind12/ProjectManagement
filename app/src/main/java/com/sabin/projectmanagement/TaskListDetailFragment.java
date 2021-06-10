package com.sabin.projectmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListDetailFragment extends Fragment {

    private static final String ARG_TaskListId = "taskListId";

    private int mTaskListId;

    private TaskList taskList = new TaskList();

    public TaskListDetailFragment() {
        // Required empty public constructor
    }

    public static TaskListDetailFragment newInstance(int taskListId) {
        TaskListDetailFragment fragment = new TaskListDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TaskListId, taskListId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTaskListId = getArguments().getInt(ARG_TaskListId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this.getContext());
        taskList = db.getTaskList(mTaskListId);
        View view = inflater.inflate(R.layout.fragment_task_list_detail, container, false);
        view.<EditText>findViewById(R.id.taskListDetailEditTextName).setText(taskList.name);
        view.<EditText>findViewById(R.id.taskListDetailEditTextDescription).setText(taskList.description);
        view.findViewById(R.id.taskListDetailSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getContext());
                //TaskList saveTaskList = new TaskList();
                //saveTaskList= taskList;
                taskList.setName(view.<EditText>findViewById(R.id.taskListDetailEditTextName).getText().toString());
                taskList.setDescription(view.<EditText>findViewById(R.id.taskListDetailEditTextName).getText().toString());
                db.editTaskList(taskList);
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.taskListDetailDeleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getContext());
                db.deleteTaskList(taskList.getId());
                getActivity().onBackPressed();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}