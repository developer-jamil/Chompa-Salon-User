package com.champasalon.chunarughat.navigation_fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.About_Us;
import com.champasalon.chunarughat.ChangePassword;
import com.champasalon.chunarughat.MainActivity;
import com.champasalon.chunarughat.R;
import com.champasalon.chunarughat.SendAMessage;
import com.champasalon.chunarughat.custom.CustomLinkManage;
import com.champasalon.chunarughat.Setting;
import com.champasalon.chunarughat.UpdateUserInfo;
import com.champasalon.chunarughat.UploadProfileImage;
import com.champasalon.chunarughat.custom.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    CardView logout, userInfoUpdate;
    GoogleSignInClient googleSignInClient;

    //Firebase
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    TextView userFullName, emailTextView;

    LinearLayout profileImage, changePassword;
    CircleImageView userProfileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //settings
        CardView settingsCard = view.findViewById(R.id.settingsId);
        settingsCard.setOnClickListener(this);
        //about us
        LinearLayout aboutUsCard = view.findViewById(R.id.aboutUsId);
        aboutUsCard.setOnClickListener(this);
        //privacy policy
        LinearLayout privacyPolicy = view.findViewById(R.id.privacyPolicyId);
        privacyPolicy.setOnClickListener(this);
        //send a message
        LinearLayout sendAMessage = view.findViewById(R.id.sendAMessageId);
        sendAMessage.setOnClickListener(this);

        userInfoUpdate = view.findViewById(R.id.updateUserInfoId);
        userInfoUpdate.setOnClickListener(this);

        logout = view.findViewById(R.id.logOutId);
        mAuth = FirebaseAuth.getInstance();

        userFullName = view.findViewById(R.id.userNameTextViewId);
        emailTextView = view.findViewById(R.id.emailTextViewId);

        //profile image
        profileImage = view.findViewById(R.id.profileImageId);
        profileImage.setOnClickListener(this);

        //changePass
        changePassword = view.findViewById(R.id.passwordChangeId);
        changePassword.setOnClickListener(this);

        //user profile image set
        userProfileImage = view.findViewById(R.id.userImageViewId);
        //set user's current dp in imageview (if uploaded already). we will use picasso imageViewer

        mAuth = FirebaseAuth.getInstance();

        //email modify
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        String modifiedEmail = Objects.requireNonNull(email).replace(".", "-");


        //get user info
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Registered Users");
            databaseReference.child("All Users").child(modifiedEmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserInfo userInfo = snapshot.getValue(UserInfo.class);
                    if (userInfo != null) {
                        String fullName = userInfo.userName;
                        String email = userInfo.userEmail;
                        String profileImageLink = userInfo.profile_pic_link;

                        userFullName.setText(fullName);
                        emailTextView.setText(email);

                        // Get profile image
                        if (profileImageLink == null || profileImageLink.isEmpty()) {
                            Toast.makeText(requireActivity(), "No Profile Image Found", Toast.LENGTH_SHORT).show();
                        } else {
                            //get image
                            Picasso.get()
                                    .load(profileImageLink)
                                    .placeholder(R.drawable.image_placeholder)
                                    .into(userProfileImage);
                        }


                    } else {
                        Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
            Log.d("error:", "Name Locate");
        }

        //logout
        logout.setOnClickListener(view1 -> logoutFun());


    }



    private void logoutFun() {
        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        // Sign out from google
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            // Check condition
            if (task.isSuccessful()) {
                // When task is successful
                // Sign out from firebase
                FirebaseAuth.getInstance().signOut();

                // Display Toast
                Toast.makeText(requireActivity(), "Logout successful", Toast.LENGTH_SHORT).show();

                // Close all previous activities and start MainActivity
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();

            }
        });

    }


    //onclick action
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.updateUserInfoId) {
            startActivity(new Intent(requireActivity(), UpdateUserInfo.class));
        }
        //settings
        if (v.getId() == R.id.settingsId) {
            requireActivity().finish();
            startActivity(new Intent(requireActivity(), Setting.class));
        }
        //about us
        if (v.getId() == R.id.aboutUsId) {
            startActivity(new Intent(requireActivity(), About_Us.class));
        }
        //privacy policy
        if (v.getId() == R.id.privacyPolicyId) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(CustomLinkManage.privacyPolicy));
            startActivity(i);
        }
        //send a message
        if (v.getId() == R.id.sendAMessageId) {
            startActivity(new Intent(requireActivity(), SendAMessage.class));
        }
        if (v.getId() == R.id.profileImageId) {
            startActivity(new Intent(requireActivity(), UploadProfileImage.class));
        }
        if (v.getId() == R.id.passwordChangeId) {
            startActivity(new Intent(requireActivity(), ChangePassword.class));
        }
    }


}