package com.example.csc325capstone;

public class Locations {
    private String state; //State
    private String name; //Name of hike
    private String[] tags; //List of tags EG: "Nature" "Quiet" "Secluded"

    public Locations(String state, String name, String[] tags) {
        this.state = state;
        this.name = name;
        this.tags = tags;
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
