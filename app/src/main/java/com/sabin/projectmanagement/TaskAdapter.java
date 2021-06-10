package com.sabin.projectmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private static final String TAG = "TaskAdapter";

    private final itemClickListener listener;

    private Context mContext;
    private final List<Task> tasks;

    public TaskAdapter(Context mContext, List<Task> tasks, itemClickListener listener) {
        this.mContext = mContext;
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tasklistitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindBiewHolder:called.");
        Task task = tasks.get(position);
        holder.taskName.setText(task.getName());
        holder.taskDescription.setText(task.getDescription());

        holder.parentTask_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public Task removeTaskItem(int position) {
        final Task removedTask = tasks.remove(position);
        notifyItemRemoved(position);
        return removedTask;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

       TextView taskName;
       TextView taskDescription;
       RelativeLayout parentTask_layout;

       public ViewHolder(@NonNull @NotNull View itemView) {
           super(itemView);
           taskName = itemView.findViewById(R.id.taskItemName);
           taskDescription = itemView.findViewById(R.id.taskItemDescription);
           parentTask_layout = itemView.findViewById(R.id.parentTask_layout);
       }
   }
   public interface itemClickListener {
        void onItemClick(Task task);

   }
}
