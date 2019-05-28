package com.example.user.project;



// 資料功能類別
public class Item {
    private long ID;
    private String TITLE;
    private int PRICE;
    private int SIZE;
    private String DESCRIPTION;
    private String PICTURE;
    private long USER_ID;

    public Item(){

    }


    public Item(long id, String TITLE, int PRICE, int SIZE, String DESCRIPTION, String PIC, long UID) {
        this.ID = id;

        this.TITLE = TITLE;
        this.PRICE = PRICE;
        this.SIZE = SIZE;
        this.DESCRIPTION = DESCRIPTION;
        this.PICTURE = PIC;
        this.USER_ID = UID;
    }

    public long getId() {
        return ID;
    }

    public void setID(long id) {
        this.ID = id;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public int getSIZE() {
        return SIZE;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getPICTURE() {
        return PICTURE;
    }

    public void setPICTIRE(String PIC1) {
        this.PICTURE = PIC1;
    }

    public long getUId() {
        return USER_ID;
    }

    public void setUID(long uid) {
        this.USER_ID = uid;
    }

}