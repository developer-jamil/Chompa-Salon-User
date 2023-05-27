package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {
    private EditText emailEditText;
    private ProgressBar progressBar;

    //Exception
    private static final String TAG = "ForgotPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button resetButton = findViewById(R.id.resetButtonId);
        emailEditText = findViewById(R.id.emailEditTextId);
        progressBar = findViewById(R.id.progressBarId);

        this.setTitle(R.string.recover_pass);

        resetButton.setOnClickListener(view -> {

            String email = emailEditText.getText().toString();

            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Enter your email");
                emailEditText.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Enter a valid email");
                emailEditText.requestFocus();
            }else{
                progressBar.setVisibility(View.VISIBLE);
                resetPassword(email);
            }
        });


    }

    //reset password
    private void resetPassword(String email) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(ForgotPassword.this, ""+getString(R.string.check_email), Toast.LENGTH_LONG).show();
                }else{
                    progressBar.setVisibility(View.GONE);
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        emailEditText.setError("User does not exists or is no longer valid. lease register again.");
                        emailEditText.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}