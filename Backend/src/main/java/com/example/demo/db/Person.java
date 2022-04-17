package com.example.demo.db;

public class Person {

    private int userID;
    private String fullName;
    private String email;



    public Person(int id, String fullName, String email) {
        this.userID = id;
        this.fullName = fullName;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
                "Person[id=%d, fullname='%s', email='%s']",
                userID, fullName, email);
    }

    public int getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
