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
    private Firestore fstore; //Hiking log

    public Database(Firestore fstore){ //hiking log store
        this.fstore = fstore;
    }
    public void saveHikingLog(User user) throws ExecutionException, InterruptedException {
        DocumentReference userRef = fstore.collection("User").document(user.getUserID());
        userRef.update("hikingLog", user.getHikingLog()).get(); // Save hiking log for user
    }

    public List<Hike> getHikingLog(String userID) throws ExecutionException, InterruptedException {
        DocumentReference userRef = fstore.collection("User").document(userID);
        return (List<Hike>) userRef.get().get().get("hikingLog"); // Retrieve hiking log for user
    }

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

    public  boolean addUser(User user) {
        DocumentReference docRef = Main.fstore.collection("User").document(UUID.randomUUID().toString());

        Map<String,Object> data = new HashMap<>();
        data.put("userID",user.getUserID());
        data.put("password",user.getPassword());
        data.put("record",user.getRecord());
        data.put("followers",user.getFollowersList());
        data.put("following",user.getFollowingList());
        data.put("journies",user.getJournies());
        data.put("securityans1",user.getSecurityAnswer1());
        data.put("securityans2",user.getSecurityAnswer2());
        docRef.set(data);
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
