package com.example.csc325capstone.View;
import com.example.csc325capstone.Model.CurrentLocation;
import com.example.csc325capstone.Model.Database;
import com.example.csc325capstone.Model.FirestoreContext;
import com.example.csc325capstone.Model.User;
import com.example.csc325capstone.ViewModel.Main;
import com.google.cloud.firestore.DocumentReference;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
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
    void loginPressed(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        String pass = passEncrypt(PassField.getText());
        Database db = new Database();
        fstore = contxtFirebase.firebase();
        fstore.collection("User");
        if(db.verifyPassword(fstore,UserField.getText(),pass)) {
            //Get user pass from DB and Compare
            System.out.println("Testing: Login Button Pressed");
            try {
                fstore = contxtFirebase.firebase();
                fauth = FirebaseAuth.getInstance();
            } catch (Exception e) {
                errorlbl.setText("Error: Could not connect to backend. Please try again.");
            }
            CurrentLocation cl = new CurrentLocation(null);
            cl.setLocation(cl.getcurrentLocation());
            Stage stage = Main.getPrimaryStage();
            stage.setTitle("TrailQuest");
            FXMLLoader fx = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Scene s = new Scene(fx.load());
            MainController mainController = fx.getController();
            mainController.initTextArea(cl);
            mainController.initWelcome("Welcome back, " + UserField.getText());
            stage.setScene(s);
        } else {
            System.out.println("Error: Incorrect Password");
        }


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
        Database db = new Database();
        fstore = contxtFirebase.firebase();
        fstore.collection("User");
        try {
            if(db.isUnique(fstore,CreateUser.getText())) {
                String pass = passEncrypt(CreatePass.getText());
                User u = new User(CreateUser.getText(),pass,null,null,null,null,null,false,Prompt1.getText(),Prompt2.getText());
                db.addUser(u);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
}
