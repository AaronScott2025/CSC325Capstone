package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.*;
import com.example.csc325capstone.Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MainController {

    @FXML
    private Button logHikeBtn;

    @FXML
    private TextField hikeNameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Label logErrorLbl;

    @FXML
    private Button activityBTN;

    @FXML
    private Label errorlbl;

    @FXML
    private Button exitBTN;

    @FXML
    private Button favoritesBTN;

    @FXML
    private Button friendsBTN;

    @FXML
    private Button profileBTN;

    @FXML
    private TextArea locations;

    @FXML
    private Button logBTN;

    @FXML
    private TextField queryCity;

    @FXML
    private TextField queryState;

    @FXML
    private Button search;

    @FXML
    private Label welcomeLbl;

    private Database database;
    private UserController userController;
    private User currentUser;

    public void initialize() {
        User currentUser = AppState.getInstance().getCurrentUser();
        if (currentUser != null) {
            initWelcome("Welcome back, " + currentUser.getUserID());

        }
        // Initialize the text area with the user's current location
        Location cl = new Location(null);
        try {
            cl.setLocation(cl.getcurrentLocation());
            initTextArea(cl);
        } catch (Exception e) {
            e.printStackTrace();
            errorlbl.setText("Error getting current location: " + e.getMessage());
        }
    }

    public void initTextArea(Location cl) {
        if (cl == null || cl.getLocation() == null) {
            errorlbl.setText("Error: Location is null");
            return;
        }

        locations.clear();
        Hikes[] h = cl.getNearbyLocations(cl.getLocation());
        String[] getter = cl.getLocation().split(",");
        if (getter.length < 2) {
            errorlbl.setText("Error: Invalid location format");
            return;
        }

        locations.appendText("Local Hikes Near: " + getter[0] + " , " + getter[1] + "\n\n");
        for (Hikes hike : h) {
            if (hike != null) {
                locations.appendText(hike.getName() + "\n" + hike.getCity() + ", " + hike.getState() + "\n" + hike.getDescription() + "\n\n");
            }
        }
    }

    @FXML
    void activityScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/activity_feed.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) activityBTN.getScene().getWindow();
            Scene activityScene = new Scene(root);
            stage.setScene(activityScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorlbl.setText("Error loading activity screen: " + e.getMessage());
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
    void profileScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/userProfileScene.fxml"));
            Parent root = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setUserController(userController);
            User currentUser = AppState.getInstance().getCurrentUser();
            profileController.initializeProfile(currentUser);

            Stage stage = (Stage) profileBTN.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logHike(ActionEvent event) {
        String hikeName = hikeNameField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();

        if (hikeName.isEmpty() || location.isEmpty() || description.isEmpty()) {
            logErrorLbl.setVisible(true);
            logErrorLbl.setText("Please fill in all fields");
        } else {
            Hike newHike = new Hike(hikeName, location, description, new Date());
            currentUser.addHike(newHike);  // Add hike to current user's log

            // Save the updated hiking log to the database
            try {
                database.saveHikingLog(currentUser);
                logErrorLbl.setVisible(false);  // Hide error message
            } catch (Exception e) {
                logErrorLbl.setVisible(true);
                logErrorLbl.setText("Error saving hike. Please try again.");
            }
        }
    }

    @FXML
    void queryLocations(ActionEvent event) throws UnsupportedEncodingException {
        if(queryState.getText().equals("") || queryCity.getText().equals("")) {
            errorlbl.setVisible(true);
            errorlbl.setText("Please enter a valid city and/or state");
        } else if(queryState.getText().length() != 2) {
            errorlbl.setVisible(true);
            errorlbl.setText("State must use the 2 letter format. EG: 'NY'");
        } else {
            Location l = new Location(queryState.getText() + "," + queryCity.getText());
            System.out.println(queryState.getText() + "," + queryCity.getText());
            try {
                l.setLocation(l.getLocationsQuery(queryCity.getText(), queryState.getText()));
            } catch (UnsupportedEncodingException e) {
                errorlbl.setVisible(true);
                errorlbl.setText("Please enter a valid city and/or state");
            }
            if(l.getLocation() == null) {
                errorlbl.setVisible(true);
                errorlbl.setText("Please enter a valid city and/or state");
            } else {
                System.out.println(l.getLocation());
                initTextArea(l);
                errorlbl.setVisible(false);
            }
        }
    }


    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void initWelcome(String u) {
        welcomeLbl.setText(u);

    }

}
