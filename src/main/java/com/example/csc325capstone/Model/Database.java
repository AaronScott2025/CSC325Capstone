package com.example.csc325capstone.Model;

import com.example.csc325capstone.ViewModel.Main;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Database {

    public String getDocID(Firestore db, String user) throws ExecutionException, InterruptedException {
        CollectionReference users = db.collection ("User");
        Query q = users.whereEqualTo("userID",user);
        ApiFuture<QuerySnapshot> qs = q.get();
        List<QueryDocumentSnapshot> ds = qs.get().getDocuments();
        if(!ds.isEmpty()) {
            DocumentSnapshot d = ds.getFirst();
            return d.getId();
        }
        return null;
    }

    public boolean isUnique(Firestore db, String name) throws ExecutionException, InterruptedException {
        CollectionReference users = db.collection ("User");
        Query q = users.whereEqualTo("userID",name);
        ApiFuture<QuerySnapshot> qs = q.get();
        return qs.get().isEmpty();
    }

    public boolean addUser(User user) {
        DocumentReference docRef = Main.fstore.collection("User").document(UUID.randomUUID().toString());
        docRef.set(user.toMap());
        return true;
    }

    public boolean verifyPassword(Firestore db, String name,String pass) throws ExecutionException, InterruptedException {
        DocumentReference doc = db.collection ("User").document(getDocID(db,name));
        ApiFuture<DocumentSnapshot> ds = doc.get();
        DocumentSnapshot document = ds.get();
        if(document.exists()){
            if(pass.equals(document.getData().get("password").toString())){
                return true;
            }
        }
        return false;
    }
}
