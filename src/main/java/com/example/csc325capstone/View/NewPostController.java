package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.Location;
import com.example.csc325capstone.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPostController {
    @FXML
    private AnchorPane postAnchor;
    @FXML
    private Pane postPane;
    @FXML
    private Button activityBTN;
    @FXML
    private Button friendsBTN;
    @FXML
    private Button logBTN;
    @FXML
    private Button mainBTN;

    // The "Activity" button navigates to the Activity Feed
    @FXML
    void activityScreen(ActionEvent event) {
        try {
            // Load Activity fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/activity_feed.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) activityBTN.getScene().getWindow();

            // Set new scene with the same dimensions as current
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // The "Main" button navigates to the Main screen
    @FXML
    void mainScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();

            User currentUser = AppState.getInstance().getCurrentUser();
            com.example.csc325capstone.Controller.UserController userController = AppState.getInstance().getUserController();

            if (currentUser != null && userController != null) {
                mainController.initWelcome("Welcome back, " + currentUser.getUserID());
                mainController.setUserController(userController);

                Location cl = new Location(null);
                try {
                    cl.setLocation(cl.getcurrentLocation());
                    mainController.initTextArea(cl);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the error, maybe show it in the UI
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
}
