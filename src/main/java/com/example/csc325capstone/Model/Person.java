package com.example.csc325capstone.Model;

import com.example.csc325capstone.Model.Locations;

public class Person {
    private String userID; //Friend Username
    private String record; //Friend Record
    private Locations[] favorites; //Favorite Location
    private boolean favoritevisibility; //Can user's friends see favorite?

    public Person(String userID, String record, Locations[] favorites, boolean favoritevisibility) {
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

    public Locations[] getFavorites() {
        return favorites;
    }

    public void setFavorites(Locations[] favorites) {
        this.favorites = favorites;
    }

    public boolean isFavoritevisibility() {
        return favoritevisibility;
    }

    public void setFavoritevisibility(boolean favoritevisibility) {
        this.favoritevisibility = favoritevisibility;
    }
}
