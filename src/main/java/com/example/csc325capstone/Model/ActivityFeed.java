package com.example.csc325capstone.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ActivityFeed {
    // Collection of posts from all users
    private List<Post> allPosts;
    // Filtered list for user and user's friends
    private List<Post> posts;
    private User user;

    // General constructor
    public ActivityFeed() {
        this.allPosts = new ArrayList<>();
        this.posts = new ArrayList<>();
    }
    // User specific constructor
    public ActivityFeed(User user) {
        this.allPosts = new ArrayList<>();
        this.user = user;
        setPosts(user); // Initialize filtered post
    }

    // Setter for filtered posts
    private void setPosts(User user) {
        this.posts = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.getUser().equals(user) /* || user.isFriend(post.getUser()) */) {
                // if the user is friends with the author of the post
                this.posts.add(post);
            }
        }
    }

    // Getter for filtered posts
    public List<Post> getPosts() {
        return posts;
    }
    // Getter for all posts
    public List<Post> getAllPosts() {
        return allPosts;
    }

    // Sorts the post by date in descending order, most recent date first
    private void sortPostsByDate(List<Post> sortList) {
        sortList.sort(Comparator.comparing(Post::getPostDate).reversed());
    }

    // Adds a post to allPosts and update filtered posts
    public void addPost(Post post) {
        allPosts.add(post);
        sortPostsByDate(allPosts);
        setPosts(user); // Refresh filter post
    }

}
