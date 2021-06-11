package com.sabin.projectmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String TAG = "TaskAdapter";

    private final itemClickListener listener;
    TaskRecyclerInterface mTaskListener;
    private Context mContext;
    private final List<Task> tasks;
    public int selectedTaskItem;
    public static Task selectedTask;


    public TaskAdapter(Context mContext, List<Task> tasks, itemClickListener listener, TaskRecyclerInterface mTaskListener) {
        this.mContext = mContext;
        this.tasks = tasks;
        this.listener = listener;
        this.mTaskListener = mTaskListener;
        selectedTaskItem = -1;
    }

    @NonNull
    @NotNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tasklistitem, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view, mTaskListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TaskViewHolder holder, int position) {
        Log.d(TAG, "onBindBiewHolder:called.");
        Task task = tasks.get(position);
        holder.taskName.setText(task.getName());
        holder.taskDescription.setText(task.getDescription());
        holder.position = position;
        holder.task = task;
        holder.taskRootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        if (selectedTaskItem == position){
            holder.taskRootView.setBackgroundColor(mContext.getResources().getColor(R.color.teal_700));
        }
        holder.parentTask_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousTaskItem = selectedTaskItem;
                selectedTaskItem = position;
                selectedTask = task;
                notifyItemChanged(previousTaskItem);
                notifyItemChanged(position);
                //holder.taskRootView;
                //tasks.remove(holder.getAbsoluteAdapterPosition());
                //notifyItemRemoved(holder.getAbsoluteAdapterPosition());
            }
        });
        holder.parentTask_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onClick: Clicked on: " + task.getName() + task);
                listener.onItemClick(task);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (tasks != null) {
            return tasks.size();
        }
        else {
            return 0;
        }
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder{

       TextView taskName;
       TextView taskDescription;
       RelativeLayout parentTask_layout;
        TaskRecyclerInterface mTaskListener;
        View taskRootView;
        int position;
        Task task;
       public TaskViewHolder(@NonNull @NotNull View itemView, TaskRecyclerInterface mTaskListener) {
           super(itemView);
           this.mTaskListener = mTaskListener;
           taskRootView = itemView;

           taskName = itemView.findViewById(R.id.taskItemName);
           taskDescription = itemView.findViewById(R.id.taskItemDescription);
           parentTask_layout = itemView.findViewById(R.id.parentTask_layout);
       }
   }
   public interface itemClickListener {
        void onItemClick(Task task);
   }

    public interface TaskRecyclerInterface{
        void saveTask();
        void deleteTask(int position);
    }
}
