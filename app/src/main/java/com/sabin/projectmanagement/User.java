package com.sabin.projectmanagement;

public class User {
    int id;
    String name;
    String email;
    String password;
    int role_id;

    public User() {    }

    public User(int id, String name, String email, String password, int role_id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    public User(String name, String email, String password, int role_id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}