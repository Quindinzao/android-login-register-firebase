package com.example.firebaseprojectii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class FormLogin extends AppCompatActivity {

    private TextView registerText;
    private EditText editEmail, editPassword;
    private Button buttonSignIn;
    private ProgressBar progressBar;
    String[] message = {"Preencha todos  os campos!" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        getSupportActionBar().hide();
        initComponents();

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this, FormRegister.class);
                startActivity(intent);
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String pass = editPassword.getText().toString();

                if (email.isEmpty() || pass.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, message[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    userAuthenticLogin(view);
                }
            }
        });
    }

    private void userAuthenticLogin(View view) {
        String email = editEmail.getText().toString();
        String pass = editPassword.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ProfileScreen();
                        }
                    }, 3000);
                } else {
                    String error;
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        error = "Erro ao entrar!";
                    }

                    Snackbar snackbar = Snackbar.make(view, error, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void ProfileScreen() {
         Intent intent = new Intent(FormLogin.this, ProfileScreen.class);
         startActivity(intent);
         finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            ProfileScreen();
        }
    }

    private void initComponents() {
        registerText = findViewById(R.id.registerText);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonSignIn  = findViewById(R.id.buttonSignIn );
        progressBar  = findViewById(R.id.progressBar);
    }
}