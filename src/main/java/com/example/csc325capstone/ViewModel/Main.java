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

/**
 * Main class
 * Start of the application. Shows a splash screen for 3 seconds as it fades, and the Login Screen is shown after
 */

public class Main extends Application {
    private static Stage primaryStage;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
    public static Firestore fstore;
    public static FirebaseAuth fauth;

    /**
     * Start of program. Simply calls method: showSplashtoLogin();
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        showSplashtoLogin();

    }

    /**
     * showSplashtoLogin()
     * Firebase is loaded. Next, the splash screen and login screen are loaded. The primaryStage shows the splash screen,
     * which uses "UNDECORATED" to get rid of the bar at the top. Fade transition is initialzed here, 3 second duration, and
     * when finished loads the login screen, which utilizes the class: LoginController for its functionality
     */
    private void showSplashtoLogin() {
        try {
            fstore = contxtFirebase.firebase();
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

    /**
     * Returns the primary stage (lets it be called in Login Controller)
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}