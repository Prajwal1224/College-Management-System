package com.example.collegeapp.ui.notice;

public class noticedata {


    String image,date,title,time,key;
    public  noticedata(){}

    public noticedata(String image, String date, String title, String time, String key) {
        this.image = image;
        this.date = date;
        this.title = title;
        this.time = time;
        this.key = key;
    }




    public String getTitle()
    {
        return title;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



}
