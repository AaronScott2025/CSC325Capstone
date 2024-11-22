package com.example.csc325capstone.Model;

public class Person {
    private String userID; //Friend Username
    private String record; //Friend Record
    private Hikes[] favorites; //Favorite Location
    private boolean favoritevisibility; //Can user's friends see favorite?

    public Person(String userID, String record) {
        this.userID = userID;
        this.record = record;
        this.favorites = favorites;
        this.favoritevisibility = favoritevisibility;
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

    public Hikes[] getFavorites() {
        return favorites;
    }

    public void setFavorites(Hikes[] favorites) {
        this.favorites = favorites;
    }

    public boolean isFavoritevisibility() {
        return favoritevisibility;
    }

    public void setFavoritevisibility(boolean favoritevisibility) {
        this.favoritevisibility = favoritevisibility;
    }
}