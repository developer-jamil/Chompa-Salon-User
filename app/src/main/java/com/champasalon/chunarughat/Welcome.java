package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.ReadWriteUserDetails;
import com.champasalon.chunarughat.custom.UserInfo;
import com.champasalon.chunarughat.navigation_fragment.BookingFragment;
import com.champasalon.chunarughat.navigation_fragment.HomeFragment;
import com.champasalon.chunarughat.navigation_fragment.ProfileFragment;
import com.champasalon.chunarughat.navigation_fragment.StyleFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Welcome extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    private int selectedItemId = 1; // Default selected item ID is 1 (home)

    //for name
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private String userName;

    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Set home as the default fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_id, HomeFragment.class, null)
                .commit();

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.show(1, true); // Select home icon by default

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_watch_later_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_style_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_person_24));

        bottomNavigation.setOnClickMenuListener(model -> {
            // Update the selected item ID
            selectedItemId = model.getId();

            // Handle fragment navigation
            switch (model.getId()) {
                case 1:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_id, HomeFragment.class, null)
                            .commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_id, BookingFragment.class, null)
                            .commit();
                    break;
                case 3:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_id, StyleFragment.class, null)
                            .commit();
                    break;
                case 4:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_id, ProfileFragment.class, null)
                            .commit();
                    break;
            }

            return null;
        });

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        // Check if email is verified before allowing access to the profile
        if (firebaseUser != null && !firebaseUser.isEmailVerified()) {
            showVerifyAlertDialog();
        }


        //uid to modify email child convert
        String uid = Objects.requireNonNull(firebaseUser).getUid();
        String email = firebaseUser.getEmail();
        String modifiedEmail = Objects.requireNonNull(email).replace(".", "-");


        //update old user id to modify email to child
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users").child("All Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)) {
                    //Toast.makeText(Welcome.this, "Updating", Toast.LENGTH_SHORT).show();

                    // Child with UID exists, retrieve the old values
                    DataSnapshot uidSnapshot = dataSnapshot.child(uid);
                    ReadWriteUserDetails oldUserDetails = uidSnapshot.getValue(ReadWriteUserDetails.class);

                    if (oldUserDetails != null) {
                        String oldName = oldUserDetails.fullName;
                        String oldEmail = oldUserDetails.email;
                        String oldMobile = oldUserDetails.mobile;
                        String oldDob = oldUserDetails.dob;
                        String oldGender = oldUserDetails.gender;
                        String userProfileBudgetStatus = "Regular";
                        String profile_pic_link = "";
                        String userCode  = "";

                        // Retrieve the FCM token
                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(tokenTask -> {
                                    if (tokenTask.isSuccessful()) {
                                        String userToken = tokenTask.getResult();

                                        // Delete the older strings from Firebase
                                        uidSnapshot.getRef().removeValue().addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                // Add new strings in Firebase using UserInfo class
                                                UserInfo userInfo = new UserInfo(oldName, oldEmail, oldMobile, oldGender, oldDob, userProfileBudgetStatus, profile_pic_link, userCode, userToken);
                                                dataSnapshot.getRef().child(modifiedEmail).setValue(userInfo)
                                                        .addOnCompleteListener(task1 -> {
                                                            if (task1.isSuccessful()) {
                                                                // New values set successfully
                                                                //Toast.makeText(Welcome.this, "Updated", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                // Handle the error case
                                                                Toast.makeText(Welcome.this, "Error occurred while updating values", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                // Handle the error case
                                                Toast.makeText(Welcome.this, "Error occurred while deleting old values", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(Welcome.this, Objects.requireNonNull(tokenTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        // Handle the case where oldUserDetails is null
                        Toast.makeText(Welcome.this, "Error occurred while retrieving old values", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error case
                Toast.makeText(Welcome.this, "Error occurred while reading data", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void showVerifyAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Welcome.this);
        alertDialogBuilder.setTitle(R.string.verify_email);
        alertDialogBuilder.setMessage(R.string.verify_msg_text);
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setNegativeButton(R.string.later, (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton(R.string.verify_now, (dialog, which) -> {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                firebaseUser.sendEmailVerification();
                startActivity(new Intent(Welcome.this, VerifyEmail.class));
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigation != null && bottomNavigation.isShown()) {
            if (selectedItemId == 1) {
                // If the current selected item is home, perform the default back button action
                super.onBackPressed();
                this.finish();
            } else {
                // If the current selected item is not home, set the home item as selected
                bottomNavigation.show(1, true);
                selectedItemId = 1;
                // Set home fragment
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_id, HomeFragment.class, null)
                        .commit();
            }
        } else {
            super.onBackPressed();
            this.finish();
        }
    }
}