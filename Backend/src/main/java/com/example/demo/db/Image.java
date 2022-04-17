package com.example.demo.db;

public class Image {

    private int userID;
    private byte[] image;


    public Image(int id, byte[] image) {
        this.userID = id;
        this.image = image;
    }

    public int getUserID() {
        return userID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
