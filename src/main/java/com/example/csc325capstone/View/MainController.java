package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.Hikes;
import com.example.csc325capstone.Model.Location;
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

public class MainController {

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

    private UserController userController;

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
    void profileScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/userProfileScene.fxml"));
            Parent root = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setUserController(userController);
            profileController.initializeProfile();

            Stage stage = (Stage) profileBTN.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    void showFavorites(ActionEvent event) {

    }
    public void initTextArea(Location cl) {
        locations.setText("");
        Hikes[] h = cl.getNearbyLocations(cl.getLocation());
        String[] getter = cl.getLocation().split(",");
        locations.appendText("Local Hikes Near:   " + getter[0] + " , " + getter[1] + "\n");
        for(int i = 0;i < h.length;i++) {
            locations.appendText("\n");
            locations.appendText(h[i].getName() + "\n" + h[i].getCity() + ", " + h[i].getState() + "\n" + h[i].getDescription());
            locations.appendText("\n");
        }

    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void initWelcome(String u) {
        welcomeLbl.setText(u);
    }

}
