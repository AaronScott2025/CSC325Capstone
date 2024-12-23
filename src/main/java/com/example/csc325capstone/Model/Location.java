package com.example.csc325capstone.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Current Location Class:
 * State | State of which the user is in
 * City  | City of which the user is in
 * getNearbyLocations() | Uses Latitude and Longitude with trailapi to get nearby trail information
 * getLocation() | Returns the current user location, based on their IP Address. "State,City,Latitude,Longitude"
 * 
 *REFERENCES:
 *https://rapidapi.com/trailapi/api/trailapi | getNearbyLocations()
 *https://developer.android.com/reference/org/json/JSONObject | getNearbyLocations()
 *https://api.ipify.org?format=text | getcurrentLocation()
 *https://www.ipify.org/ | getLocation()
 * https://nominatim.openstreetmap.org | getLocationsQuery()
 */

public class Location {

    private String location; //EG: New York,Farmingdale,40.4,73.2

    public Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getcurrentLocation()
     * Takes in the users IP address through api.ipify API, then passes that through to a second call through ip-api.com, which returns
     * a json. The json is passed through JSONObject, and parsed to find and return:
     * lattitude
     * longitude
     * City
     * State
     * Into one line, and return the result. If the IP cannot be resolved for any reason, the default location is Los Angeles
     * SEE API REFERENCES AT THE TOP
     * @return
     */
    public String getcurrentLocation() {
        try {
            //Connection 1
            URL url = new URL("https://api.ipify.org?format=text"); //Get user IP
            HttpURLConnection con = (HttpURLConnection) url.openConnection(); //Connects
            con.setRequestMethod("GET"); //Connection Type
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String ip = in.readLine(); //Read line

            //Connection 2
            URL url2 = new URL("http://ip-api.com/json/" + ip); //Get Location (Based on IP)
            HttpURLConnection con2 = (HttpURLConnection) url2.openConnection(); //Connects
            con2.setRequestMethod("GET"); //Connection Type
            BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            String inputline; //For looping
            StringBuilder sb = new StringBuilder(); //To build JSON
            while ((inputline = in2.readLine()) != null) { //Get JSON contents.
                sb.append(inputline);
            }

            //Close connections (No need to keep them open past this point)
            in.close();
            in2.close();

            //JSONObject Handling
            JSONObject obj = new JSONObject(sb.toString()); //Turns String builder to string
            double latitude = obj.getDouble("lat"); //Get Latitude
            double longitude = obj.getDouble("lon"); //Get Longitude
            String region = obj.getString("region"); //Get Region
            String city = obj.getString("city"); //Get City
            System.out.println( region + "," + city + "," + latitude + "," + longitude);
            return region + "," + city + "," + latitude + "," + longitude; //EG: New York,Farmingdale,40.4,73.2

        } catch (Exception e) { //Error occured somewhere
            System.out.println("error");
            return "California,Los Angeles,34.0,118.1"; //DEFAULT TO LOS ANGELES CALIFORNIA ON ERROR
        }
    }

    /**
     * getLocationsQuery()
     * Sends a request to nominatim API, and retrieves a JSON object, which is then parsed for coordinates, and then is
     * returned in the proper locations format (EG: New York,Farmingdale,40.4,73.2)
     * @param city
     * @param state
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getLocationsQuery(String city, String state) throws UnsupportedEncodingException {
        String encodeC = URLEncoder.encode(city,"UTF-8");
        String encodeS = URLEncoder.encode(state,"UTF-8");
        String urlString = String.format("https://nominatim.openstreetmap.org/search?city=%s&state=%s&format=json", encodeC, encodeS);
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString()); //Returns as list of arrays
            if (jsonArray.length() > 0) { //Make sure actual coords got returned
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                double lat = jsonObject.getDouble("lat");
                double lon = jsonObject.getDouble("lon");
                System.out.println("Latitude: " + lat);
                System.out.println("Longitude: " + lon);
                return state + "," + city + "," + lat + "," + lon;
            } else {
                System.out.println("No results found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getNearbyLocations()
     * Utilizes the trailapi from rapidapi. Takes the location string, and splits into an array. The array is parsed to get the
     * lattitude and longitude, and this is formatted and passed into the trailapi to return a JSON. The JSON is then
     * parsed to retrieve the Hike name, city, state, and a short description. If the JSON section does not have all of the
     * necessary parts, error is handled, and skips over it.
     * @param location
     * @return
     */
    public Hikes[] getNearbyLocations(String location){
        Hikes[] locations = new Hikes[25];
        int i = 0;
        String[] getter = location.split(",");
        double lat = Double.parseDouble(getter[2]);
        double lon = Double.parseDouble(getter[3]);
        lat = Math.round(lat * 1);
        lon = Math.round(lon * 1);
        System.out.println( lat + "," + lon);
        String format = "?lat=" + lat + "&limit=25&lon=" + lon; //NEEDS TO BE AS FOLLOWS: "?lat=(LATITUDE HERE)&limit=25&lon=(LONGITUDE HERE) | EG: ?lat=34.1&limit=25&lon=-105.2
        String link = "https://trailapi-trailapi.p.rapidapi.com/activity/" + format + "&radius=25&q-activities_activity_type_name_eq=hiking";
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

                    Hikes l = new Hikes(trailName, city, state, description);
                    locations[i] = l;
                    i++;

                } catch (JSONException e) {
                    System.out.println("Key " + key + " does not correspond to a trail.");// Key does not correspond to a trail
                }
            }

        } catch (Exception e) { //Error occured somewhere
            e.printStackTrace();
        }
        return locations;
    }
}
