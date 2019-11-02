package com.example.campusee;

import java.util.ArrayList;

public class DB_Post {
    public String AuthorEmail;
    public String Title;
    public String Description;
    public DateWrapper dateSelected;
    public TimeWrapper timeSelected;
    public ArrayList<String> ImageUrlList;
    public ArrayList<String> DownloadUrls;

    public DB_Post(String AuthorEmail, String Title, String Description, ArrayList<String> ImageUrlList, ArrayList<String> DownloadUrls, DateWrapper dw, TimeWrapper tw){
        this.AuthorEmail = AuthorEmail;
        this.Title = Title;
        this.Description = Description;
        this.ImageUrlList = ImageUrlList;
        this.DownloadUrls = DownloadUrls;
        this.dateSelected = dw;
        this.timeSelected = tw;
    }

    public DB_Post() {

    }
}