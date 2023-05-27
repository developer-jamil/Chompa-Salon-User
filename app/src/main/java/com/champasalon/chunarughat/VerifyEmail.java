package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.ReadWriteUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class VerifyEmail extends AppCompatActivity implements View.OnClickListener {
    //for name
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    //Firebase
    FirebaseAuth mAuth;
    private String email;
    FirebaseUser firebaseUser;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        TextView yourEmail = findViewById(R.id.yourEmailId);
        Button openEmail = findViewById(R.id.openEmailId);
        openEmail.setOnClickListener(this);

        Button refresh = findViewById(R.id.loginAgainId);
        refresh.setOnClickListener(this);

        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            firebaseUser = mAuth.getCurrentUser();
            assert firebaseUser != null;
            String userId = firebaseUser.getUid();
            databaseReference = firebaseDatabase.getReference("Registered Users");
            databaseReference.child("All Users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                    if (readWriteUserDetails != null) {
                        email = readWriteUserDetails.email;
                        yourEmail.setText(getString(R.string.email_text)+ ": " + email);
                    } else {
                        Toast.makeText(VerifyEmail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "error: email:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.openEmailId) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //to email app in new window and not within our app
            startActivity(intent);
        }
        if (v.getId() == R.id.loginAgainId) {
            finish();
            startActivity(new Intent(VerifyEmail.this, MainActivity.class));
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}