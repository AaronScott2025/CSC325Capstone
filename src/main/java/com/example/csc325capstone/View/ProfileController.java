package com.example.csc325capstone.View;

import com.example.csc325capstone.Controller.UserController;
import com.example.csc325capstone.Model.Journey;
import com.example.csc325capstone.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class ProfileController {

    @FXML
    private Button userIdLabel;

    @FXML
    private Button followersLabel;

    @FXML
    private Button followingLabel;

    @FXML
    private ListView<Journey> journeyListView;

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void initializeProfile() {
        User currentUser = userController.getCurrentUser();
        if (currentUser != null) {
            userIdLabel.setText("User ID: " + currentUser.getUserID());
            followersLabel.setText("Followers: " + userController.getFollowersCount());
            followingLabel.setText("Following: " + userController.getFollowingCount());
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
}