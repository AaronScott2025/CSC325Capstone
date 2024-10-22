package com.example.csc325capstone.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private Button CreateAccount;

    @FXML
    private Button FAQ;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PassField;

    @FXML
    private TextField UserField;

    @FXML
    void FAQPressed(ActionEvent event) {
        System.out.println("Testing: FAQ Button Pressed");
    }

    @FXML
    void createPressed(ActionEvent event) {
        System.out.println("Testing: Create Account Button Pressed");
    }

    @FXML
    void loginPressed(ActionEvent event) {
        System.out.println("Testing: Login Button Pressed");

    }

}
