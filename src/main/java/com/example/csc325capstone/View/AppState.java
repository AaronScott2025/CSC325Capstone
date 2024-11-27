package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.User;
import com.example.csc325capstone.Controller.UserController;

public class AppState {
    private static AppState instance;
    private User currentUser;
    private UserController userController;

    private AppState() {}

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }

    public UserController getUserController() {
        return userController;
    }
}