package com.project.aditya.antitheftboomerang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button signin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView Signup;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;
    private TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
        else
        {
            SharedPreferences.Editor editor = getSharedPreferences("data",0).edit();
            editor.putString("sms_password","");
            editor.commit();
        }

        signin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Signup = (TextView) findViewById(R.id.signup);
        forgot = (TextView) findViewById(R.id.forgot);

        editTextEmail.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (editTextEmail.getText().toString().length() <= 0) {
                    editTextEmail.setError("This field cannot be blank");
                }
                if(!isValidEmail(editTextEmail.getText().toString()))
                {
                    editTextEmail.setError("Please Enter correct Email");
                }
                else {
                    editTextEmail.setError(null);
                }
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (editTextPassword.getText().toString().length() <= 0) {
                    editTextPassword.setError("This field cannot be blank");
                }
                if(editTextPassword.getText().toString().length()<8)
                {
                    editTextPassword.setError("This field must contain a minimum of 8 characters");
                }
                else {
                    editTextPassword.setError(null);
                }
            }
        });


        progressdialog = new ProgressDialog(this);

        signin.setOnClickListener(this);
        Signup.setOnClickListener(this);
        forgot.setOnClickListener(this);

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Plese Enter your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Plese Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressdialog.setMessage("Logging in...");
        progressdialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();

                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity(new Intent(Login.this, HomeActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == signin) {
            // login user
            userLogin();
        }
        if(v == Signup)
        {
            //go back to registration page
            finish();
            startActivity(new Intent(Login.this,MainActivity.class));

        }
        if(v==forgot)
        {
            finish();
            startActivity(new Intent(Login.this,RecoverPassword.class));

        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}