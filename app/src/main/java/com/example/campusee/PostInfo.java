package com.example.campusee;

import android.net.Uri;

import java.util.ArrayList;

public class PostInfo {
    public String email;
    public String postDescription;
    public String postTitle;
    public ArrayList<Uri> imageList;
    public PostInfo(String email, String postDescription, String postTitle, ArrayList<Uri> imageList){
        this.email = email;
        this.postDescription = postDescription;
        this.postTitle = postTitle;
        this.imageList = imageList;
    }
}
