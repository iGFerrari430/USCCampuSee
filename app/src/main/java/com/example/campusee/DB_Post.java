package com.example.campusee;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class DB_Post implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DB_Post createFromParcel(Parcel in) {
            return new DB_Post(in);
        }

        public DB_Post[] newArray(int size) {
            return new DB_Post[size];
        }
    };

    public String AuthorEmail;
    public String Title;
    public String Description;
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public ArrayList<String> ImageUrlList;
    public ArrayList<String> DownloadUrls;

    public DB_Post(String AuthorEmail, String Title, String Description, ArrayList<String> ImageUrlList, ArrayList<String> DownloadUrls,
                   int year, int month, int day, int hour, int minute){
        this.AuthorEmail = AuthorEmail;
        this.Title = Title;
        this.Description = Description;
        this.ImageUrlList = ImageUrlList;
        this.DownloadUrls = DownloadUrls;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public DB_Post() {

    }


    public DB_Post(Parcel in){
        this.AuthorEmail = in.readString();
        this.Title = in.readString();
        this.Description = in.readString();
        //this.dateSelected
        this.ImageUrlList = in.readArrayList(null);
        this.DownloadUrls = in.readArrayList(null);
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
        this.hour = in.readInt();
        this.minute = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest,int flags){
        dest.writeList(ImageUrlList);
        dest.writeList(DownloadUrls);
        dest.writeString(AuthorEmail);
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(hour);
        dest.writeInt(minute);
    }

    @Override
    public String toString() {
        return "DB_Post{" + " DownloadUrllist= "+DownloadUrls.toString()+"\n"+
                " ImageUrls: " +ImageUrlList.toString()+"\n"+
                " year: "+year+" AuthorEmail: "+AuthorEmail.toString()+
                " Rest are ignored";
    }
}