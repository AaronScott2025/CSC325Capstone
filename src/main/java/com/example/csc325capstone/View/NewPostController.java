package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.*;
import com.google.cloud.firestore.Firestore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class NewPostController {
    @FXML
    private TextField newImageUrlField;
    @FXML
    private TextField newPostDescription;
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

    //private Firestore firestore;

    //private Database database;

    private User currentUser = AppState.getInstance().getCurrentUser();

    private ActivityFeedController activityFeedController;

    FileChooser fileChooser = new FileChooser();

/*---------------------------------------------------------------------------------------------------*/

    // set Activity Feed Controller
    public void setActivityFeedController(ActivityFeedController activityFeedController) {
        this.activityFeedController = activityFeedController;
        System.out.println("Controller set successfully");
    }

    /*public NewPostController(FirestoreContext firestoreContext){
        this.firestore = firestoreContext.firebase();
    }*/

    // The "Post" button create a new post to add to the activity feed
    @FXML
    public void addPost(ActionEvent event) {

        String newDescription = newPostDescription.getText();
        String newImageUrl = newImageUrlField.getText();
        String postAuthor = currentUser.getUserID();

        // Validate the image URL and sets default url
        if (newImageUrl == null || newImageUrl.isEmpty()) {
            System.out.println("No image URL provided. Using default placeholder.");
            newImageUrl = getClass().getResource("/images/gradient.png").toExternalForm();
        }

        // Print the URL
        System.out.println("Image URL: " + newImageUrl);

        try {
            // Create the new post
            Post newPost = new Post(newDescription, newImageUrl, postAuthor);

            if (activityFeedController != null) {
                activityFeedController.addPostToFeed(newPost);
                activityFeedController.refreshFeed(); // Error refreshing the feed :(
                //   specifically: Error creating post: Invalid URL: Invalid URL or resource not found
                //   Note: the url is external not local to the project
                System.out.println("Post added and feed refreshed.");
            } else {
                System.out.println("Controller not set");
            }

        } catch (Exception e) {
            System.out.println("Error creating post: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // The "Upload" button, opens file window, set the text field to the selected file url
    @FXML
    public void getNewImageUrl(javafx.scene.input.MouseEvent mouseEvent) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        // Show the file chooser dialog
        File file = fileChooser.showOpenDialog(new Stage());
        // Create a FileChooser instance
        if (file != null) {
            // Get the file URL and set it in the Url text field
            String fileUrl = file.toURI().toString();
            newImageUrlField.setText(fileUrl);
            //Image image = new Image(fileUrl);

        } else {
            System.out.println("No file selected");
        }
    }

    // getNewImageUrl will call this method to store the image url to firebase
    public void uploadUrlToFirebase() {
    }

/*---------------------------------------------------------------------------------------------------*/

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

    // The "Profile" button navigates to the Profile screen
    @FXML
    void profileScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/userProfileScene.fxml"));
            Parent root = loader.load();

            ProfileController profileController = loader.getController();
            User currentUser = AppState.getInstance().getCurrentUser();
            com.example.csc325capstone.Controller.UserController userController = AppState.getInstance().getUserController();

            if (currentUser != null && userController != null) {
                profileController.setUserController(userController);
                profileController.initializeProfile(currentUser);
            }

            Stage stage = (Stage) friendsBTN.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The "Log" button navigates to the Log screen
    @FXML
    void logScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/logHike.fxml"));
            Parent root = loader.load();

            LogHikeController logHikeController = loader.getController();

            // Use the UserController from AppState instead of the class field
            com.example.csc325capstone.Controller.UserController userController = AppState.getInstance().getUserController();
            if (userController == null) {
                System.err.println("Error: UserController is null in ActivityFeedController");
                return;
            }
            logHikeController.setUserController(userController);

            Stage stage = (Stage) logBTN.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
