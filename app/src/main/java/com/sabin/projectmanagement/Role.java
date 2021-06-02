package com.sabin.projectmanagement;

public class Role {
    String name;
    Boolean project_read;
    Boolean project_edit;
    Boolean project_delete;
    Boolean list_read;
    Boolean list_edit;
    Boolean list_delete;
    Boolean task_read;
    Boolean task_edit;
    Boolean task_delete;

    public Role() { }

    public Role(String name, Boolean project_read, Boolean project_edit, Boolean project_delete, Boolean list_read, Boolean list_edit, Boolean list_delete, Boolean task_read, Boolean task_edit, Boolean task_delete) {
        this.name = name;
        this.project_read = project_read;
        this.project_edit = project_edit;
        this.project_delete = project_delete;
        this.list_read = list_read;
        this.list_edit = list_edit;
        this.list_delete = list_delete;
        this.task_read = task_read;
        this.task_edit = task_edit;
        this.task_delete = task_delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getProject_read() {
        return project_read;
    }

    public void setProject_read(Boolean project_read) {
        this.project_read = project_read;
    }

    public Boolean getProject_edit() {
        return project_edit;
    }

    public void setProject_edit(Boolean project_edit) {
        this.project_edit = project_edit;
    }

    public Boolean getProject_delete() {
        return project_delete;
    }

    public void setProject_delete(Boolean project_delete) {
        this.project_delete = project_delete;
    }

    public Boolean getList_read() {
        return list_read;
    }

    public void setList_read(Boolean list_read) {
        this.list_read = list_read;
    }

    public Boolean getList_edit() {
        return list_edit;
    }

    public void setList_edit(Boolean list_edit) {
        this.list_edit = list_edit;
    }

    public Boolean getList_delete() {
        return list_delete;
    }

    public void setList_delete(Boolean list_delete) {
        this.list_delete = list_delete;
    }

    public Boolean getTask_read() {
        return task_read;
    }

    public void setTask_read(Boolean task_read) {
        this.task_read = task_read;
    }

    public Boolean getTask_edit() {
        return task_edit;
    }

    public void setTask_edit(Boolean task_edit) {
        this.task_edit = task_edit;
    }

    public Boolean getTask_delete() {
        return task_delete;
    }

    public void setTask_delete(Boolean task_delete) {
        this.task_delete = task_delete;
    }
}
