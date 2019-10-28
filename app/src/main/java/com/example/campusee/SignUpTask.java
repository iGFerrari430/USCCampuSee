package com.example.campusee;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpTask extends AsyncTask<Void,Void,Boolean> {

    public User user = null;
    public DB_util db = null;
    public Boolean complete = false;
    public SignUpTask(User user, DB_util db){
        this.user = user;
        this.db = db;
    }
    @Override
    protected Boolean doInBackground(Void... users) {
        CollectionReference col = null;
        if (user.isPublisher){
            col = db.publisherCollection;
        }else{
            col = db.userCollection;
        }

        final CollectionReference collection = col;
        final ArrayList<Boolean> complete = new ArrayList<>();
        final ArrayList<Boolean> res = new ArrayList<>();
        complete.add(false);
        res.add(false);

        Query query = col.whereEqualTo("Email",user.email);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots){
                ArrayList<DocumentSnapshot> shots = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                    shots.add(snapshot);
                }

                if (shots.size() > 0){
                    complete.set(0,true);
                }else{
                    HashMap<String,String> map = new HashMap<>();
                    map.put("Email",user.email);
                    map.put("Password",user.password);
                    collection.add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task){
                            complete.set(0,true);
                            res.set(0,true);
                        }
                    });
                }


            }
        });

        while (!complete.get(0)){

        }

        return res.get(0);
    }
}