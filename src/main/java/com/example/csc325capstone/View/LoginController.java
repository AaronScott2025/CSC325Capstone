package com.example.csc325capstone.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Stage stage;
    @FXML
    private Button CreateAccount;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PassField;

    @FXML
    private TextField UserField;


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
    void loginPressed(ActionEvent event) {
        String pass = passEncrypt(PassField.getText());
        //Get user pass from DB and Compare
        System.out.println("Testing: Login Button Pressed");

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
        StringBuilder encryptedPassword = new StringBuilder();
        for (char ch: password.toCharArray()) {
            int value = ch - 'a' + 1;
            value = value + 13 % 26;
            char letter = (char)('a' + value - 1);
            encryptedPassword.append(letter);
        }
        String encryptedPasswordString = encryptedPassword.toString();
        return encryptedPasswordString;
    }
}
