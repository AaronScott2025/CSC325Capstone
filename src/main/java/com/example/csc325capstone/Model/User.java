package com.example.csc325capstone.Model;


import java.util.ArrayList;

public class User extends Person {

    private String userID; //Username
    private String password; //User's Encrypted Password (SEE LOGIN)
    private String record; //Best travel
    private ArrayList<User> followersList;  //List of followers
    private ArrayList<User> followingList; //List of following
    private Journey[] journies; //Last 10 hikes
    private String securityAnswer1;
    private String securityAnswer2;

    public User(String userID, String password, String record, ArrayList<User> followersList, ArrayList<User> followingList, Journey[] journies, String securityAnswer1, String securityAnswer2) {
        super(userID, record);
        this.userID = userID;
        this.password = password;
        this.record = record;
        this.followersList = followersList;
        this.followingList = followingList;
        this.journies = journies;
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

    public Journey[] getJournies() {
        return journies;
    }

    public void setJournies(Journey[] journies) {
        this.journies = journies;
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