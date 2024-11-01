package com.example.csc325capstone.Model;


public class Locations extends CurrentLocation {
    private String name; //Name of hike
    private String description; //Description of hike

    public Locations(String location, String name, String description) {
        super(location);
        this.name = name;
        this.description = description;
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

