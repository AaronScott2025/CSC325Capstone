package com.example.csc325capstone.Model;

import com.example.csc325capstone.ViewModel.Main;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Database
 * All methods used in the program to interact with the database.
 */
public class Database {
    private Firestore fstore; //Hiking log

    public Database(){ //hiking log store
        this.fstore = fstore;
    }
    public void saveHikingLog(User user) throws ExecutionException, InterruptedException {
        DocumentReference userRef = fstore.collection("User").document(user.getUserID());
        userRef.update("hikingLog", user.getHikingLog()).get(); // Save hiking log for user
    }

    public List<Hike> getHikingLog(String userID) throws ExecutionException, InterruptedException {
        DocumentReference userRef = fstore.collection("User").document(userID);
        return (List<Hike>) userRef.get().get().get("hikingLog"); // Retrieve hiking log for user
    }

    /**
     * getDocID()
     * Gets the random assortment of letters given by Firebase, by querying for a username (primary key). If not found, returns
     * null. Otherwise returns that sequence of letters.
     * @param db
     * @param user
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String getDocID(Firestore db, String user) throws ExecutionException, InterruptedException {
        CollectionReference users = db.collection ("User");
        Query q = users.whereEqualTo("userID",user);
        ApiFuture<QuerySnapshot> qs = q.get();
        List<QueryDocumentSnapshot> ds = qs.get().getDocuments();
        if(!ds.isEmpty()) {
            DocumentSnapshot d = ds.getFirst();
            return d.getId();
        }
        return null;
    }

    /**
     * isUnique()
     * checks database for username. If the username is not found, then the user is allowed to be created. Returns true or false given
     * the circumstance.
     * @param db
     * @param name
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean isUnique(Firestore db, String name) throws ExecutionException, InterruptedException {
        CollectionReference users = db.collection ("User");
        Query q = users.whereEqualTo("userID",name);
        ApiFuture<QuerySnapshot> qs = q.get();
        return qs.get().isEmpty();
    }

    /**
     * addUser()
     * puts a user into the database. Uniqueness of username is assumed here, and is determined in parent methods.
     * @param user
     * @return
     */
    public  boolean addUser(User user) {
        DocumentReference docRef = Main.fstore.collection("User").document(UUID.randomUUID().toString());

        Map<String,Object> data = new HashMap<>();
        data.put("userID",user.getUserID());
        data.put("password",user.getPassword());
        data.put("record",user.getRecord());
        data.put("followers",user.getFollowersList());
        data.put("following",user.getFollowingList());
        data.put("journies",user.getJournies());
        data.put("securityans1",user.getSecurityAnswer1());
        data.put("securityans2",user.getSecurityAnswer2());
        docRef.set(data);
        return true;
    }

    /**
     * verifyPassword()
     * Takes the entered username and password, and verifies that the password matches the DB password. Otherwise, returns false.
     * @param db
     * @param name
     * @param pass
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean verifyPassword(Firestore db, String name,String pass) throws ExecutionException, InterruptedException {
        DocumentReference doc = db.collection ("User").document(getDocID(db,name));
        ApiFuture<DocumentSnapshot> ds = doc.get();
        DocumentSnapshot document = ds.get();
        if(document.exists()){
            if(pass.equals(document.getData().get("password").toString())){
                return true;
            }
        }
        return false;
    }

    // Saves post
    public void addPost(Post post) {
        CollectionReference postsCollection = Main.fstore.collection("Post");

        // generate unique ID for new post
        String postID = UUID.randomUUID().toString();

        // HashMap to represent post's fields
        Map<String,Object> postMap = new HashMap<>();
        postMap.put("postID",postID);
        postMap.put("postAuthor", post.getUser());
        postMap.put("postDescription",post.getDescription());
        postMap.put("postImage",post.getImageURL());
        postMap.put("postDate",post.getPostDate());
        // Save to Firestore
        DocumentReference docRef = postsCollection.document(postID);
        ApiFuture<WriteResult> writeResult = docRef.set(postMap);

        // Error handling
        try{
            WriteResult result = writeResult.get();
            System.out.println("Post added successfully");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Post could not be added");
        }
    }


}
