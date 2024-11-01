package com.example.csc325capstone.Model;

public class Journey{
    private Hikes location;
    private String starttime;
    private String endtime;
    private boolean personalbest;
    private double miles;

    public Journey(Hikes location, String starttime, String endtime, boolean personalbest, double miles) {
        this.location = location;
        this.starttime = starttime;
        this.endtime = endtime;
        this.personalbest = personalbest;
        this.miles = miles;
    }

    public Hikes getLocation() {
        return location;
    }

    public void setLocation(Hikes location) {
        this.location = location;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public boolean isPersonalbest() {
        return personalbest;
    }

    public void setPersonalbest(boolean personalbest) {
        this.personalbest = personalbest;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }
}
