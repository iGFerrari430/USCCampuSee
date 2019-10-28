package com.example.campusee;

// Used to organize login/signup requests.
public class User {
    public boolean isPublisher = false;
    public String email;
    public String password;
    public User(boolean isPublisher, String Username, String Password){
        this.isPublisher = isPublisher;
        this.email = Username;
        this.password = Password;
    }
}
