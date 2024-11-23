package com.example.csc325capstone.Model;

import java.util.Date;

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
