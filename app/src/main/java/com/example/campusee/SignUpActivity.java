package com.example.campusee;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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



public class SignUpActivity extends AppCompatActivity {
    private EditText emailView = null;
    private EditText passwordView = null;
    private RadioGroup radioGroup = null;
    public DB_util db = new DB_util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);

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
    public boolean isValidEmail(String s){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return s.matches(regex);
    }
    public void onSubmit(View v){
        Log.d("initial submit", "INITIAL SUBMIT");
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        Log.d("Checked id: ",""+selectedId);
        Log.d("EMAIL: ",email);
        Log.d("PASSWORD: ",password);
        if (email.isEmpty()){
            emailView.setError("um, Email shouldn't be empty");
            return;
        }else if (password.trim().length() < 6){
            passwordView.setError("um, password should be at least 6 digits long");
            return;
        }else if (!isValidEmail(email)){
            emailView.setError("But this is not an email!");
            return;
        }
        boolean isPub = true;
        if (selectedId == R.id.LogIn_Publisher){
            isPub = true;
        }else{
            isPub = false;
        }
        User user = new User(isPub,email,password);
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
        protected void onPreExecute() {
            //TODO: ADD A SPINNER;
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
            // all the below arraylist only represent one variable which is its first.
            // objects cannot be modified in anonymous inner classes, that's why a list is used.
            final ArrayList<Boolean> complete = new ArrayList<>();
            final ArrayList<Boolean> res = new ArrayList<>();
            complete.add(false);
            res.add(false);

            Query query = col.whereEqualTo("Email",user.email);
            Log.d("EXCITED!!!","VE");
            final ArrayList<DocumentSnapshot> shots = new ArrayList<>();
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots){

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        shots.add(snapshot);
                    }
                    Log.d("LIST SIZE: ",""+shots.size());
                    if (shots.size() > 0){
                        complete.set(0,true);
                    }else{
                        HashMap<String,String> map = new HashMap<>();
                        map.put("Email",user.email);
                        map.put("Password",user.password);
                        collection.add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task){
                                res.set(0,true);
                                complete.set(0,true);

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
            // todo: STOP THE SPINNER.
            if (result){
                Log.d("Success","SuccessRegistration");
                Toast.makeText(getApplication().getBaseContext(),"Success!",Toast.LENGTH_LONG).show();
                Intent intent = null;
                if (user.isPublisher){
                    intent = new Intent(SignUpActivity.this,PublisherDashboardActivity.class);
                }else{
                    intent = new Intent(SignUpActivity.this,NormalUserDashboardActivity.class);
                }
                intent.putExtra("Email",user.email);
                startActivity(intent);
            }else{
                Log.d("Failure","most likely more than 2 users");
                Toast.makeText(getApplication().getBaseContext(),"USER ALREADY EXISTS",Toast.LENGTH_LONG).show();
                emailView.setError("This username already exists/network error.");
            }
        }
    }
}
