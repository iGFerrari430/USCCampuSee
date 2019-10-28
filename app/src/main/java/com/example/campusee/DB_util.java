package com.example.campusee;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class DB_util {
    public FirebaseAuth mAuth;
    public FirebaseFirestore db = null;
    public CollectionReference publisherCollection = null;
    public CollectionReference userCollection = null;
    public DB_util() {
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        this.mAuth = FirebaseAuth.getInstance();
        this.publisherCollection = db.collection("Publisher");
        this.userCollection = db.collection("NormalUser");
    }


}