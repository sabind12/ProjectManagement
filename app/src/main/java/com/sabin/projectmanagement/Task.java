package com.sabin.projectmanagement;

public class Task {
    int id;
    String name;
    String description;
    int list_id;

    public Task() {    }

    public Task(int id, String name, String description, int list_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.list_id = list_id;
    }

    public Task(String name, String description, int list_id) {
        this.name = name;
        this.description = description;
        this.list_id = list_id;
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

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }
}