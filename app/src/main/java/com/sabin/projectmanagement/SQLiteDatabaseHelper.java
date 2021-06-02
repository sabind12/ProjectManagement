package com.sabin.projectmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    //Nume baza de date
    public static final String DB_NAME = "project.db";
    //Definire variabile nume tabel roluri utilizatori si nume coloane tabel roluri/drepturi utilizatori
    public static final String TABLE_ROLE = "role";
        public static final String COLUMN_ROLE_ID = "role_id";
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
    //Definire variabile nume tabel utilizatori si nume coloane tabel utilizatori
    public static final String TABLE_USER = "user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_EMAIL = "user_email";
        public static final String COLUMN_USER_PASSWORD = "user_password";
        public static final String COLUMN_USER_ROLE = "user_role";
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
            + COLUMN_USER_NAME + " VARCHAR(255) , " + COLUMN_USER_EMAIL + " VARCHAR(255) , " + COLUMN_USER_PASSWORD + " VARCHAR(255) , " + COLUMN_USER_ROLE + " VARCHAR(255));";
    public static final String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + " (" + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ROLE_NAME + " VARCHAR(255), " + COLUMN_ROLE_PROJECT_READ + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_PROJECT_READ + " IN (0, 1)) , "
            + COLUMN_ROLE_PROJECT_EDIT + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_PROJECT_EDIT + " IN (0, 1)) , " + COLUMN_ROLE_PROJECT_DELETE + " BOOLEAN NOT NULL CHECK ("+ COLUMN_ROLE_PROJECT_DELETE + " IN (0, 1)) , "
            + COLUMN_ROLE_LIST_READ + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_LIST_READ + " IN (0, 1)) , " + COLUMN_ROLE_LIST_EDIT + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_LIST_EDIT + " IN (0, 1)) , "
            + COLUMN_ROLE_LIST_DELETE + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_LIST_DELETE + " IN (0, 1)) , " + COLUMN_ROLE_TASK_READ + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_TASK_READ + " IN (0, 1)) , "
            + COLUMN_ROLE_TASK_EDIT + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_TASK_EDIT + " IN (0, 1)) , " + COLUMN_ROLE_TASK_DELETE + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_TASK_DELETE + " IN (0, 1)));";
    public static final String CREATE_TABLE_PROJECT = "CREATE TABLE " + TABLE_PROJECT + " (" + COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PROJECT_NAME + " VARCHAR(255) ," + COLUMN_PROJECT_DESCRIPTION + " VARCHAR(255));";
    public static final String CREATE_TABLE_LIST = "CREATE TABLE " + TABLE_LIST + " (" + COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LIST_NAME + " VARCHAR(255) ," + COLUMN_LIST_DESCRIPTION + " VARCHAR(255) ," + COLUMN_LIST_ICON + " VARCHAR(255) ," + COLUMN_LIST_PROJECT_ID + " INTEGER NOT NULL);";
    public static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + " (" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TASK_NAME + " VARCHAR(255) ," + COLUMN_TASK_DESCRIPTION + " VARCHAR(255) , " + COLUMN_TASK_LIST_ID + " INTEGER NOT NULL);";


    public SQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.beginTransaction();
            db.execSQL(CREATE_TABLE_ROLE);
            db.execSQL(CREATE_TABLE_USER);
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

    public boolean createRole(@org.jetbrains.annotations.NotNull Role role){ //adaugarea unui nou rol
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, role.getName());
        values.put(COLUMN_ROLE_PROJECT_READ, role.getProject_read());
        values.put(COLUMN_ROLE_PROJECT_EDIT, role.getProject_edit());
        values.put(COLUMN_ROLE_PROJECT_DELETE, role.getProject_delete());
        values.put(COLUMN_ROLE_LIST_READ, role.getList_read());
        values.put(COLUMN_ROLE_LIST_EDIT, role.getList_edit());
        values.put(COLUMN_ROLE_LIST_DELETE, role.getList_delete());
        values.put(COLUMN_ROLE_TASK_READ, role.getTask_read());
        values.put(COLUMN_ROLE_TASK_EDIT, role.getTask_edit());
        values.put(COLUMN_ROLE_TASK_DELETE, role.getTask_delete());
        long role_id = db.insert(TABLE_ROLE, null, values); //inserarea randului cu rolul in tabel
        if (role_id == -1){
            return false;
        }else {
            return true;
        }
    }

    public List<Role> getAllRoles(){
        List<Role> returnAllRoles = new ArrayList<>();
        String queryGetAllRoles = "SELECT * FROM " + TABLE_ROLE + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryGetAllRoles, null);
        //parcurgerea cursorului prin rezultat pentru a crea noi obiecte de pus in lista
        if(cursor.moveToFirst()){
            do {
                int roleID = cursor.getInt(0);
                String roleName = cursor.getString(1);
                boolean readProject = cursor.getInt(2) ==1 ? true : false;
                boolean editProject = cursor.getInt(3) ==1 ? true : false;
                boolean deleteProject = cursor.getInt(4) ==1 ? true : false;
                boolean readTaskList = cursor.getInt(5) ==1 ? true : false;
                boolean editTaskList = cursor.getInt(6) ==1 ? true : false;
                boolean deleteTaskList = cursor.getInt(7) ==1 ? true : false;
                boolean readTask = cursor.getInt(8) ==1 ? true : false;
                boolean editTask = cursor.getInt(9) ==1 ? true : false;
                boolean deleteTask = cursor.getInt(10) ==1 ? true : false;
                Role newRole = new Role(roleID, roleName, readProject, editProject, deleteProject, readTaskList, editTaskList, deleteTaskList, readTask, editTask, deleteTask);
                returnAllRoles.add(newRole);
            }while (cursor.moveToNext());
        }else{
            // nu se adauga nimic in lista.
        }
        //se inchid cursorul si baza de date pentru a putea fi refolosite
        cursor.close();
        db.close();
        return returnAllRoles;
    }


}
