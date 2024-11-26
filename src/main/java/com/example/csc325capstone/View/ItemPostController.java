package com.example.csc325capstone.View;

import com.example.csc325capstone.Model.Post;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemPostController {
    @FXML
    private Pane itemContainer;
    @FXML
    private AnchorPane itemPane;
    @FXML
    private ImageView postImage;
    @FXML
    private Label postAuthor;
    @FXML
    private TextArea postDescription;

    // Set post data image, author, description
    public void setPost(Post post) {
        if (post.getImageURL() != null) {
            postImage.setImage(new Image(post.getImageURL()));
        }
        postAuthor.setText(post.getUser());
        postDescription.setText(post.getDescription());
    }
}
