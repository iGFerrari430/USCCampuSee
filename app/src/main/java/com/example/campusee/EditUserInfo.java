package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class EditUserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        emailView = findViewById(R.id.new_email);
        passwordView = findViewById(R.id.new_password);
        oldEmail = getIntent().getStringExtra("Email");

        emailView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                emailView.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailView.setError(null);
            }
        });

        passwordView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                passwordView.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordView.setError(null);
            }
        });
    }
    public DB_util db = new DB_util();
    public EditText emailView = null;
    public EditText passwordView = null;
    public String oldEmail = null;

    public boolean onUpdate(View v){
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        if (email.trim().length() == 0){
            return false;
        }
        if (password.trim().length() <= 5){
            passwordView.setError("Password Incorrect");
            return false;
        }
        queryfirebase(email, password);
        return true;
    }

    public void queryfirebase(final String email, final String password) {
        this.db.userCollection.whereEqualTo("Email",oldEmail).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("更新了","新Email有了");
                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                    Log.d("看这里看这里", ""+snapshot.getId());
                    String id = snapshot.getId();
                    DocumentReference updateFile = EditUserInfo.this.db.userCollection.document(id);
                    updateFile.update("Email", email, "Password", password);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("啥情况,", "怎么挂了post?");
            }
        });
    }
}
