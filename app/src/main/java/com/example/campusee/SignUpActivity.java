package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class SignUpActivity extends AppCompatActivity {
    private EditText emailView = null;
    private EditText passwordView = null;
    private RadioGroup radioGroup = null;
    private final int PUBLISHER = 2131230849;
    public DB_util db = new DB_util();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);


    }


    public void onSubmit(View v){
        Log.d("initial submit", "INITIAL SUBMIT");
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        Log.d("Checked id: ",""+selectedId);
        Log.d("EMAIL: ",email);
        Log.d("PASSWORD: ",password);

        HashMap<String,String> map = new HashMap<>();
        map.put("vavava","veveve");
        db.db.collection("MACOSX").add(map);
        User user = new User((selectedId == this.PUBLISHER),email,password);
        SignUpTask sTask = new SignUpTask(user,db);
        sTask.execute((Void)null);



    }

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
            Log.d("EXCITED!!!","VE");
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
                //Log.d("WTF","WTF");
            }

            return res.get(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Update the UI thread with the final result
            if (result){
                Log.d("Success","SuccessRegistration");
            }else{
                Log.d("Failure","most likely more than 2 users");
                Toast.makeText(getApplication().getBaseContext(),"USER ALREADY EXISTS",Toast.LENGTH_SHORT);
            }
        }
    }
}
