package com.example.csc325capstone.View;

import com.example.csc325capstone.Controller.UserController;
import com.example.csc325capstone.Model.Hike;
import com.example.csc325capstone.Model.Location;
import com.example.csc325capstone.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class LogHikeController {

    @FXML
    private TextField hikeNameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button discardBTN;

    @FXML
    private Label messageLabel;

    @FXML
    private Button submitBTN;

    @FXML
    private ListView<String> hikeListView;

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
        loadHikes();
    }

    private void loadHikes() {
        if (hikeListView != null && userController != null) {
            hikeListView.getItems().clear();
            List<Hike> hikes = userController.getUserHikes();
            for (Hike hike : hikes) {
                hikeListView.getItems().add(hike.getHikeName() + " - " + hike.getLocation());
            }
        }
    }

    @FXML
    void addHike(ActionEvent event) throws InterruptedException {
        String hikeName = hikeNameField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionField.getText().trim();

        if (hikeName.isEmpty() || location.isEmpty()) {
            messageLabel.setText("Please enter a hike name and location.");
            return;
        }

        Hike newHike = new Hike(hikeName, location, description, new Date());

        if (userController.addHike(newHike)) {
            messageLabel.setText("Hike logged successfully!");
            clearFields();
            loadHikes();
            Thread.sleep(300);
            mainScreen(event);
        } else {
            messageLabel.setText("Failed to log hike. Please try again.");
        }
    }

    @FXML
    void backToProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/userProfileScene.fxml"));
            Parent root = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setUserController(userController);
            profileController.initializeProfile(userController.getCurrentUser());

            Stage stage = (Stage) hikeNameField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            Stage stage = (Stage) discardBTN.getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void clearFields() {
        hikeNameField.clear();
        locationField.clear();
        descriptionField.clear();
    }
}