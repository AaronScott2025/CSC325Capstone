package com.example.csc325capstone.Model;

import com.google.cloud.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class User extends Person {
    private String userID;
    private String password;
    private String record;
    private List<String> followersList;
    private List<String> followingList;
    private List<Journey> journies;    // Changed to List<Journey>
    private String securityAnswer1;
    private String securityAnswer2;
    private List<Hike> hikinglog; //Field for Hiking Log

    // Default constructor for Firestore
    public User() {
        super(null, null);
        this.followersList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.journies = new ArrayList<>();
    }

    // Constructor with parameters
    public User(String userID, String password, String record,
                String securityAnswer1, String securityAnswer2) {
        super(userID, record);
        this.userID = userID;
        this.password = password;
        this.record = record;
        this.followersList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.journies = new ArrayList<>();
        this.securityAnswer1 = securityAnswer1;
        this.securityAnswer2 = securityAnswer2;
        this.hikinglog = new ArrayList<>();
    }

    // Static factory method to create User from Firestore document
    public static User fromDocument(DocumentSnapshot document) {
        if (document == null || !document.exists()) {
            return null;
        }

        User user = new User();
        user.userID = document.getString("userID");
        user.password = document.getString("password");
        user.record = document.getString("record");
        user.securityAnswer1 = document.getString("securityans1");
        user.securityAnswer2 = document.getString("securityans2");

        // Convert Lists from Firestore
        List<String> followers = (List<String>) document.get("followers");
        List<String> following = (List<String>) document.get("following");
        List<Map<String, Object>> journeyMaps = (List<Map<String, Object>>) document.get("journies");
        List<Hike> hikinglog = (List<Hike>) document.get("hikinglog");

        user.followersList = followers != null ? followers : new ArrayList<>();
        user.followingList = following != null ? following : new ArrayList<>();

        // Convert journey maps to Journey objects
        user.journies = new ArrayList<>();
        if (journeyMaps != null) {
            for (Map<String, Object> journeyMap : journeyMaps) {
                Journey journey = Journey.fromMap(journeyMap);
                if (journey != null) {
                    user.journies.add(journey);
                }
            }
        }

        return user;
    }

    // Convert User to Map for Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("userID", userID);
        data.put("password", password);
        data.put("record", record);
        data.put("followers", followersList);
        data.put("following", followingList);
        // Convert Journey objects to maps
        List<Map<String, Object>> journeyMaps = new ArrayList<>();
        for (Journey journey : journies) {
            journeyMaps.add(journey.toMap());
        }

        data.put("journies", journeyMaps);
        data.put("securityans1", securityAnswer1);
        data.put("securityans2", securityAnswer2);
        return data;
    }

    // Modified follow/unfollow methods to work with IDs instead of whole User objects
    public void follow(String userId) {
        if (!followingList.contains(userId)) {
            followingList.add(userId);
        }
    }

    public void unfollow(String userId) {
        followingList.remove(userId);
    }

    public void addFollower(String userId) {
        if (!followersList.contains(userId)) {
            followersList.add(userId);
        }
    }

    public void removeFollower(String userId) {
        followersList.remove(userId);
    }

    public void addJourney(Journey journey) {
        if (!journies.contains(journey)) {
            journies.add(journey);
        }
    }

    public void removeJourney(Journey journey) {
        journies.remove(journey);
    }

    // Getters and setters
    public void addHike(Hike hike){
        this.hikinglog.add(hike);
    }
    public List<Hike> getHikingLog(){
        return this.hikinglog;
    }
    @Override
    public String getUserID() { return userID; }

    @Override
    public void setUserID(String userID) { this.userID = userID; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String getRecord() { return record; }

    @Override
    public void setRecord(String record) { this.record = record; }

    public List<String> getFollowersList() { return followersList; }

    public void setFollowersList(List<String> followersList) { this.followersList = followersList; }

    public List<String> getFollowingList() { return followingList; }

    public void setFollowingList(List<String> followingList) { this.followingList = followingList; }

    public List<Journey> getJournies() { return journies; }

    public void setJournies(List<Journey> journies) { this.journies = journies; }

    public String getSecurityAnswer1() { return securityAnswer1; }

    public void setSecurityAnswer1(String securityAnswer1) {
        this.securityAnswer1 = securityAnswer1;
    }

    public String getSecurityAnswer2() { return securityAnswer2; }

    public void setSecurityAnswer2(String securityAnswer2) {
        this.securityAnswer2 = securityAnswer2;
    }
}