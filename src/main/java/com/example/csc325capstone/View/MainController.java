package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.CurrentLocation;
import com.example.csc325capstone.Model.Hikes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private Button activityBTN;

    @FXML
    private Label errorlbl;

    @FXML
    private Label welcomeLbl;

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
    private TextField queryField;

    @FXML
    private Button search;

    @FXML
    void activityScreen(ActionEvent event) {

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
    void queryLocations(ActionEvent event) {

    }

    @FXML
    void showFavorites(ActionEvent event) {

    }
    public void initTextArea(CurrentLocation cl) {
        locations.setText("");
        Hikes[] h = cl.getNearbyLocations(cl.getLocation());
        String[] getter = cl.getLocation().split(",");
        locations.appendText("Local Hikes Near:   " + getter[0] + " , " + getter[1] + "\n");
        for(int i = 0;i < h.length;i++) {
            locations.appendText("\n");
            locations.appendText(h[i].getName() + "\n" + h[i].getState() + "\n" + h[i].getCity() + "\n" + h[i].getDescription());
            locations.appendText("\n");
        }

    }
    public void initWelcome(String u) {
        welcomeLbl.setText(u);
    }

}
