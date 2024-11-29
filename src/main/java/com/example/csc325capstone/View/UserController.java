package com.example.csc325capstone.Controller;

import com.example.csc325capstone.Model.Database;
import com.example.csc325capstone.Model.Journey;
import com.example.csc325capstone.Model.User;
import com.example.csc325capstone.ViewModel.Main;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserController {
    @FXML
    private Button activityBTN;

    @FXML
    private Label errorlbl;

    //UserController, click a button to go to your profile.
    //Will be able to click on your followers and following and see the list
    //   Displayed will be their userID
    //Also their journies formatted as a list

    private final Firestore firestore;
    private final Database database;
    private User currentUser;

    public UserController(Firestore firestore) {
        this.firestore = firestore;
        this.database = new Database();
    }

    // Load user profile
    public User loadUserProfile(String userId) throws ExecutionException, InterruptedException {
        String docId = database.getDocID(firestore, userId);
        if (docId == null) return null;

        DocumentReference docRef = firestore.collection("User").document(docId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            currentUser = User.fromDocument(document);
            return currentUser;
        }
        return null;
    }

    // Update user profile
    public boolean updateUserProfile(User user) {
        try {
            String docId = database.getDocID(firestore, user.getUserID());
            if (docId == null) return false;

            DocumentReference docRef = firestore.collection("User").document(docId);

            // Update the document with the new user data
            ApiFuture<WriteResult> future = docRef.set(user.toMap());

            // Wait for the update to complete
            future.get();

            // Update the current user in memory
            this.currentUser = user;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a new journey
    public boolean addJourney(Journey journey) {
        try {
            if (currentUser == null) return false;

            currentUser.addJourney(journey);
            return updateUserProfile(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Follow another user
    public boolean followUser(String targetUserId) {
        try {
            if (currentUser == null) return false;

            // Check if target user exists
            String targetDocId = database.getDocID(firestore, targetUserId);
            if (targetDocId == null) return false;

            // Add to following list
            currentUser.follow(targetUserId);

            // Update both users
            DocumentReference targetDocRef = firestore.collection("User").document(targetDocId);
            ApiFuture<DocumentSnapshot> future = targetDocRef.get();
            DocumentSnapshot targetDoc = future.get();

            if (targetDoc.exists()) {
                User targetUser = User.fromDocument(targetDoc);
                targetUser.addFollower(currentUser.getUserID());

                // Update both users in a batch
                WriteBatch batch = firestore.batch();

                String currentUserDocId = database.getDocID(firestore, currentUser.getUserID());
                DocumentReference currentUserDocRef = firestore.collection("User").document(currentUserDocId);

                batch.set(currentUserDocRef, currentUser.toMap());
                batch.set(targetDocRef, targetUser.toMap());

                batch.commit().get();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Unfollow a user
    public boolean unfollowUser(String targetUserId) {
        try {
            if (currentUser == null) return false;

            String targetDocId = database.getDocID(firestore, targetUserId);
            if (targetDocId == null) return false;

            currentUser.unfollow(targetUserId);

            DocumentReference targetDocRef = firestore.collection("User").document(targetDocId);
            ApiFuture<DocumentSnapshot> future = targetDocRef.get();
            DocumentSnapshot targetDoc = future.get();

            if (targetDoc.exists()) {
                User targetUser = User.fromDocument(targetDoc);
                targetUser.removeFollower(currentUser.getUserID());

                WriteBatch batch = firestore.batch();

                String currentUserDocId = database.getDocID(firestore, currentUser.getUserID());
                DocumentReference currentUserDocRef = firestore.collection("User").document(currentUserDocId);

                batch.set(currentUserDocRef, currentUser.toMap());
                batch.set(targetDocRef, targetUser.toMap());

                batch.commit().get();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get user's journeys
    public List<Journey> getUserJourneys() {
        return currentUser != null ? currentUser.getJournies() : new ArrayList<>();
    }

    // Get user's followers
    public List<String> getUserFollowers() {
        return currentUser != null ? currentUser.getFollowersList() : new ArrayList<>();
    }

    // Get users being followed
    public List<String> getUserFollowing() {
        return currentUser != null ? currentUser.getFollowingList() : new ArrayList<>();
    }

    // Get user's followers count
    public int getFollowersCount() {
        return currentUser != null ? currentUser.getFollowersList().size() : 0;
    }

    // Get user's following count
    public int getFollowingCount() {
        return currentUser != null ? currentUser.getFollowingList().size() : 0;
    }

    // Update user's personal best
    public boolean updatePersonalBest(String newRecord) {
        try {
            if (currentUser == null) return false;

            currentUser.setRecord(newRecord);
            return updateUserProfile(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    void activityScreen(ActionEvent event) {
        try {
            // Load Activity Feed fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/activity_feed.fxml"));
            Parent root = loader.load();

            // Gets current stage
            Stage stage = (Stage) activityBTN.getScene().getWindow();

            // Sets new scene with same dimensions as current
            Scene activityScene = new Scene(root);
            stage.setScene(activityScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorlbl.setText("Error loading activity screen." + e.getMessage());
        }
    }

    // Asynchronous profile loading
    public Task<User> loadUserProfileAsync(String userId) {
        return new Task<>() {
            @Override
            protected User call() throws Exception {
                return loadUserProfile(userId);
            }
        };
    }

    // Get current user
    public User getCurrentUser() {
        return currentUser;
    }

    // Check if user exists
    public boolean userExists(String userId) throws ExecutionException, InterruptedException {
        return database.getDocID(firestore, userId) != null;
    }

    public Firestore getFirestore() {
        return this.firestore;
    }

    // Update security answers
    public boolean updateSecurityAnswers(String answer1, String answer2) {
        try {
            if (currentUser == null) return false;

            currentUser.setSecurityAnswer1(answer1);
            currentUser.setSecurityAnswer2(answer2);
            return updateUserProfile(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // New method to get user by ID (without setting currentUser)
    public User getUserById(String userId) throws ExecutionException, InterruptedException {
        String docId = database.getDocID(firestore, userId);
        if (docId == null) return null;

        DocumentReference docRef = firestore.collection("User").document(docId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return User.fromDocument(document);
        }
        return null;
    }
}
