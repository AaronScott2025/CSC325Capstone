package com.example.csc325capstone.Model;


public class Locations extends CurrentLocation {
    private String state; //State
    private String city; //City
    private String name; //Name of hike
    private String description; //Description of hike

    public Locations(String state, String city, String state1, String city1, String name, String description) {
        super(state, city);
        this.state = state1;
        this.city = city1;
        this.name = name;
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

