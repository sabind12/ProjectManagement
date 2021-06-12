package com.sabin.projectmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {            //clasa pentru citirea si scrierea in baza de date SQLite
    public static final String LOG = "DBHelper"; //Tag pentru inregistrare loguri
    public static final String DB_NAME = "project.db"; //Nume baza de date
                                                                        //crearea variabilelor String pentru numele tabalelor si coloanelor din baza de date
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
        public static final String COLUMN_PROJECT_CREATEDTIME = "project_createdtime";
        public static final String COLUMN_PROJECT_DEADLINE = "project_deadline";
        public static final String COLUMN_PROJECT_CREATEDBYUSERID = "project_createdbyuserid";
        public static final String COLUMN_PROJECT_SHAREDTOUSERID = "project_sharedtouserid";
    //Definire variabile nume tabel lista task-uri si nume coloane tabel lista task-uri
    public static final String TABLE_LIST = "list";
        public static final String COLUMN_LIST_ID = "list_id";
        public static final String COLUMN_LIST_NAME = "list_name";
        public static final String COLUMN_LIST_DESCRIPTION = "list_description";
        public static final String COLUMN_LIST_ICON = "list_icon";
        public static final String COLUMN_LIST_PROJECT_ID = "list_project_id";
    //Definire variabile nume tabel task-uri si nume coloane tabel task-uri
    public static final String TABLE_TASK = "task";
        public static final String COLUMN_TASK_ID = "task_id";
        public static final String COLUMN_TASK_NAME = "task_name";
        public static final String COLUMN_TASK_DESCRIPTION = "task_description";
        public static final String COLUMN_TASK_LIST_ID = "task_list_id";
    //Crearea query-urilor in variabile String pentru creerea initiala a tabelelor din baza de date
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " VARCHAR(255) , " + COLUMN_USER_EMAIL + " VARCHAR(255) , " + COLUMN_USER_PASSWORD + " VARCHAR(255) , " + COLUMN_USER_ROLE + " VARCHAR(255));";
    public static final String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + " (" + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ROLE_NAME + " VARCHAR(255), " + COLUMN_ROLE_PROJECT_READ + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_PROJECT_READ + " IN (0, 1)) , "
            + COLUMN_ROLE_PROJECT_EDIT + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_PROJECT_EDIT + " IN (0, 1)) , " + COLUMN_ROLE_PROJECT_DELETE + " BOOLEAN NOT NULL CHECK ("+ COLUMN_ROLE_PROJECT_DELETE + " IN (0, 1)) , "
            + COLUMN_ROLE_LIST_READ + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_LIST_READ + " IN (0, 1)) , " + COLUMN_ROLE_LIST_EDIT + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_LIST_EDIT + " IN (0, 1)) , "
            + COLUMN_ROLE_LIST_DELETE + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_LIST_DELETE + " IN (0, 1)) , " + COLUMN_ROLE_TASK_READ + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_TASK_READ + " IN (0, 1)) , "
            + COLUMN_ROLE_TASK_EDIT + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_TASK_EDIT + " IN (0, 1)) , " + COLUMN_ROLE_TASK_DELETE + " BOOLEAN NOT NULL CHECK (" + COLUMN_ROLE_TASK_DELETE + " IN (0, 1)));";
    public static final String CREATE_TABLE_PROJECT = "CREATE TABLE " + TABLE_PROJECT + " (" + COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PROJECT_NAME + " TEXT ," + COLUMN_PROJECT_DESCRIPTION + " TEXT ," + COLUMN_PROJECT_CREATEDTIME + " DATETIME ," + COLUMN_PROJECT_DEADLINE + " DATETIME ,"
            + COLUMN_PROJECT_CREATEDBYUSERID + " INTEGER ," + COLUMN_PROJECT_SHAREDTOUSERID + " TEXT);";
    public static final String CREATE_TABLE_LIST = "CREATE TABLE " + TABLE_LIST + " (" + COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LIST_NAME + " VARCHAR(255) ," + COLUMN_LIST_DESCRIPTION + " TEXT ," + COLUMN_LIST_ICON + " VARCHAR(255) ," + COLUMN_LIST_PROJECT_ID + " INTEGER NOT NULL);";
    public static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + " (" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TASK_NAME + " VARCHAR(255) ," + COLUMN_TASK_DESCRIPTION + " TEXT , " + COLUMN_TASK_LIST_ID + " INTEGER NOT NULL);";


    public SQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {       //creerea initiala a bazei de date si a tabelelor

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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {      //procedura de upgrade a bazei de date
        if (oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LIST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TASK);
        onCreate(db);
    }
                                                                                    //funcii pentru diferite tipuri de accesari in fiecare tabel
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
        db.close();
        if (role_id == -1){
            return false;
        }else {
            return true;
        }
    }

    public Role getRole(String roleName){                               //returnarea unui singur rol
        SQLiteDatabase db =this.getReadableDatabase();
        String queryRole = "SELECT * FROM " + TABLE_ROLE + " WHERE " + COLUMN_ROLE_NAME + " = '" + roleName + "';";

        Log.e(LOG, queryRole);

        Cursor cursor = db.rawQuery(queryRole,null);

        if (cursor !=null)
            cursor.moveToFirst();
        Role returnedRole = new Role();
        returnedRole.setId(cursor.getInt(0));
        returnedRole.setName(cursor.getString(1));
        returnedRole.setProject_read(cursor.getInt(2) == 1);
        returnedRole.setProject_edit(cursor.getInt(3) == 1);
        returnedRole.setProject_delete(cursor.getInt(4) == 1);
        returnedRole.setList_read(cursor.getInt(5) == 1);
        returnedRole.setList_edit(cursor.getInt(6) == 1);
        returnedRole.setList_delete(cursor.getInt(7) == 1);
        returnedRole.setTask_read(cursor.getInt(8) == 1);
        returnedRole.setTask_edit(cursor.getInt(9) == 1);
        returnedRole.setTask_delete(cursor.getInt(10) == 1);

        cursor.close();
        db.close();

        return returnedRole;
    }


    public ArrayList<Role> getAllRoles(){                       //returnarea tuturor rolurilor
        ArrayList<Role> returnAllRoles = new ArrayList<>();
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

    public int checkIsRole(String roleName){                    //verificarea existentei unui rol
        SQLiteDatabase db = this.getReadableDatabase();
        String queryCheckRole = "SELECT " + COLUMN_ROLE_ID + ", " + COLUMN_ROLE_NAME + " FROM " + TABLE_ROLE + ";";
        Cursor cursor = db.rawQuery(queryCheckRole, null);
        if(cursor.moveToFirst()){
            do{
                int readId = cursor.getInt(cursor.getColumnIndex(COLUMN_ROLE_ID));
                String readRole = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE_NAME));
                if (readRole.equals(roleName))
                    return readId;
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return -1;
    }

    public int editRole(Role role){                     //editarea unui rol
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
        if(checkIsRole(role.name) == -1)
            return -1;
        int returnedRoleId =db.update(TABLE_ROLE, values, COLUMN_ROLE_ID + " = ?",new String[]{String.valueOf(role.getId())});
        db.close();
        return returnedRoleId;
    }

    public int deleteRole(String roleName){                     //stergerea unui rol
        SQLiteDatabase db = this.getWritableDatabase();
        int roleCheck = checkIsRole(roleName);
        if (roleCheck == -1) {
            return -1;
        }
        return db.delete(TABLE_ROLE,COLUMN_ROLE_ID + " = ?",new String[]{String.valueOf(checkIsRole(roleName))});

    }

    public int checkIsUserEmail(String email){                  //verificarea existentei unui mail
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?", new String[]{email});
        int userNumber = cursor.getCount();
        cursor.close();
        db.close();
        return userNumber;
    }

    public int checkUserEmailPassCombo(String email, String password){          //verificarea combinatiei user/parola
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?", new String[]{email});
        int userId = -1;
        String dbPassword;
        if (cursor != null)
        cursor.moveToFirst();
            dbPassword = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD));
            if (password.equals(dbPassword))
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
        else userId = -1;
        cursor.close();
        db.close();
        return userId;
    }

    public int editUser(User user){                                         //editarea unui utilizator
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ROLE, user.getRole_name());
        int editResult = db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
        return editResult;
    }

    public long createUser(User user){                  //crearea unui utilizator
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ROLE, user.getRole_name());
        long createResult = db.insert(TABLE_USER, null,values);

        db.close();
        return createResult;
    }

    public User getUser(int userId){                    //returnarea unui utilizator
        SQLiteDatabase db = getReadableDatabase();
        String queryGetUser = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = " + userId;
        Cursor cursor = db.rawQuery(queryGetUser, null);

        if (cursor != null)
            cursor.moveToFirst();
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        user.setRole_name(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE)));
        cursor.close();
        db.close();
        return user;
    }

    public int getUserId(String email){                     //returnarea id-ului unui utilizator folosind email-ul
        SQLiteDatabase db = getReadableDatabase();
        String queryGetUserID = "SELECT * FROM " + TABLE_USER +  " WHERE " + COLUMN_USER_EMAIL + " = " + email;
        Cursor cursor = db.rawQuery(queryGetUserID, null);
        int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
        cursor.close();
        db.close();
        return userId;
    }

    public ArrayList<User> getAllUsers(){                   //returnarea tuturor utilizatorilor
        ArrayList<User> allUsers = new ArrayList<User>();
        String queryGetAllUsers = "SELECT * FROM " + TABLE_USER;
        Log.e(LOG, queryGetAllUsers);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllUsers, null);
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setRole_name(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE)));
                allUsers.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allUsers;
    }

    public int deleteUser(int userId){                              //stergerea unui utilizator
        SQLiteDatabase db = getWritableDatabase();
        int deletedUserId = db.delete(TABLE_USER, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
        return deletedUserId;
    }

    public long createProject(Project project){                     //crearea unui proiect
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_NAME, project.getName());
        values.put(COLUMN_PROJECT_DESCRIPTION, project.getDescription());
        values.put(COLUMN_PROJECT_CREATEDTIME, project.getCreatedTime());
        values.put(COLUMN_PROJECT_DEADLINE, project.getDeadline());
        values.put(COLUMN_PROJECT_CREATEDBYUSERID, project.getCreatedByUserId());
        values.put(COLUMN_PROJECT_SHAREDTOUSERID, project.getSharedToUsersId().toString());
        long projectId = db.insert(TABLE_PROJECT, null, values);
        db.close();
        return projectId;
    }

    public Project getProject(int projectId){                       //returnarea unui proiect
        SQLiteDatabase db = this.getReadableDatabase();
        String queryGetProject = "SELECT * FROM " + TABLE_PROJECT + " WHERE " + COLUMN_PROJECT_ID + " = " + projectId + " ;";
        Cursor cursor = db.rawQuery(queryGetProject, null);
        if (cursor != null)
            cursor.moveToFirst();
        Project project = new Project();
        project.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_ID)));
        project.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)));
        project.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_DESCRIPTION)));
        project.setCreatedTime(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_CREATEDTIME)));
        project.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_DEADLINE)));
        project.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_CREATEDBYUSERID)));
        project.setSharedToUsersId(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_SHAREDTOUSERID)));
        cursor.close();
        db.close();
        return project;
    }

    public ArrayList<Project> getAllProjects(){                 //returnarea tuturor proiectelor
        ArrayList<Project> allProjects = new ArrayList<Project>();
        String querygetAllProjects = "SELECT * FROM " + TABLE_PROJECT + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querygetAllProjects, null);
        if (cursor.moveToFirst()){
            do {
                Project project = new Project();
                project.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_ID)));
                project.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)));
                project.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_DESCRIPTION)));
                project.setCreatedTime(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_CREATEDTIME)));
                project.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_DEADLINE)));
                project.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_CREATEDBYUSERID)));
                project.setSharedToUsersId(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_SHAREDTOUSERID)));
                allProjects.add(project);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allProjects;
    }

    public int editProject (Project project){                           //editarea unui proiect
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_NAME, project.getName());
        values.put(COLUMN_PROJECT_DESCRIPTION, project.getDescription());
        values.put(COLUMN_PROJECT_CREATEDTIME, project.getCreatedTime());
        values.put(COLUMN_PROJECT_DEADLINE, project.getDeadline());
        values.put(COLUMN_PROJECT_CREATEDBYUSERID, project.getCreatedByUserId());
        values.put(COLUMN_PROJECT_SHAREDTOUSERID, project.getSharedToUsersId());
        int projectId = db.update(TABLE_PROJECT, values, COLUMN_PROJECT_ID + " = ?", new String[]{String.valueOf(project.getId())});
        db.close();
        return projectId;
    }

    public int deleteProject (Project project){                         //stergerea unui proiect
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedid = db.delete(TABLE_PROJECT, COLUMN_PROJECT_ID + " = ? ", new String[]{String.valueOf(project.getId())});
        db.close();
        return deletedid;
    }

    public ArrayList<ArrayList<Task>> getAllTaskListsTasks(int projectID){          //returnarea tuturor Task-urilor in cate un ArrayList pentru fiecare lista de Task-uri
        ArrayList<ArrayList<Task>> projectLists = new ArrayList<>();
        ArrayList<Integer> listIds = new ArrayList<>();
        ArrayList<TaskList> lists = (ArrayList<TaskList>) getAllTaskLists(projectID);
        for (int i = 0; i < lists.size(); i++) {
            listIds.add(lists.get(i).getId());
        }
        for (int i = 1; i <= listIds.size(); i++) {
            ArrayList<Task> tasks;
            tasks = (ArrayList<Task>) getListTasks(i);
            projectLists.add(tasks);
        }
        return projectLists;
    }

    public long createTaskList (TaskList taskList){                     //crearea unei liste de Task-uri
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_NAME, taskList.getName());
        values.put(COLUMN_LIST_DESCRIPTION, taskList.getDescription());
        values.put(COLUMN_LIST_ICON, taskList.getIcon());
        values.put(COLUMN_LIST_PROJECT_ID, taskList.getProject_id());
        long taskListId =db.insert(TABLE_LIST, null, values);
        db.close();
        return taskListId;
    }

    public TaskList getTaskList ( int taskListId){                      //returnarea unei liste de Task-uri
        SQLiteDatabase db = this.getReadableDatabase();
        String queryGetTaskList = "SELECT * FROM " + TABLE_LIST + " WHERE " + COLUMN_LIST_ID + " = " + taskListId + " ;";
        Cursor cursor = db.rawQuery(queryGetTaskList,null);
        if (cursor != null)
            cursor.moveToFirst();
        TaskList taskList = new TaskList();
        taskList.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_LIST_ID)));
        taskList.setName(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_NAME)));
        taskList.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_DESCRIPTION)));
        taskList.setIcon(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_ICON)));
        taskList.setProject_id(cursor.getInt(cursor.getColumnIndex(COLUMN_LIST_PROJECT_ID)));
        cursor.close();
        db.close();
        return taskList;
    }

    public int getTaskListIdByName (String taskListName){               //returnarea unei liste de Task-uri avand numele listei
        SQLiteDatabase db = getReadableDatabase();
        String queryGetTaskListIdByName = "SELECT " + COLUMN_LIST_ID + " FROM " + TABLE_LIST + " WHERE " + COLUMN_LIST_NAME + " = '" + taskListName + "' ;";
        Cursor cursor = db.rawQuery(queryGetTaskListIdByName, null);
        if (cursor != null)
            cursor.moveToFirst();
        int taskListId = cursor.getInt(cursor.getColumnIndex(COLUMN_LIST_ID));
        cursor.close();
        db.close();
        return taskListId;
    }

    public ArrayList<TaskList> getAllTaskLists (int projectId){         //returnarea tuturor listelor de Task-uri
        ArrayList<TaskList> taskLists = new ArrayList<>();
        String queryGetAllTaskLists = "SELECT * FROM " + TABLE_LIST + " WHERE " + COLUMN_LIST_PROJECT_ID + " = " + projectId + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllTaskLists,null);
        if (cursor.moveToFirst()){
            do {
                TaskList taskList = new TaskList();
                taskList.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_LIST_ID)));
                taskList.setName(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_NAME)));
                taskList.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_DESCRIPTION)));
                taskList.setIcon(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_ICON)));
                taskList.setProject_id(cursor.getInt(cursor.getColumnIndex(COLUMN_LIST_PROJECT_ID)));
                taskLists.add(taskList);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskLists;
    }

    public int editTaskList(TaskList taskList) {                    //modificarea unei liste de Task-uri
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_NAME, taskList.getName());
        values.put(COLUMN_LIST_DESCRIPTION, taskList.getDescription());
        values.put(COLUMN_LIST_ICON, taskList.getIcon());
        values.put(COLUMN_LIST_PROJECT_ID, taskList.getProject_id());
        int taskListId =db.update(TABLE_LIST, values, COLUMN_LIST_ID + " = ?", new String[]{String.valueOf(taskList.getId())});
        db.close();
        return taskListId;
    }

    public int deleteTaskList(int taskListId){                      //stergerea unei liste de Task-uri si mutarea celor ramase spre inceputul tabelului
        ArrayList<TaskList> taskList = getAllTaskLists(1);
        ArrayList<Task> listTasks;
        listTasks = getListTasks(taskListId);
        for (int i = 0; i < listTasks.size() ; i++) {
            deleteTask(listTasks.get(i).getId());
        }
        SQLiteDatabase db = this.getWritableDatabase();
        int taskDeleted = db.delete(TABLE_LIST,COLUMN_LIST_ID + " = ?", new String[]{String.valueOf(taskListId)});
        db.close();
        taskList.remove(taskListId-1);
        for (int i = taskListId; i <= taskList.size(); i++) {
            listTasks.clear();
            listTasks = getListTasks(i+1);
            for (int j = 0; j < listTasks.size(); j++) {              //schimbarea id-urilor Task-urilor
                listTasks.get(j).setList_id(i);
                editTask(listTasks.get(j));
            }
            taskList.get(i-1).setId(i);                                 //schimbarea id-urilor listelor de Task-uri

            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_LIST_ID, taskList.get(i-1).getId());
            values.put(COLUMN_LIST_NAME, taskList.get(i-1).getName());
            values.put(COLUMN_LIST_DESCRIPTION, taskList.get(i-1).getDescription());
            values.put(COLUMN_LIST_ICON, taskList.get(i-1).getIcon());
            values.put(COLUMN_LIST_PROJECT_ID, taskList.get(i-1).getProject_id());
            db.update(TABLE_LIST, values, COLUMN_LIST_ID + " = ?", new String[]{String.valueOf(taskList.get(i-1).getId()+1)});
            db.close();
        }

        return taskDeleted;
    }

    public int insertTaskList(int currentTaskListId, TaskList newTaskList){             //inserarea unei liste goale la pozitia curenta
        ArrayList<TaskList> taskList = getAllTaskLists(1);
        int dbtaskListSize = taskList.size();
        ArrayList<Task> listTasks = new ArrayList<>();
        taskList.add(currentTaskListId-1, newTaskList);
        for (int i = taskList.size(); i >= currentTaskListId; i--) {                    //mutarea tutror Task-urilor si listelor de Task-uri spre sfarsit
            listTasks.clear();
            listTasks = getListTasks(i);
            for (int j = 0; j < listTasks.size(); j++) {
                listTasks.get(j).setList_id(listTasks.get(j).getList_id()+1);
                editTask(listTasks.get(j));
            }
            TaskList currentElement = new TaskList();
            currentElement=taskList.get(i-1);
            currentElement.setId(i);
            ContentValues values = new ContentValues();
            values.put(COLUMN_LIST_ID, currentElement.getId());
            values.put(COLUMN_LIST_NAME, currentElement.getName());
            values.put(COLUMN_LIST_DESCRIPTION, currentElement.getDescription());
            values.put(COLUMN_LIST_ICON, currentElement.getIcon());
            values.put(COLUMN_LIST_PROJECT_ID, currentElement.getProject_id());
            if (taskList.size() > dbtaskListSize) {
                dbtaskListSize++;
                SQLiteDatabase db = this.getWritableDatabase();
                db.insert(TABLE_LIST, null, values);
                db.close();
            }else {
                SQLiteDatabase db = this.getWritableDatabase();
                db.update(TABLE_LIST, values, COLUMN_LIST_ID + " = ?", new String[]{String.valueOf(currentElement.getId())});
                db.close();
            }
        }
        int insertedTaskListId = taskList.get(currentTaskListId-1).getId();
        return insertedTaskListId;
    }

    public long createTask(Task task){                  //crearea unui task
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_LIST_ID, task.getList_id());
        long taskId = db.insert(TABLE_TASK, null, values);
        db.close();
        return taskId;
    }
    public Task getTask(int taskId){                    //returnarea unui task
        String queryGetTask = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_ID + " = " + taskId + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetTask, null);
        if (cursor != null)
            cursor.moveToFirst();
        Task task = new Task();
        task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
        task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
        task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
        task.setList_id(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_LIST_ID)));
        cursor.close();
        db.close();
        return task;
    }

    public ArrayList<Task> getAllTasks(){               //returnarea tuturor Task-urilor
        ArrayList<Task> allTasks = new ArrayList<>();
        String queryGetAllTasks = "SELECT * FROM " + TABLE_TASK + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllTasks, null);
        if (cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
                task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                task.setList_id(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_LIST_ID)));
                allTasks.add(task);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allTasks;
    }

    public ArrayList<Task> getListTasks(int listId){                    //returnarea tuturor Task-urilor unei liste
        ArrayList<Task> listTasks = new ArrayList<>();
        String queryGetAllTasks = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_LIST_ID + " = " + listId + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllTasks, null);
        if (cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
                task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                task.setList_id(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_LIST_ID)));
                listTasks.add(task);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listTasks;
    }

    public int editTask(Task task){                     //modificarea unui Task
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_LIST_ID, task.getList_id());
        int taskId = db.update(TABLE_TASK, values,COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(task.getId())} );
        db.close();
        return taskId;
    }

    public int deleteTask(int taskId){                  //Stergerea unui Task
        SQLiteDatabase db = getWritableDatabase();
        int deletedTaskId = db.delete(TABLE_TASK,COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(taskId)} );
        db.close();
        return deletedTaskId;
    }
}
