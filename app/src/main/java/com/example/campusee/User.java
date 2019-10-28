package com.example.campusee;

public class User {
    public boolean isPublisher = false;
    public String username;
    public String password;
    public User(boolean isPublisher, String Username, String Password){
        this.isPublisher = isPublisher;
        this.username = Username;
        this.password = Password;
    }
}
