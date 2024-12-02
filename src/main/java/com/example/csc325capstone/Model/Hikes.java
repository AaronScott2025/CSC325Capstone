package com.example.csc325capstone.Model;

/**
 * Hikes
 * Dataclass that is used in the Location class to contain information about hiking hot-spots.
 */

public class Hikes {
    private String city;
    private String state;
    private String name; //Name of hike
    private String description; //Description of hike

    public Hikes(String city, String state, String name, String description) {
        this.city = city;
        this.state = state;
        this.name = name;
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

