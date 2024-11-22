package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.Post;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityFeedController {

    @FXML
    private Button newBTN;
    @FXML
    private Button mainBTN;
    @FXML
    private Button friendsBTN;
    @FXML
    private Button logBTN;

    @FXML
    void mainScreen(ActionEvent event) {
        try {
            // Load Main fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) mainBTN.getScene().getWindow();

            // Set new scene with the same dimensions as current
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void newPostScreen(ActionEvent event) {
        try {
            // Load new Post fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/new_post.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) newBTN.getScene().getWindow();

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
