package com.project.aditya.antitheftboomerang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.aditya.antitheftboomerang.Data.UserDetails;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    final static String DB_URL="https://boomerang-19e4f.firebaseio.com/";
    private FirebaseAuth firebaseAuth;
    private static final String TAG = MainActivity.class.getSimpleName();
    DatabaseReference fire = null;
    FirebaseDatabase database;

    /*private*/ EditText name;
    /*private*/ EditText Contact;
    /*private*/ EditText EContact;
    /*private*/ Button submit2;

    @Override
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_profile);
        //Initialize
        initializeFirebase();

        name = (EditText)findViewById(R.id.editTextName);
        Contact = (EditText)findViewById(R.id.editTextContact);
        EContact = (EditText)findViewById(R.id.editTextEContact);
        submit2 = (Button)findViewById(R.id.buttonSubmitProfile);
        submit2.setOnClickListener(this);

        name.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (name.getText().toString().length() <= 0) {
                    name.setError("Please Enter your name");
                }
                else {
                    name.setError(null);
                }
            }
        });
        Contact.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (Contact.getText().toString().length() < 10) {
                    Contact.setError("Invalid Contact Number");
                }
                else {
                    Contact.setError(null);
                }
            }
        });
        EContact.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (EContact.getText().toString().length() < 10) {
                    EContact.setError("Invalid Contact Number");
                }
                else {
                    EContact.setError(null);
                }
            }
        });
        if (TextUtils.isEmpty(name.getText().toString())) {
            //email is empty
            Toast.makeText(this, "Plese Enter your Name", Toast.LENGTH_SHORT).show();
            //return;
        }
        if (TextUtils.isEmpty(Contact.getText().toString())) {
            Toast.makeText(this, "Please Enter your Contact Number", Toast.LENGTH_SHORT).show();
            //return;
        }
        if (TextUtils.isEmpty(EContact.getText().toString())) {
            Toast.makeText(this, "Please Enter your Emergency Contact Number", Toast.LENGTH_SHORT).show();
            //return;
        }
        firebaseAuth = FirebaseAuth.getInstance();

        //Get Firebase Instance
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        fire = database.getReference("User");
        Toast.makeText(getApplicationContext(), "hereerer", Toast.LENGTH_SHORT).show();

    }

    //Initialize Firebase
    private void initializeFirebase(){
        Firebase.setAndroidContext(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
        final FirebaseUser f = firebaseAuth.getCurrentUser();
        UserDetails user = new UserDetails();
        user.setName(name.getText().toString());
        user.setContact(Contact.getText().toString());
        user.setEContact(EContact.getText().toString());
        user.setEmail(f.getEmail());
        fire.push().setValue(user);
                /*name.setText("");
                Contact.setText("");
                EContact.setText("");
                */
        Toast.makeText(getApplicationContext(),"here 2",Toast.LENGTH_SHORT).show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name.getText().toString())
                .build();

        f.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(ProfileActivity.this, "Displayname updated", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"User Profile Updated");
                        }
                    }
                });
        finish();
        startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
    }
}
