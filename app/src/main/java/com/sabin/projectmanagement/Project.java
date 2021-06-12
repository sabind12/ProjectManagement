package com.sabin.projectmanagement;

import java.util.ArrayList;
import java.util.List;

public class Project {                  //Clasa model pentru proiect
    int id;
    String name;
    String description;
    String createdTime;
    String deadline;
    int createdByUserId;
    String sharedToUsersId;

    public Project() {    }

    public Project(String name, String description, String createdTime, String deadline, int createdByUserId, String sharedToUsersId) {
        this.name = name;
        this.description = description;
        this.createdTime = createdTime;
        this.deadline = deadline;
        this.createdByUserId = createdByUserId;
        this.sharedToUsersId = sharedToUsersId;
    }

    public Project(int id, String name, String description, String createdTime, String deadline, int createdByUserId, String sharedToUsersId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdTime = createdTime;
        this.deadline = deadline;
        this.createdByUserId = createdByUserId;
        this.sharedToUsersId = sharedToUsersId;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getSharedToUsersId() {
        return sharedToUsersId;
    }

    public void setSharedToUsersId(String sharedToUsersId) {
        this.sharedToUsersId = sharedToUsersId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", createdByUserId=" + createdByUserId +
                ", sharedToUsersId=" + sharedToUsersId +
                '}';
    }
}
