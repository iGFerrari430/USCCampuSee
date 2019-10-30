package com.example.campusee;

import android.net.Uri;

import java.util.ArrayList;

public class PostInfo {
    public String email;
    public String postDescription;
    public String postTopic;
    public ArrayList<Uri> imageList;
    public PostInfo(String email, String postDescription, String postTopic, ArrayList<Uri> imageList){
        this.email = email;
        this.postDescription = postDescription;
        this.postTopic = postTopic;
        this.imageList = imageList;
    }
}
