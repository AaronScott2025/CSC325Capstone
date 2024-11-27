package com.example.csc325capstone.View;

import com.example.csc325capstone.Controller.UserController;
import com.example.csc325capstone.Model.Database;
import com.example.csc325capstone.Model.Journey;
import com.example.csc325capstone.Model.Location;
import com.example.csc325capstone.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileController {
    @FXML
    private Button activityBTN;

    @FXML
    private Label userIdLabel;

    @FXML
    private Button followersBTN;

    @FXML
    private Button followingBTN;

    @FXML
    private ListView<Journey> journeyListView;

    @FXML
    private Button mainBTN;

    @FXML
    private Label errorlbl;

    @FXML
    private TextField userIdTextField;

    @FXML
    private Button discardBTN;

    @FXML
    private Button submitBTN;

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void initializeProfile(User  currentUser) {

        if (currentUser != null) {
            userIdLabel.setText("User ID: " + currentUser.getUserID());
            userIdTextField.setText(currentUser.getUserID());
            followersBTN.setText("Followers: " + userController.getFollowersCount());
            followingBTN.setText("Following: " + userController.getFollowingCount());
            List<Journey> journeys = userController.getUserJourneys();
            journeyListView.getItems().addAll(journeys);
        }
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void friendsScreen(ActionEvent event) {

    }

    @FXML
    void logScreen(ActionEvent event) {

    }

    @FXML
    void mainScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();

            User currentUser = AppState.getInstance().getCurrentUser();
            UserController userController = AppState.getInstance().getUserController();

            if (currentUser != null && userController != null) {
                mainController.initWelcome("Welcome back, " + currentUser.getUserID());
                mainController.setUserController(userController);

                Location cl = new Location(null);
                try {
                    cl.setLocation(cl.getcurrentLocation());
                    mainController.initTextArea(cl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Stage stage = (Stage) mainBTN.getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
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

    public void initWelcome(String u) {
        userIdLabel.setText(u);
        userIdTextField.setText(u);
    }

    @FXML
    public void updateProfile(ActionEvent event) {
        String newUsername = userIdTextField.getText().trim();
        User currentUser = userController.getCurrentUser();

        //Shouldn't be possible to get to this, but just in case
        if (currentUser == null) {
            errorlbl.setText("Error: No user logged in.");
            return;
        }

        //userIdTextField can't be empty
        if (newUsername.isEmpty()) {
            errorlbl.setText("Error: Username cannot be empty.");
            return;
        }

        //userIdTextField is the same, no changes submitted
        if (newUsername.equals(currentUser.getUserID())) {
            errorlbl.setText("No changes made to username.");
            return;
        }

        //checks the database to see if the username exists already
        try {
            Database db = new Database();
            boolean isUnique = db.isUnique(userController.getFirestore(), newUsername);
            if (isUnique) {
                // Update the username in the User object if username in userIdTextField is unique
                currentUser.setUserID(newUsername);

                // Update the user profile in the database
                boolean updateSuccess = userController.updateUserProfile(currentUser);

                if (updateSuccess) { //"Success" label changes depending on success
                    userIdLabel.setText("User ID: " + newUsername);
                    errorlbl.setText("Username updated successfully.");
                } else {
                    errorlbl.setText("Error: Failed to update username in the database.");
                }
            } else {
                //in case the user tries submitting a username that exists already in the database
                errorlbl.setText("Error: Username already exists.");
            }
        } catch (ExecutionException | InterruptedException e) {
            errorlbl.setText("Error updating username: " + e.getMessage());
        }
    }

}