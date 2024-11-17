package com.example.csc325capstone.Model;


import java.util.ArrayList;

public class User extends Person {

    private String userID; //Username
    private String password; //User's Encrypted Password (SEE LOGIN)
    private String record; //Best travel
    private ArrayList<User> followersList;  //List of followers
    private ArrayList<User> followingList; //List of following
    private Hikes[] favorites; //List of Favorite Locations  |  MAX 5
    private Journey[] journies; //Last 10 hikes
    private boolean favoritevisibility;
    private String securityAnswer1;
    private String securityAnswer2;

    public User(String userID, String password, String record, ArrayList<User> followersList, ArrayList<User> followingList, Hikes[] favorites, Journey[] journies, boolean favoritevisibility, String securityAnswer1, String securityAnswer2) {
        super(userID, record, favorites, favoritevisibility);
        this.userID = userID;
        this.password = password;
        this.record = record;
        this.followersList = followersList;
        this.followingList = followingList;
        this.favorites = favorites;
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

    public ArrayList<User> getFollowersList() {
        return followersList;
    }

    public void setFollowers(ArrayList<User> followersList) { this.followersList = followersList; }

    public ArrayList<User> getFollowingList() { return followingList; }

    public void setFollowing(ArrayList<User> followingList) { this.followingList = followingList; }

    public void follow(User user) {
        if (!followingList.contains(user)) {
            followingList.add(user);
            user.addFollower(this); // Adds this user as a follower of the other user
        }
    }

    // Method to add a follower
    private void addFollower(User user) {
        if (!followersList.contains(user)) {
            followersList.add(user);
        }
    }

    // Method to unfollow a user
    public void unfollow(User user) {
        if (followingList.contains(user)) {
            followingList.remove(user);
            user.removeFollower(this); // Removes this user from the other user's followers
        }
    }

    // Method to remove a follower
    private void removeFollower(User user) {
        followersList.remove(user);
    }

    @Override
    public Hikes[] getFavorites() {
        return favorites;
    }

    @Override
    public void setFavorites(Hikes[] favorites) {
        this.favorites = favorites;
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