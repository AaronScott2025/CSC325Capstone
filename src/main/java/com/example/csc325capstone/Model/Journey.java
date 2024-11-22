package com.example.csc325capstone.Model;

import java.util.HashMap;
import java.util.Map;

public class Journey {
    private Hikes location;
    private String starttime;
    private String endtime;
    private boolean personalbest;
    private double miles;

    // Default constructor for Firestore
    public Journey() {
    }

    public Journey(Hikes location, String starttime, String endtime, boolean personalbest, double miles) {
        this.location = location;
        this.starttime = starttime;
        this.endtime = endtime;
        this.personalbest = personalbest;
        this.miles = miles;
    }

    // Convert a Map to a Journey object
    public static Journey fromMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }

        Journey journey = new Journey();

//         Convert location string back to Hikes enum
//        String locationStr = (String) map.get("location");
//        if (locationStr != null) {
//            try {
//                journey.location = Hikes.valueOf(locationStr);
//            } catch (IllegalArgumentException e) {
//                journey.location = null; // or set a default value
//            }
//        }

        journey.starttime = (String) map.get("starttime");
        journey.endtime = (String) map.get("endtime");

        // Handle Boolean object from Firestore
        Boolean personalBestObj = (Boolean) map.get("personalbest");
        journey.personalbest = personalBestObj != null ? personalBestObj : false;

        // Handle Number object from Firestore for miles
        Number milesNum = (Number) map.get("miles");
        journey.miles = milesNum != null ? milesNum.doubleValue() : 0.0;

        return journey;
    }

    // Convert a Journey object to a Map
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        // Convert enum to string for storage
        map.put("location", location != null ? location.getName() : null);
        map.put("starttime", starttime);
        map.put("endtime", endtime);
        map.put("personalbest", personalbest);
        map.put("miles", miles);

        return map;
    }

    // Equals method for proper comparison in collections
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Journey journey = (Journey) o;
//
//        if (personalbest != journey.personalbest) return false;
//        if (Double.compare(journey.miles, miles) != 0) return false;
//        if (location != journey.location) return false;
//        if (starttime != null ? !starttime.equals(journey.starttime) : journey.starttime != null) return false;
//        return endtime != null ? endtime.equals(journey.endtime) : journey.endtime == null;
//    }

    // HashCode method for proper use in collections
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = location != null ? location.hashCode() : 0;
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (personalbest ? 1 : 0);
        temp = Double.doubleToLongBits(miles);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
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