package com.example.csc325capstone.Model;

import com.google.cloud.firestore.DocumentSnapshot;

import java.util.*;

public class User extends Person {
    private String userID;
    private String password;
    private String record;
    private List<String> followersList;
    private List<String> followingList;
    private List<Journey> journies;
    private List<Hike> hikingLog;
    private String securityAnswer1;
    private String securityAnswer2;

    // Default constructor for Firestore
    public User() {
        super(null, null);
        this.followersList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.journies = new ArrayList<>();
    }

    // Constructor with parameters
    public User(String userID, String password, String record, String securityAnswer1, String securityAnswer2) {
        super(userID, record);
        this.userID = userID;
        this.password = password;
        this.record = record;
        this.followersList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.journies = new ArrayList<>();
        this.hikingLog = new ArrayList<>();
        this.securityAnswer1 = securityAnswer1;
        this.securityAnswer2 = securityAnswer2;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userID", userID);
        map.put("password", password);
        map.put("record", record);
        map.put("followers", followersList);
        map.put("following", followingList);
        map.put("journies", journies);
        map.put("hikeLog", hikeLogToMap());
        map.put("securityAnswer1", securityAnswer1);
        map.put("securityAnswer2", securityAnswer2);
        return map;
    }

    private List<Map<String, Object>> hikeLogToMap() {
        List<Map<String, Object>> hikeMaps = new ArrayList<>();
        for (Hike hike : hikingLog) {
            Map<String, Object> hikeMap = new HashMap<>();
            hikeMap.put("hikeName", hike.getHikeName());
            hikeMap.put("location", hike.getLocation());
            hikeMap.put("description", hike.getDescription());
            hikeMap.put("date", hike.getDate());
            hikeMaps.add(hikeMap);
        }
        return hikeMaps;
    }

    public static User fromDocument(DocumentSnapshot document) {
        if (document == null || !document.exists()) {
            return null;
        }

        String userID = document.getString("userID");
        String password = document.getString("password");
        String record = document.getString("record");
        String securityAnswer1 = document.getString("securityAnswer1");
        String securityAnswer2 = document.getString("securityAnswer2");

        User user = new User(userID, password, record, securityAnswer1, securityAnswer2);

        List<String> followers = (List<String>) document.get("followers");
        if (followers != null) {
            user.followersList.addAll(followers);
        }

        List<String> following = (List<String>) document.get("following");
        if (following != null) {
            user.followingList.addAll(following);
        }

        List<Map<String, Object>> journeyMaps = (List<Map<String, Object>>) document.get("journies");
        if (journeyMaps != null) {
            for (Map<String, Object> journeyMap : journeyMaps) {
                user.journies.add(Journey.fromMap(journeyMap));
            }
        }

        List<Map<String, Object>> hikeMaps = (List<Map<String, Object>>) document.get("hikingLog");
        if (hikeMaps != null) {
            for (Map<String, Object> hikeMap : hikeMaps) {
                Hike hike = new Hike(
                        (String) hikeMap.get("hikeName"),
                        (String) hikeMap.get("location"),
                        (String) hikeMap.get("description"),
                        hikeMap.get("date") instanceof Date ? (Date) hikeMap.get("date") : new Date()
                );
                user.hikingLog.add(hike);
            }
        }

        return user;
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
    public List<Hike> getHikingLog() {
        return hikingLog;
    }

    public void addHike(Hike hike) {
        hikingLog.add(hike);
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