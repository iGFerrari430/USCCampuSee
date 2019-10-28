package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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
    DB_util db = new DB_util();
    public RadioGroup radioGroup = null;
    public EditText emailView = null;
    public EditText passwordView = null;
    public int PUBLISHER = 2131230849;
    public void onLogin(View v){
        Log.d("initial submit", "INITIAL SUBMIT");
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        Log.d("Checked id: ",""+selectedId);
        Log.d("EMAIL: ",email);
        Log.d("PASSWORD: ",password);
        User user = new User((selectedId == this.PUBLISHER),email,password);

        LogInTask loginTask = new LogInTask(user,db);
        loginTask.execute((Void)null);
    }

    public class LogInTask extends AsyncTask<Void,Void,Integer> {
        public User user = null;
        public DB_util db = null;
        public Integer Login_Success = 0;
        public Integer Login_NO_SUCH_USER = 1;
        public Integer Login_Password_Wrong = 2;
        public Integer Login_Unforseen = 3;
        public Boolean complete = false;
        public LogInTask(User user, DB_util db){
            this.user = user;
            this.db = db;
        }

        @Override
        protected Integer doInBackground(Void... users) {
            CollectionReference col = null;
            if (user.isPublisher) {
                col = db.publisherCollection;
            } else {
                col = db.userCollection;
            }

            final CollectionReference collection = col;

            final ArrayList<Boolean> complete = new ArrayList<>();
            final ArrayList<Integer> res = new ArrayList<>();
            complete.add(false);
            res.add(Login_Unforseen);

            Query query = col.whereEqualTo("Email", user.email);

            final ArrayList<DB_User> shots = new ArrayList<>();
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                            shots.add(snapshot.toObject(DB_User.class));
                        }
                    }
                    Log.d("see see shots:",shots.toString());
                    if (shots.size() == 0){
                        res.set(0,Login_NO_SUCH_USER);
                        complete.set(0,true);

                    }else{
                        String actualPW = user.password;
                        String ExpectedPW = shots.get(0).Password;
                        Log.d("Checking...::",actualPW+" "+ExpectedPW);

                        if (!actualPW.equals(ExpectedPW)){

                            res.set(0,Login_Password_Wrong);
                            complete.set(0,true);

                        }else{
                            res.set(0,Login_Success);
                            complete.set(0,true);

                        }
                    }
                }
            });

            while (!complete.get(0)){

            }

            Log.d("About to return this: ",res.get(0)+"");
            return res.get(0);


        }

        protected void onPostExecute(Integer result) {
            // TODO Update the UI thread with the final result
            if (result == Login_NO_SUCH_USER){
                Log.d("Failure","No such user");
                Toast.makeText(getApplication().getBaseContext(),"No such user",Toast.LENGTH_LONG).show();
                emailView.setError("No such user");
            }else if (result == Login_Password_Wrong){
                Log.d("Failure","password WRONG!");
                Toast.makeText(getApplication().getBaseContext(),"Password Incorrect",Toast.LENGTH_LONG).show();
                passwordView.setError("Password Incorrect");
            }else if (result == Login_Success){
                Toast.makeText(getApplication().getBaseContext(),"LOGIN SUCCESS!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplication().getBaseContext(),"FATAL: did not connect to DB.",Toast.LENGTH_LONG).show();
            }
        }
    }
}