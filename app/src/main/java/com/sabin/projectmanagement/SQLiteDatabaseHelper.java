package com.sabin.projectmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "project.db";

    public static final String TABLE_USER = "user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "user_id";
        public static final String COLUMN_USER_email = "user_email";
        public static final String COLUMN_USER_PASSWORD = "user_password";
        public static final String COLUMN_USER_ROLE = "user_role";

    public static final String TABLE_ROLE = "role";
        public static final String COLUMN_ROLE_NAME = "role_name";
        public static final String COLUMN_ROLE_PROJECT_READ = "project_read";
        public static final String COLUMN_ROLE_PROJECT_EDIT = "project_edit";
        public static final String COLUMN_ROLE_PROJECT_DELETE = "project_delete";
        public static final String COLUMN_ROLE_LIST_READ = "list_read";
        public static final String COLUMN_ROLE_LIST_EDIT = "list_edit";
        public static final String COLUMN_ROLE_LIST_DELETE = "list_delete";
        public static final String COLUMN_ROLE_TASK_READ = "task_read";
        public static final String COLUMN_ROLE_TASK_EDIT = "task_edit";
        public static final String COLUMN_ROLE_TASK_DELETE = "task_delete";

    public static final String TABLE_PROJECT = "project";
        public static final String COLUMN_PROJECT_ID = "project_id";
        public static final String COLUMN_PROJECT_NAME = "project_name";
        public static final String COLUMN_PROJECT_DESCRIPTION = "project_description";
        public static final String COLUMN_PROJECT_LISTS = "project_lists";

    public static final String TABLE_LIST = "list";
        public static final String COLUMN_LIST_ID = "list_id";
        public static final String COLUMN_LIST_NAME = "list_name";
        public static final String COLUMN_LIST_DESCRIPTION = "list_description";
        public static final String COLUMN_LIST_ICON = "list_icon";
        public static final String COLUMN_LIST_TASKS = "list_tasks";

    public static final String TABLE_TASK = "task";
        public static final String COLUMN_TASK_ID = "task_id";
        public static final String COLUMN_TASK_NAME = "task_name";
        public static final String COLUMN_TASK_DESCRIPTION = "task_description";

    public SQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
