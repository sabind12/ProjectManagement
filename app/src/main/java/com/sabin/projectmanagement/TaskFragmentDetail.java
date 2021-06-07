package com.sabin.projectmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragmentDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragmentDetail extends Fragment {



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
        view.findViewById(R.id.taskDetailSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getContext());
                Task saveTask = new Task();
                saveTask.setName(view.<EditText>findViewById(R.id.taskDetailEditTextName).getText().toString());
                saveTask.setDescription(view.<EditText>findViewById(R.id.taskDetailEditTextDescription).getText().toString());
                saveTask.setId(task.getId());
                saveTask.setList_id(task.getList_id());
                db.editTask(saveTask);

                getActivity().onBackPressed();

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

}