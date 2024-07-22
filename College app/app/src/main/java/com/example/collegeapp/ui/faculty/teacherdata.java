package com.example.collegeapp.ui.faculty;

public class teacherdata {

    private String  name, post, mail, image, key;

    public teacherdata(){


    }


    public teacherdata(String name, String post, String mail, String image, String key) {
        this.name = name;
        this.post = post;
        this.mail = mail;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
