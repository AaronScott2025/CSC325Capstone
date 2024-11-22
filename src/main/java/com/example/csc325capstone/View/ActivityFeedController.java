package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.Post;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private Button logBTN;

    @FXML
    public void initialize() {
        ObservableList<Post> posts = getPosts();

        for (Post post : posts) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/item_post.fxml"));
                //AnchorPane root = fxmlLoader.load();
                Parent root = fxmlLoader.load();

                ItemPostController itemPostController = fxmlLoader.getController();
                itemPostController.setPost(post);

                postContainer.getChildren().add(root);
            } catch (IOException e){
                e.printStackTrace();

            }
        }
    }

    // List of Post
    private ObservableList<Post> getPosts() {
        ObservableList<Post> posts = FXCollections.observableArrayList();

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
                "Kyrie Irving",
                "Arizona is a MUST! visit",
                Objects.requireNonNull(getClass().getResource("/images/posts/hikerpost3.jpg")).toExternalForm()
        ));

        return posts;
    }


    @FXML
    void mainScreen(ActionEvent event) {
        try {
            // Load Main fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/main.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) mainBTN.getScene().getWindow();

            // Set new scene with the same dimensions as current
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void newPostScreen(ActionEvent event) {
        try {
            // Load new Post fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csc325capstone/new_post.fxml"));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) newBTN.getScene().getWindow();

            // Get the current dimensions of the stage
            //double width = stage.getWidth();
            //double height = stage.getHeight();

            // Set new scene with the same dimensions as current
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
