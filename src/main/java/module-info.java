module com.example.csc325capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.api.apicommon;


    opens com.example.csc325capstone to javafx.fxml;
    exports com.example.csc325capstone.View;
    opens com.example.csc325capstone.View to javafx.fxml;
    exports com.example.csc325capstone.Model;
    opens com.example.csc325capstone.Model to javafx.fxml;
    exports com.example.csc325capstone.ViewModel;
    opens com.example.csc325capstone.ViewModel to javafx.fxml;
}