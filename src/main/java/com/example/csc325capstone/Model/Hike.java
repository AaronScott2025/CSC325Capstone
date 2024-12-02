package com.example.csc325capstone.Model;

import com.google.cloud.Timestamp;

import java.util.Date;
import java.util.Map;

public class Hike {
    private String hikeName;
    private String location;
    private String description;
    private Date date;

    public Hike(String hikeName, String location, String description, Date date) {
        this.hikeName = hikeName;
        this.location = location;
        this.description = description;
        this.date = date;
    }

    public static Hike fromMap(Map<String, Object> map) {
        if (map == null) return null;

        try {
            String hikeName = (String) map.get("hikeName");
            String location = (String) map.get("location");
            String description = (String) map.get("description");
            Date date = map.get("date") instanceof Timestamp
                    ? ((Timestamp) map.get("date")).toDate()
                    : new Date();
            return new Hike(hikeName, location, description, date);
        } catch (ClassCastException | NullPointerException e) {
            System.err.println("Error parsing Hike: " + e.getMessage());
            return null;
        }
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
