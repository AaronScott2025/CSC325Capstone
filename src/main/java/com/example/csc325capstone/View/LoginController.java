package com.example.csc325capstone.View;
import com.example.csc325capstone.Model.CurrentLocation;
import com.example.csc325capstone.Model.FirestoreContext;
import com.example.csc325capstone.ViewModel.Main;
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
import java.io.IOException;
import java.lang.reflect.AccessFlag;
import java.util.concurrent.TimeUnit;

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
    void loginPressed(ActionEvent event) throws InterruptedException {
        String pass = passEncrypt(PassField.getText());
        //Get user pass from DB and Compare
        System.out.println("Testing: Login Button Pressed");
        CurrentLocation cl = new CurrentLocation(null);
        cl.setLocation(cl.getcurrentLocation());
        cl.getNearbyLocations(cl.getLocation());
        try {
            fstore = contxtFirebase.firebase();
            fauth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            errorlbl.setText("Error: Could not connect to backend. Please try again.");
        }
        Stage stage = Main.getPrimaryStage();
        stage.setTitle("TrailQuest");
        FXMLLoader fx = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
        Scene s = new Scene(


    }
/**========================================================================================================================================================*/
    /**
     * Create Account Methods
     */
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
    void CAPressed(ActionEvent event) {
        //Check if username is unique
        String pass = passEncrypt(CreatePass.getText()); //Put into user's data
        //Put into DB
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
}
