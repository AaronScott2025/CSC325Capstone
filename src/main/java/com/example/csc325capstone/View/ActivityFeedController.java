package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.Post;
import com.example.csc325capstone.Model.User;
import com.example.csc325capstone.Model.Location;
import com.example.csc325capstone.View.AppState;
import com.example.csc325capstone.Controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class ActivityFeedController {
    @FXML
    private VBox postContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button newBTN;
    @FXML
    private Button mainBTN;
    @FXML
    private Button friendsBTN;
    @FXML
    private Button logHikeBtn;
    @FXML
    private Button profileBTN;

    private UserController userController;

    private ObservableList<Post> posts = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // list of post
        ObservableList<Post> posts = getPosts();

        // Iterates through list of posts
        for (Post post : posts) {
            try {
                // Set post template
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/item_post.fxml"));
                Parent root = fxmlLoader.load();

                // Set post template controller
                ItemPostController itemPostController = fxmlLoader.getController();
                itemPostController.setPost(post);

                // Set container for the post
                postContainer.getChildren().add(root);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        //verify postContainer is not null
        if (postContainer == null) {
            System.out.println("postContainer is null during initialization");
        } else {
            System.out.println("postContainer initialized successfully");
            refreshFeed();
        }
    }

    public void addPostToFeed(Post post) {
        posts.add(post);
    }

    public void refreshFeed() {
        // Clear the existing posts in the container
        postContainer.getChildren().clear();

        // Iterate through the updated posts list
        for (Post post : posts) {
            try {
                // Load the post template
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/item_post.fxml"));
                Parent root = fxmlLoader.load();

                // Set the post data in the controller for the post template
                ItemPostController itemPostController = fxmlLoader.getController();
                itemPostController.setPost(post);

                // Add the post to the container
                postContainer.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void openNewPostScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/new_post.fxml"));
            Parent root = loader.load();

            // Get the NewPostController instance
            NewPostController newPostController = loader.getController();

            // Pass this ActivityFeedController to the NewPostController
            newPostController.setActivityFeedController(this);

            // Show the New Post screen
            //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage stage = (Stage) newBTN.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newPostScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/new_post.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) newBTN.getScene().getWindow();

            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private ObservableList<Post> getPosts() {
        posts.add(new Post(
                "Tony Hawkz",
                "I wonder if I could ollie over this stream?",
                Objects.requireNonNull(getClass().getResource("/images/posts/hikerpost6.jpg")).toExternalForm()
        ));
        posts.add(new Post(
                "Teddy Duncan",
                "Guess who's enjoying their hike?!",
                Objects.requireNonNull(getClass().getResource("/images/posts/hikerpost7.jpg")).toExternalForm()
        ));
        posts.add(new Post(
                "Carly Shay",
                "Shout to my big brother for an awesome day!",
                Objects.requireNonNull(getClass().getResource("/images/posts/hikerpost5.jpg")).toExternalForm()
        ));
        posts.add(new Post(
                "Tina Belcher",
                "Wow, you know I was actually really worried about this but I did it. I'm sitting on top of a mountain : p #sisterfamilytimebonding",
                Objects.requireNonNull(getClass().getResource("/images/posts/hikerpost1.jpg")).toExternalForm()
        ));
        posts.add(new Post(
                "Fred",
                "I do like to be quiet...sometimes.",
                Objects.requireNonNull(getClass().getResource("/images/posts/hikerpost2.jpg")).toExternalForm()
        ));
        posts.add(new Post(
                "Kai Parker",
                "Mystics Falls is not ready for yet xD",
                Objects.requireNonNull(getClass().getResource("/images/posts/hikerpost3.jpg")).toExternalForm()
        ));

        return posts;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    void mainScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();

            User currentUser = AppState.getInstance().getCurrentUser();
            UserController userController = AppState.getInstance().getUserController();

            if (currentUser != null && userController != null) {
                mainController.initWelcome("Welcome back, " + currentUser.getUserID());
                mainController.setUserController(userController);

                Location cl = new Location(null);
                try {
                    cl.setLocation(cl.getcurrentLocation());
                    mainController.initTextArea(cl);
                } catch (Exception e) {
                    e.printStackTrace();
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
            }

            Stage stage = (Stage) friendsBTN.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/logHike.fxml"));
            Parent root = loader.load();

            LogHikeController logHikeController = loader.getController();

            // Use the UserController from AppState instead of the class field
            UserController userController = AppState.getInstance().getUserController();
            if (userController == null) {
                System.err.println("Error: UserController is null in ActivityFeedController");
                return;
            }
            logHikeController.setUserController(userController);

            Stage stage = (Stage) logHikeBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeProfile(User currentUser) {

    }
}