package com.example.csc325capstone.ViewModel;

import com.example.csc325capstone.Model.FirestoreContext;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Main extends Application {
    private static Stage primaryStage;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
    public static Firestore fstore;
    public static FirebaseAuth fauth;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        showSplashtoLogin();

    }

    private void showSplashtoLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/csc325capstone/splash_screen.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
            Parent newRoot = FXMLLoader.load(getClass().getResource("/com/example/csc325capstone/login.fxml"));
            Scene currentScene = primaryStage.getScene();
            Parent currentRoot = currentScene.getRoot();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), currentRoot);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                Stage login = new Stage();
                Scene newScene = new Scene(newRoot);
                login.setScene(newScene);
                login.initStyle(StageStyle.DECORATED);
                primaryStage.close();
                login.show();
                primaryStage = login;

            });

            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}