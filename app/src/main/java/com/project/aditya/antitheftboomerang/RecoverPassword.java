package com.project.aditya.antitheftboomerang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RecoverPassword extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmail;
    private Button button_submit;
    private FirebaseAuth firebaseAuth;
    private TextView text_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        firebaseAuth = FirebaseAuth.getInstance();

        button_submit = (Button) findViewById(R.id.Submit);
        mEmail = (EditText) findViewById(R.id.mEmail);
        text_login=(TextView) findViewById(R.id.textlogin);

        button_submit.setOnClickListener(this);
        text_login.setOnClickListener(this);

        mEmail.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (mEmail.getText().toString().length() <= 0) {
                    mEmail.setError("Enter your Email");
                }
                if(!isValidEmail(mEmail.getText().toString()))
                {
                    mEmail.setError("Please Enter correct Email");
                }
                else {
                    mEmail.setError(null);
                }
            }
        });
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private void reset(){
        String resetemail=mEmail.getText().toString().trim();

        if (TextUtils.isEmpty(resetemail)) {
            //email is empty
            Toast.makeText(this, "Plese Enter your Email", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(resetemail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RecoverPassword.this, "Recovery Email has been sent", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RecoverPassword.this, "Email Not Registered..Enter Correct Email", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    public void onClick(View v) {
        if(v==button_submit)
        {
            reset();
        }
        if(v==text_login)
        {
            finish();
            startActivity(new Intent(RecoverPassword.this, Login.class));
        }
    }
}
