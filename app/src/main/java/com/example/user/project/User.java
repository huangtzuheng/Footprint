package com.example.user.project;

public class User {

//        USER_NAME
//        USER_PICTURE
//        USER_PHONE
//        USER_SCORE

    private long ID;
    private String NAME;
    private String PICTURE;
    private String PHONE;
    private int SCORE;

    public User(){

    }


    public User(long id, String name, String picture, String phone, int score) {
        this.ID = id;

        this.NAME = name;
        this.PICTURE = picture;
        this.PHONE = phone;
        this.SCORE = score;
    }

    public long getId() {
        return ID;
    }

    public void setID(long id) {
        this.ID = id;
    }

    public String getName(){ return NAME;}

    public void setName(String name) {this.NAME = name;}

    public String getPicture(){ return PICTURE;}

    public void setPicture(String pic) {this.PICTURE = pic;}

    public String getPhone(){ return PHONE;}

    public void setPhone(String phone) {this.PHONE = phone;}

    public int getScore(){ return SCORE;}

    public void setSCORE(int score) {this.SCORE = score;}

}
