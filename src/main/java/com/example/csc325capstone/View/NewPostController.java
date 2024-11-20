package com.example.csc325capstone.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPostController {
    @FXML
    private Button activityBTN;
    @FXML
    private Button friendsBTN;
    @FXML
    private Button logBTN;
    @FXML
    private Button mainBTN;

    @FXML
    void activityScreen(ActionEvent event) {
        try {
            // Load Activity fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/activity_feed.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) activityBTN.getScene().getWindow();

            // Get the current dimensions of the stage
            //double width = stage.getWidth();
            //double height = stage.getHeight();

            // Set new scene with the same dimensions as current
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void mainScreen(ActionEvent event) {
        try {
            // Load Main fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) mainBTN.getScene().getWindow();

            // Get the current dimensions of the stage
            //double width = stage.getWidth();
            //double height = stage.getHeight();

            // Set new scene with the same dimensions as current
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
