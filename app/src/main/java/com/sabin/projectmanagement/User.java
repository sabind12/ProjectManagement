package com.sabin.projectmanagement;

public class User {             //clasa model pentru utilizator
    int id;
    String name;
    String email;
    String password;
    String role_name;

    public User() {    }

    public User(int id, String name, String email, String password, String role_name) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role_name = role_name;
    }

    public User(String name, String email, String password, String role_name) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role_name = role_name;
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

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
