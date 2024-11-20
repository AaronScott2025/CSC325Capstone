package com.example.csc325capstone.Model;

public class Person {
    private String userID; //Friend Username
    private String record; //Friend Record


    public Person(String userID, String record) {
        this.userID = userID;
        this.record = record;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

}