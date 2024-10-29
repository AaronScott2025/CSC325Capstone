package com.example.csc325capstone.Model;


import com.example.csc325capstone.Model.Journey;
import com.example.csc325capstone.Model.Locations;
import com.example.csc325capstone.Model.Person;

public class User extends Person {

    private String userID; //Username
    private String password; //User's Encrypted Password (SEE LOGIN)
    private String record; //Best travel
    private Person[] friendsList; //List of Friends
    private Locations[] favorites; //List of Favorite Locations  |  MAX 5
    private String located; //Current Location (IP BASED)
    private Journey[] journies; //Last 10 hikes
    private boolean favoritevisibility;
    private String securityAnswers;

    public User(String userID, String record, Locations[] favorites, boolean favoritevisibility, String password, Person[] friendsList, String located, Journey[] journies,String securityAnswers) {
        super(userID, record, favorites, favoritevisibility);
        this.password = password;
        this.friendsList = friendsList;
        this.located = located;
        this.journies = journies;
        this.securityAnswers = securityAnswers;
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getRecord() {
        return record;
    }

    @Override
    public void setRecord(String record) {
        this.record = record;
    }

    public Person[] getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(Person[] friendsList) {
        this.friendsList = friendsList;
    }

    @Override
    public Locations[] getFavorites() {
        return favorites;
    }

    public void setFavorites(Locations[] favorites) {
        this.favorites = favorites;
    }

    public String getLocated() {
        return located;
    }

    public void setLocated(String located) {
        this.located = located;
    }

    public Journey[] getJournies() {
        return journies;
    }

    public void setJournies(Journey[] journies) {
        this.journies = journies;
    }

    @Override
    public boolean isFavoritevisibility() {
        return favoritevisibility;
    }

    @Override
    public void setFavoritevisibility(boolean favoritevisibility) {
        this.favoritevisibility = favoritevisibility;
    }

    public String getSecurityAnswers() {
        return securityAnswers;
    }

    public void setSecurityAnswers(String securityAnswers) {
        this.securityAnswers = securityAnswers;
    }
}
