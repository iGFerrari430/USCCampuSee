package com.example.campusee;

import java.util.ArrayList;
import java.util.List;

// used to convert Firebase object into java object.
public class DB_User {
    public String Email;
    public String Password;
    public List<DB_User> subPublisher;
    public List<DB_Post> subPost;

    public DB_User(){
        // Default no-arg constructor are required by fire-storage
    }

    public DB_User(String Email, String Password, List<DB_User> SubscribedPublisher, List<DB_Post> SubscribedPost){
        this.Email = Email;
        this.Password = Password;
        this.subPublisher = SubscribedPublisher;
        this.subPost = SubscribedPost;

    }

    public DB_User(String Email, String Password){
        this.Email = Email;
        this.Password = Password;
        this.subPublisher = new ArrayList<>();
        this.subPost = new ArrayList<>();
    }
}
