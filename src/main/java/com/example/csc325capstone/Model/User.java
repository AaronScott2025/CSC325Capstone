package com.example.csc325capstone.Model;


import com.example.csc325capstone.Model.Journey;
import com.example.csc325capstone.Model.Locations;
import com.example.csc325capstone.Model.Person;
import java.util.ArrayList;

public class User extends Person {

    private String userID; //Username
    private String password; //User's Encrypted Password (SEE LOGIN)
    private String record; //Best travel
    private ArrayList<Person> friendsList; //List of Friends
    private Locations[] favorites; //List of Favorite Locations  |  MAX 5
    private String located; //Current Location (IP BASED)
    private Journey[] journies; //Last 10 hikes
    private boolean favoritevisibility;
    private String securityAnswer1;
    private String securityAnswer2;

    public User(String userID, String password, String record, ArrayList<Person> friendsList, Locations[] favorites, String located, Journey[] journies, boolean favoritevisibility, String securityAnswer1, String securityAnswer2) {
        super(userID, record, favorites, favoritevisibility);
        this.userID = userID;
        this.password = password;
        this.record = record;
        this.friendsList = friendsList;
        this.favorites = favorites;
        this.located = located;
        this.journies = journies;
        this.favoritevisibility = favoritevisibility;
        this.securityAnswer1 = securityAnswer1;
        this.securityAnswer2 = securityAnswer2;
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

    public ArrayList<Person> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(ArrayList<Person> friendsList) {
        this.friendsList = friendsList;
    }

    @Override
    public Locations[] getFavorites() {
        return favorites;
    }

    @Override
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

    public String getSecurityAnswer1() {
        return securityAnswer1;
    }

    public void setSecurityAnswer1(String securityAnswer1) {
        this.securityAnswer1 = securityAnswer1;
    }

    public String getSecurityAnswer2() {
        return securityAnswer2;
    }

    public void setSecurityAnswer2(String securityAnswer2) {
        this.securityAnswer2 = securityAnswer2;
    }
}