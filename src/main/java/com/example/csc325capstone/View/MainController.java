package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.*;
import com.example.csc325capstone.Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainController {

    @FXML
    private Button logHikeBtn;

    @FXML
    private TextField hikeNameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Label logErrorLbl;

    @FXML
    private Label searchForTrailsLabel;

    @FXML
    private Label searchFriendsLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button searchFriendBTN;

    @FXML
    private Button searchForFriendsBTN;

    @FXML
    private Button searchForTrailsBTN;

    @FXML
    private Button followBTN;

    @FXML
    private Button activityBTN;

    @FXML
    private Label errorlbl;

    @FXML
    private Button exitBTN;

    @FXML
    private Button favoritesBTN;

    @FXML
    private Button friendsBTN;

    @FXML
    private Button profileBTN;

    @FXML
    private TextArea locations;

    @FXML
    private Button logBTN;

    @FXML
    private TextField queryCity;

    @FXML
    private TextField queryState;

    @FXML
    private Button search;

    @FXML
    private Label welcomeLbl;

    private Database database;
    private UserController userController;
    private User currentUser;

    public void initialize() {
        User currentUser = AppState.getInstance().getCurrentUser();
        if (currentUser != null) {
            initWelcome("Welcome back, " + currentUser.getUserID());

        }
        // Initialize the text area with the user's current location
        Location cl = new Location(null);
        try {
            cl.setLocation(cl.getcurrentLocation());
            initTextArea(cl);
        } catch (Exception e) {
            e.printStackTrace();
            errorlbl.setText("Error getting current location: " + e.getMessage());
        }
    }

    public void initTextArea(Location cl) {
        if (cl == null || cl.getLocation() == null) {
            errorlbl.setText("Error: Location is null");
            return;
        }

        locations.clear();
        Hikes[] h = cl.getNearbyLocations(cl.getLocation());
        String[] getter = cl.getLocation().split(",");
        if (getter.length < 2) {
            errorlbl.setText("Error: Invalid location format");
            return;
        }

        locations.appendText("Local Hikes Near: " + getter[0] + " , " + getter[1] + "\n\n");
        for (Hikes hike : h) {
            if (hike != null) {
                locations.appendText(hike.getName() + "\n" + hike.getCity() + ", " + hike.getState() + "\n" + hike.getDescription() + "\n\n");
            }
        }
    }


    @FXML
    void searchForFriends(ActionEvent event) {
        // Hide search for trails elements
        searchForTrailsLabel.setVisible(false);
        queryCity.setVisible(false);
        queryState.setVisible(false);
        search.setVisible(false);
        searchForFriendsBTN.setVisible(false);

        // Show search for friends elements
        searchFriendsLabel.setVisible(true);
        usernameTextField.setVisible(true);
        searchFriendBTN.setVisible(true);
        searchForTrailsBTN.setVisible(true);
    }

    @FXML
    void searchFriend(ActionEvent event) {
        String username = usernameTextField.getText().trim();

        if (username.isEmpty()) {
            errorlbl.setText("Please enter a username to search.");
            errorlbl.setVisible(true);
            followBTN.setVisible(false);
            return;
        }

        try {
            if (userController.userExists(username)) {
                errorlbl.setText("User found: " + username);
                errorlbl.setVisible(true);
                followBTN.setVisible(true);
            } else {
                errorlbl.setText("User not found: " + username);
                errorlbl.setVisible(true);
                followBTN.setVisible(false);
            }
        } catch (ExecutionException | InterruptedException e) {
            errorlbl.setText("Error searching for user: " + e.getMessage());
            errorlbl.setVisible(true);
            followBTN.setVisible(false);
            e.printStackTrace();
        }
    }

    @FXML
    void followUser(ActionEvent event) {
        String usernameToFollow = usernameTextField.getText().trim();

        if (usernameToFollow.isEmpty()) {
            errorlbl.setText("Please enter a username to follow.");
            errorlbl.setVisible(true);
            return;
        }

        if (userController == null) {
            errorlbl.setText("Error: UserController is not initialized.");
            errorlbl.setVisible(true);
            return;
        }

        currentUser = AppState.getInstance().getCurrentUser();
        if (currentUser == null) {
            errorlbl.setText("Error: Current user is not logged in.");
            errorlbl.setVisible(true);
            return;
        }

        if (usernameToFollow.equals(currentUser.getUserID())) {
            errorlbl.setText("You cannot follow yourself.");
            errorlbl.setVisible(true);
            return;
        }

        try {
            boolean followSuccess = userController.followUser(usernameToFollow);
            if (followSuccess) {
                errorlbl.setText("Successfully followed " + usernameToFollow);
                errorlbl.setVisible(true);
                followBTN.setDisable(true);
                followBTN.setText("Following");
            } else {
                errorlbl.setText("Failed to follow " + usernameToFollow + ". User might not exist or you're already following them.");
                errorlbl.setVisible(true);
            }
        } catch (Exception e) {
            errorlbl.setText("Error following user: " + e.getMessage());
            errorlbl.setVisible(true);
            e.printStackTrace();
        }
    }

    @FXML
    void searchForTrails(ActionEvent event) {
        // Show search for trails elements
        searchForTrailsLabel.setVisible(true);
        queryCity.setVisible(true);
        queryState.setVisible(true);
        search.setVisible(true);
        searchForFriendsBTN.setVisible(true);

        // Hide search for friends elements
        searchFriendsLabel.setVisible(false);
        usernameTextField.setVisible(false);
        searchFriendBTN.setVisible(false);
        searchForTrailsBTN.setVisible(false);
    }

    @FXML
    void activityScreen(ActionEvent event) {
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/userProfileScene.fxml"));
            Parent root2 = loader2.load();

            ProfileController profileController = loader2.getController();

            User currentUser = AppState.getInstance().getCurrentUser();
            UserController userController = AppState.getInstance().getUserController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/activity_feed.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) activityBTN.getScene().getWindow();
            Scene activityScene = new Scene(root);
            stage.setScene(activityScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorlbl.setText("Error loading activity screen: " + e.getMessage());
        }
    }

    /**
     * exit()
     * Simply exits the program when the exit button is pushed
     * @param event
     */
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void logScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/logHike.fxml"));
            Parent root = loader.load();

            LogHikeController logHikeController = loader.getController();
            logHikeController.setUserController(userController);

            Stage stage = (Stage) logHikeBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorlbl.setText("Error loading hike logging screen: " + e.getMessage());
        }
    }


    @FXML
    void profileScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/userProfileScene.fxml"));
            Parent root = loader.load();

            ProfileController profileController = loader.getController();

            User currentUser = AppState.getInstance().getCurrentUser();
            UserController userController = AppState.getInstance().getUserController();

            if (currentUser != null && userController != null) {
                profileController.setUserController(userController);
                profileController.initializeProfile(currentUser);
            } else {
                System.err.println("Error: Current user or UserController is null");
            }

            Stage stage = (Stage) profileBTN.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorlbl.setText("Error loading profile screen: " + e.getMessage());
        }
    }

    @FXML
    void logHike(ActionEvent event) {
        String hikeName = hikeNameField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();

        if (hikeName.isEmpty() || location.isEmpty() || description.isEmpty()) {
            logErrorLbl.setVisible(true);
            logErrorLbl.setText("Please fill in all fields");
        } else {
            Hike newHike = new Hike(hikeName, location, description, new Date());
            currentUser.addHike(newHike);  // Add hike to current user's log

            // Save the updated hiking log to the database
            try {
                database.saveHikingLog(currentUser);
                logErrorLbl.setVisible(false);  // Hide error message
            } catch (Exception e) {
                logErrorLbl.setVisible(true);
                logErrorLbl.setText("Error saving hike. Please try again.");
            }
        }
    }

    /**
     * queryLocations()
     * Handles the location query. Takes in a City and State, and passes it to Location class, where this method creates
     * a Location object from its parts. this location object is used in "inittextarea" to find locations nearby to the
     * entered location
     * @param event
     * @throws UnsupportedEncodingException
     */
    @FXML
    void queryLocations(ActionEvent event) throws UnsupportedEncodingException {
        if(queryState.getText().equals("") || queryCity.getText().equals("")) {
            errorlbl.setVisible(true);
            errorlbl.setText("Please enter a valid city and/or state");
        } else if(queryState.getText().length() != 2) {
            errorlbl.setVisible(true);
            errorlbl.setText("State must use the 2 letter format. EG: 'NY'");
        } else {
            Location l = new Location(queryState.getText() + "," + queryCity.getText());
            System.out.println(queryState.getText() + "," + queryCity.getText());
            try {
                l.setLocation(l.getLocationsQuery(queryCity.getText(), queryState.getText()));
            } catch (UnsupportedEncodingException e) {
                errorlbl.setVisible(true);
                errorlbl.setText("Please enter a valid city and/or state");
            }
            if(l.getLocation() == null) {
                errorlbl.setVisible(true);
                errorlbl.setText("Please enter a valid city and/or state");
            } else {
                System.out.println(l.getLocation());
                initTextArea(l);
                errorlbl.setVisible(false);
            }
        }
    }


    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    /**
     * initWelcome()
     * Sets the welcome text for the user
     * @param u
     */
    public void initWelcome(String u) {
        welcomeLbl.setText(u);

    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

}
