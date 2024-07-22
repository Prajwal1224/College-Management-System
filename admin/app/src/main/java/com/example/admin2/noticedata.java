package com.example.admin2;

public class noticedata {

    String image,date,title,time,key;

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

}
