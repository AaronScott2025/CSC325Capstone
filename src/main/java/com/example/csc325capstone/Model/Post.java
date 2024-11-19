package com.example.csc325capstone.Model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private String imageURL; // URL for the image, optional
    private String description; // Post's description added by user's, required
    private User user;
    private Journey hikerLog; // Hiker log, optional
    private final Date postDate; //Date when the post was made

    // Constructor for Post with description
    public Post(User user, String description) {
        this.description = description;
        this.user = user;
        this.postDate = new Date();
    }

    // Constructor for Post with description and image
    public Post(User user, String description, String imageURL) {
        this.imageURL = imageURL;
        this.description = description;
        this.user = user;
        this.postDate = new Date();
    }

    // Constructor for Post with description and hiker's log
    public Post(User user, String description, Journey hikerLog) {
        this.hikerLog = hikerLog;
        this.description = description;
        this.user = user;
        this.postDate = new Date();
    }

    // Constructor for Post with description, image, and hiker's log
    public Post(User user, String description, String imageURL, Journey hikerLog) {
        this.hikerLog = hikerLog;
        this.imageURL = imageURL;
        this.description = description;
        this.user = user;
        this.postDate = new Date();
    }

    SimpleDateFormat fromatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //Getters and Setters
    public Journey getHikerLog() { return hikerLog; }
    public void setHikerLog(Journey hikerLog) { this.hikerLog = hikerLog; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Date getPostDate() { return postDate; }

    // Returns detail about the post
    public String toString() {
        String postDetails = "Post by " + getUser() + " on " + fromatter.format(getPostDate()) + "\nDescription: " + getDescription();
        if(imageURL != null){
            postDetails += "\nImage URL: " + imageURL;
        }
        if(hikerLog != null) {
            postDetails += "\nHiker Log: " + hikerLog;
        }
        return postDetails;
    }

}
