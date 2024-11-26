package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.*;
import com.google.cloud.firestore.Firestore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MainController {
    @FXML

    private Button logHikeBtn;  //log a hike

    @FXML
    private TextField hikeNameField;  //enter the name of the hike

    @FXML
    private TextField locationField;  //enter the location

    @FXML
    private TextArea descriptionField;  //enter a description

    @FXML
    private Label logErrorLbl;  //for showing error messages

    private AnchorPane bottomAnchor;

    @FXML
    private AnchorPane topAnchor;

    @FXML
    private SplitPane splitPane;

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

    private User currentUser;
    private Database database;

    public MainController() {

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

    public MainController(User user, Firestore firestore) {
        this.currentUser = user;
        this.database = new Database(firestore);
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
        if (queryState.getText().equals("") || queryCity.getText().equals("")) {
            errorlbl.setVisible(true);
            errorlbl.setText("Please enter a valid city and/or state");
        } else if (queryState.getText().length() != 2) {
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
            if (l.getLocation() == null) {
                errorlbl.setVisible(true);
                errorlbl.setText("Please enter a valid city and/or state");
            } else {
                System.out.println(l.getLocation());
                initTextArea(l);
                errorlbl.setVisible(false);
            }
        }
    }

    @FXML
    void showFavorites(ActionEvent event) {

    }

    public void initTextArea(Location cl) {
        locations.setText("");
        Hikes[] h = cl.getNearbyLocations(cl.getLocation());
        String[] getter = cl.getLocation().split(",");
        locations.appendText("Local Hikes Near:   " + getter[0] + " , " + getter[1] + "\n");
        for (int i = 0; i < h.length; i++) {
            locations.appendText("\n");
            locations.appendText(h[i].getName() + "\n" + h[i].getState() + "\n" + h[i].getCity() + "\n" + h[i].getDescription());
            locations.appendText("\n");
        }

    }

    public void initWelcome(String u) {
        welcomeLbl.setText(u);
    }
}


