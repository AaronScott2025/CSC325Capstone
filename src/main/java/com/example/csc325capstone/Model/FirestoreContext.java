package com.example.csc325capstone.Model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class FirestoreContext {

    private static Firestore fs;

    public Firestore firebase() {
        try {
            InputStream serviceAccount = FirestoreContext.class.getResourceAsStream("/key.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            List<FirebaseApp> apps = FirebaseApp.getApps();
            if (apps.isEmpty()) {
                FirebaseApp.initializeApp(options);
            } else {
                FirebaseApp existing = FirebaseApp.getInstance();
            }
            fs = FirestoreClient.getFirestore();
            System.out.println("Firebase is initialized");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fs;
    }

}
