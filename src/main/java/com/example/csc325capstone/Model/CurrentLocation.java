package com.example.csc325capstone.Model;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *REFERENCES:
 *https://rapidapi.com/trailapi/api/trailapi
 *https://developer.android.com/reference/org/json/JSONObject
 */

public class CurrentLocation {

    private String state; //State
    private String city; //City

    public CurrentLocation(String state, String city) {
        this.state = state;
        this.city = city;
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
    public void getNearbyLocations(String structure){
        structure = "?lat=34.1&limit=25&lon=-105.2"; //NEEDS TO BE AS FOLLOWS: "?lat=(LATITUDE HERE)&limit=25&lon=(LONGITUDE HERE) | EG: ?lat=34.1&limit=25&lon=-105.2
        String link = "https://trailapi-trailapi.p.rapidapi.com/activity/" + structure + "&radius=25&q-activities_activity_type_name_eq=hiking";
        try {
            HttpRequest request = HttpRequest.newBuilder() //Build API Request
                    .uri(URI.create(link))
                    .header("x-rapidapi-key", "32f30e6ddemsha239e82908d91ebp184018jsn5dc0c2e056fb")
                    .header("x-rapidapi-host", "trailapi-trailapi.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //Response declared
            String responseBody = response.body(); //Response body
            System.out.println(responseBody);

            JSONObject jsonObject = new JSONObject(responseBody); //Pass to json reader (For interaction)

            for (String key : jsonObject.keySet()) { //String traversing json
                try {
                    JSONObject trail = jsonObject.getJSONObject(key); //Full json section
                    String trailName = trail.getString("name"); //Get name of trail
                    String city = trail.getString("city"); //Get city of trail
                    String state = trail.getString("state"); //Get state of trail
                    String description = trail.getString("description") + " " + trail.getString("directions"); //Get Description + Directions of trail

                    //Comment out after testing. Pre-GUI Integration test for API (Ctrl + /) -Aaron
                    System.out.println("=================================");
                    System.out.println("Trail Name: " + trailName);
                    System.out.println("City: " + city);
                    System.out.println("State: " + state);
                    System.out.println("Description: " + description);
                    System.out.println("=================================");

                } catch (JSONException e) {
                    System.out.println("Key " + key + " does not correspond to a trail.");// Key does not correspond to a trail
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
