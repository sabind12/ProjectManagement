package com.sabin.projectmanagement;

public class TaskList {

    int id;
    String name;
    String description;
    String icon;
    int project_id;

    public TaskList() {    }

    public TaskList(int id, String name, String description, String icon, int project_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.project_id = project_id;
    }

    public TaskList(String name, String description, String icon, int project_id) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.project_id = project_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
