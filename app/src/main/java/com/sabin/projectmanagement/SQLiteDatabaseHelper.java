package com.sabin.projectmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    //Nume baza de date
    public static final String DB_NAME = "project.db";
    //Definire variabile nume tabel utilizatori si nume coloane tabel utilizatori
    public static final String TABLE_USER = "user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_EMAIL = "user_email";
        public static final String COLUMN_USER_PASSWORD = "user_password";
        public static final String COLUMN_USER_ROLE = "user_role";
    //Definire variabile nume tabel roluri utilizatori si nume coloane tabel roluri/drepturi utilizatori
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
    //Definire variabile nume tabel proiecte si nume coloane tabel proiecte
    public static final String TABLE_PROJECT = "project";
        public static final String COLUMN_PROJECT_ID = "project_id";
        public static final String COLUMN_PROJECT_NAME = "project_name";
        public static final String COLUMN_PROJECT_DESCRIPTION = "project_description";

    public static final String TABLE_LIST = "list";
        public static final String COLUMN_LIST_ID = "list_id";
        public static final String COLUMN_LIST_NAME = "list_name";
        public static final String COLUMN_LIST_DESCRIPTION = "list_description";
        public static final String COLUMN_LIST_ICON = "list_icon";
        public static final String COLUMN_LIST_PROJECT_ID = "list_project_id";

    public static final String TABLE_TASK = "task";
        public static final String COLUMN_TASK_ID = "task_id";
        public static final String COLUMN_TASK_NAME = "task_name";
        public static final String COLUMN_TASK_DESCRIPTION = "task_description";
        public static final String COLUMN_TASK_LIST_ID = "task_list_id";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " VARCHAR(255) ," + COLUMN_USER_EMAIL + " VARCHAR(255) ," + COLUMN_USER_PASSWORD + " VARCHAR(255) ," + COLUMN_USER_ROLE + " VARCHAR(255));";
    public static final String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + " (" + COLUMN_ROLE_NAME + " VARCHAR(255) PRIMARY KEY, "
            + COLUMN_ROLE_PROJECT_READ+" BOOLEAN NOT NULL CHECK (name IN (0, 1)) ," + COLUMN_ROLE_PROJECT_EDIT + " BOOLEAN NOT NULL CHECK (name IN (0, 1)) ,"
            + COLUMN_ROLE_PROJECT_DELETE+" BOOLEAN NOT NULL CHECK (name IN (0, 1)) ," + COLUMN_ROLE_LIST_READ + " BOOLEAN NOT NULL CHECK (name IN (0, 1)) ,"
            + COLUMN_ROLE_LIST_EDIT + " BOOLEAN NOT NULL CHECK (name IN (0, 1)) ," + COLUMN_ROLE_LIST_DELETE + " BOOLEAN NOT NULL CHECK (name IN (0, 1)) ,"
            + COLUMN_ROLE_TASK_READ + " BOOLEAN NOT NULL CHECK (name IN (0, 1)) ," + COLUMN_ROLE_TASK_EDIT + " BOOLEAN NOT NULL CHECK (name IN (0, 1)) ,"
            + COLUMN_ROLE_TASK_DELETE + " BOOLEAN NOT NULL CHECK (name IN (0, 1)));";
    public static final String CREATE_TABLE_PROJECT = "CREATE TABLE " + TABLE_PROJECT + " (" + COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PROJECT_NAME + " VARCHAR(255) ," + COLUMN_PROJECT_DESCRIPTION + " VARCHAR(255));";
    public static final String CREATE_TABLE_LIST = "CREATE TABLE " + TABLE_LIST + " (" + COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LIST_NAME + " VARCHAR(255) ," + COLUMN_LIST_DESCRIPTION + " VARCHAR(255) ," + COLUMN_LIST_ICON + " VARCHAR(255) ," + COLUMN_LIST_PROJECT_ID + " INTEGER NOT NULL);";
    public static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + " (" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TASK_NAME + " VARCHAR(255) ," + COLUMN_TASK_DESCRIPTION + " VARCHAR(255), " + COLUMN_TASK_LIST_ID + " INTEGER NOT NULL);";


    public SQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_ROLE);
            db.execSQL(CREATE_TABLE_PROJECT);
            db.execSQL(CREATE_TABLE_LIST);
            db.execSQL(CREATE_TABLE_TASK);
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LIST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TASK);
        onCreate(db);

    }
}
