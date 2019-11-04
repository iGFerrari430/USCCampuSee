package com.example.campusee;

import java.util.ArrayList;
import java.util.List;

// used to convert Firebase object into java object.
public class DB_User {
    public String Email;
    public String Password;
    public List<DB_User> Subscribed;

    public DB_User(){
        // Default no-arg constructor are required by fire-storage
    }

    public DB_User(String Email, String Password, List<DB_User> Subscribed){
        this.Email = Email;
        this.Password = Password;
        this.Subscribed = Subscribed;
    }

    public DB_User(String Email, String Password){
        this.Email = Email;
        this.Password = Password;
        this.Subscribed = new ArrayList<>();
    }
}
