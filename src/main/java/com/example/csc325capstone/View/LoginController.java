package com.example.csc325capstone.View;
import com.example.csc325capstone.Model.Location;
import com.example.csc325capstone.Model.Database;
import com.example.csc325capstone.Model.FirestoreContext;
import com.example.csc325capstone.Model.User;
import com.example.csc325capstone.ViewModel.Main;
import com.example.csc325capstone.Controller.UserController;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginController {

    private int counter = 3;
    @FXML
    private Stage stage;
    @FXML
    private Button CreateAccount;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PassField;
    @FXML
    private Label errorlbl;
    @FXML
    private TextField UserField;
    public static Firestore fstore;
    public static FirebaseAuth fauth;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
    private int attempts = 3;


    /**
     * Login Methods
     */

    @FXML
    void createPressed(ActionEvent event) throws IOException {
        stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/csc325capstone/create.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Testing: Create Account Button Pressed");
    }

    @FXML
    void loginPressed(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        String pass = passEncrypt(PassField.getText());
        Database db = new Database();
        fstore = contxtFirebase.firebase();
        fstore.collection("User");
        if (db.verifyPassword(fstore, UserField.getText(), pass)) {
            //Get user pass from DB and Compare
            System.out.println("Testing: Login Button Pressed");
            try {
                fstore = contxtFirebase.firebase();
                fauth = FirebaseAuth.getInstance();
            } catch (Exception e) {
                errorlbl.setText("Error: Could not connect to backend. Please try again.");
            }
            Location cl = new Location(null);
            cl.setLocation(cl.getcurrentLocation());
            Stage stage = Main.getPrimaryStage();
            stage.setTitle("TrailQuest");
            FXMLLoader fx = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Parent root = fx.load();
            MainController mainController = fx.getController();
            if (mainController == null) {
                System.err.println("Error: MainController is null");
                return;
            }
            FXMLLoader fx2 = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/userProfileScene.fxml"));
            fx2.load();
            ProfileController profileController = fx2.getController();
            UserController userController = new UserController(fstore);
            mainController.setUserController(userController);

            // Load the user profile
            String userId = UserField.getText();
            User user = userController.loadUserProfile(userId);

            // Set the current user in AppState
            AppState.getInstance().setCurrentUser(user);

            mainController.initTextArea(cl);
            mainController.initWelcome("Welcome back, " + userId);
            profileController.initWelcome(userId);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            errorHandler();
            System.out.println("Error: Incorrect Password");
        }
    }

    public String getUserField() {
        return UserField.getText();
    }

    @FXML
    public void errorHandler() {
        errorlbl.setVisible(true);
        attempts--;
        if(attempts == 0) {
            System.exit(0);
        } else {
            errorlbl.setText("Error: Incorrect Password {" + attempts + "}");
        }
    }
/**========================================================================================================================================================*/
    /**
     * Create Account Methods
     */

    @FXML
    private Button haveButton;

    @FXML
    private Button CreateAnAccount;

    @FXML
    private PasswordField CreatePass;

    @FXML
    private TextField CreateUser;

    @FXML
    private TextField Prompt1;

    @FXML
    private TextField Prompt2;
    @FXML
    private Label createerrorlbl;


    @FXML
    void CAPressed(ActionEvent event) throws IOException {
        Database db = new Database();
        fstore = contxtFirebase.firebase();
        fstore.collection("User");

        try {
            String username = CreateUser.getText();
            if(db.isUnique(fstore, username)) {
                // Encrypt password
                String encryptedPassword = passEncrypt(CreatePass.getText());

                // Create new user with minimal required information
                // The constructor now takes only the essential parameters
                User newUser = new User(
                        username,                // userID
                        encryptedPassword,       // encrypted password
                        "",                     // empty record to start
                        Prompt1.getText(),      // security answer 1
                        Prompt2.getText()       // security answer 2
                );

                // The lists (followers, following, journeys) are automatically initialized
                // as empty ArrayLists in the User constructor

                if(db.addUser(newUser)) {
                    // Optional: Add success handling here
                    createerrorlbl.setVisible(false);
                    // You might want to add a success message or redirect to login
                } else {
                    createerrorlbl.setText("Error: Failed to create account");
                    createerrorlbl.setVisible(true);
                }
            } else {
                createerrorlbl.setText("Error: Username already exists");
                createerrorlbl.setVisible(true);
            }
        } catch (ExecutionException | InterruptedException e) {
            createerrorlbl.setText("Error: Unable to create account");
            createerrorlbl.setVisible(true);
            e.printStackTrace(); // For debugging purposes
        }
    }

    private String passEncrypt(String password) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch: password.toCharArray()) {
            int value = ch - 'a' + 1;
            value = value + 13 % 26;
            char letter = (char)('a' + value - 1);
            encrypted.append(letter);
        }
        return encrypted.toString();
    }

    // The "Have An Account?" button navigates to the Login screen
    public void closeCreate(ActionEvent actionEvent) {
        Stage stage = (Stage) haveButton.getScene().getWindow();
        stage.close();
    }


}
