package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = null;
    private EditText emailView = null;
    private EditText passwordView = null;
    private RadioGroup radioGroup = null;
    private final int PUBLISHER = 2131230849;
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
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        this.mAuth = FirebaseAuth.getInstance();


        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        Log.d("Checked id: ",""+selectedId);
        Log.d("EMAIL: ",email);
        Log.d("PASSWORD: ",password);

        String collectionName = null;
        if (selectedId == this.PUBLISHER){
            collectionName = "Publisher";
        }else{
            collectionName = "NormalUser";
        }

        Query query = db.collection(collectionName).whereEqualTo("Email",email);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<DocumentSnapshot> shots = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                    shots.add(snapshot);
                }

                if (shots.size() > 1){
                    Toast.makeText(SignUpActivity.this,"Failure: Username used.",Toast.LENGTH_LONG).show();
                }else{

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ERROR", "难受了", e);
            }
        });

        try{
            Thread.sleep(3000);
            Log.d("printsomething...","print something...");
        }catch(Exception e){

        }



    }
}
