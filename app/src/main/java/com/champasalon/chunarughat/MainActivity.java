package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.GlobalIdManager;
import com.champasalon.chunarughat.custom.UserInfo;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient client;
    private ProgressBar progressBar;
    private EditText loginEmail, loginPass;
    private static final String TAG = "MainActivity";
    private TextView forgotPass;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ads
        MobileAds.initialize(this, initializationStatus -> {});

        //get admob app id from firebase database
        AdMobAdAppIdFromFirebase();

        //fullScreen activity
        Objects.requireNonNull(getSupportActionBar()).hide();

        // hide title bar and action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // hide time, notifications, network, and battery icons
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LOW_PROFILE;
        getWindow().getDecorView().setSystemUiVisibility(flags);


        //progressbar
        progressBar = findViewById(R.id.progressBarId);

        //create new account
        TextView createNewAccount = findViewById(R.id.createNewAccountId);
        createNewAccount.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Register.class)));


        //internet connection area
        TextView connectionArea = findViewById(R.id.internetConnectionCheckAreaId);
        connectionArea.setOnClickListener(v -> recreate());


        //internet connection check - if no internet
        if (!CheckNetwork.isInternetAvailable(this)) {
            //if there is no internet do this
            connectionArea.setVisibility(View.VISIBLE);
        } else { //if connected with internet
            //Web view stuff
            // Find the WebView and configure it
            connectionArea.setVisibility(View.GONE);
        }

        //login user
        Button login = findViewById(R.id.loginButtonId);
        login.setOnClickListener(this);

        loginEmail = findViewById(R.id.emailEditTextId);
        loginPass = findViewById(R.id.passwordEditTextId);
        progressBar = findViewById(R.id.progressBarId);
        forgotPass = findViewById(R.id.forgotPasswordId);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

        //google sign in
        // Initialize variables
        SignInButton signInButton = findViewById(R.id.signInWithGoogle);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);
        signInButton.setOnClickListener(v -> {
            Intent i = client.getSignInIntent();
            startActivityForResult(i, 7313);
        });

        //get the user token
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userToken = task.getResult().getToken();
                        } else {
                            userToken = "";
                        }
                    });
        }


    }


    @Override
    public void onClick(View v) {
        //login
        if (v.getId() == R.id.loginButtonId) {
            String textEmail = loginEmail.getText().toString();
            String textPass = loginPass.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                loginEmail.setError("Enter your email...");
                loginEmail.requestFocus();
            } else if (TextUtils.isEmpty(textPass)) {
                loginPass.setError("Enter Password...");
                loginPass.requestFocus();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                loginUser(textEmail, textPass);
            }
        }
    }

    //login Method
    private void loginUser(String textEmail, String textPass) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(textEmail, textPass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                //get instance of the current user
                FirebaseUser firebaseUser = auth.getCurrentUser();
                //check if email is verified before user can access their profile
                assert firebaseUser != null;
                if (firebaseUser.isEmailVerified()) {
                    startActivity(new Intent(getApplicationContext(), Welcome.class));
                    finish();
                } else {
                    firebaseUser.sendEmailVerification();
                    auth.signOut(); //sign out user
                    showAlertDialog();
                }


            } else {
                progressBar.setVisibility(View.GONE);
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthInvalidUserException e) {
                    loginEmail.setError("User does not exist or is no longer valid. Please register again.");
                    loginEmail.requestFocus();
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    loginPass.setError("Wrong Password.");
                    loginPass.requestFocus();
                    forgotPass.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //not verified email show alert dialog
    private void showAlertDialog() {
        //setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now, You can't login without email verification");

        //open email app if user Click continue button
        builder.setPositiveButton("Continue", (dialogInterface, i) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //to email app in new window and not within our app
            startActivity(intent);
        });
        //create the alertdialog
        AlertDialog alertDialog = builder.create();
        //show the alertDialog
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7313) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            progressBar.setVisibility(View.VISIBLE);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String selectedEmail = account.getEmail(); // get the selected Google account's email
                String modifiedEmail = Objects.requireNonNull(selectedEmail).replace(".", "-");

                // Check if the user already exists in the Realtime Database
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                referenceProfile.child("All Users").child(modifiedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // User already exists, sign in with credential
                            Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            // Retrieve the FCM token
                                            FirebaseMessaging.getInstance().getToken()
                                                    .addOnCompleteListener(tokenTask -> {
                                                        if (tokenTask.isSuccessful()) {
                                                            String token = tokenTask.getResult();

                                                            // Save the token in the database
                                                            referenceProfile.child("All Users").child(modifiedEmail).child("userToken").setValue(token)
                                                                    .addOnCompleteListener(tokenSaveTask -> {
                                                                        if (tokenSaveTask.isSuccessful()) {
                                                                            progressBar.setVisibility(View.GONE);
                                                                            finish();
                                                                            Intent intent = new Intent(getApplicationContext(), Welcome.class);
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Toast.makeText(MainActivity.this, Objects.requireNonNull(tokenSaveTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        } else {
                                                            Toast.makeText(MainActivity.this, Objects.requireNonNull(tokenTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(MainActivity.this, Objects.requireNonNull(task2.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            UserInfo userInfo = new UserInfo(
                                    account.getDisplayName(),
                                    selectedEmail,
                                    "",
                                    "",
                                    "",
                                    "Regular",
                                    Objects.requireNonNull(account.getPhotoUrl()).toString(),
                                    "user",
                                    ""
                            );

                            // User does not exist, create new entry in Realtime Database
                            referenceProfile.child("All Users").child(modifiedEmail).setValue(userInfo).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    // Sign in with the credential
                                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                                    FirebaseAuth.getInstance().signInWithCredential(credential)
                                            .addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful()) {
                                                    // Retrieve the FCM token
                                                    FirebaseMessaging.getInstance().getToken()
                                                            .addOnCompleteListener(tokenTask -> {
                                                                if (tokenTask.isSuccessful()) {
                                                                    String token = tokenTask.getResult();
                                                                    // Save the token in the database
                                                                    referenceProfile.child("All Users").child(modifiedEmail).child("userToken").setValue(token)
                                                                            .addOnCompleteListener(tokenSaveTask -> {
                                                                                if (tokenSaveTask.isSuccessful()) {
                                                                                    progressBar.setVisibility(View.GONE);
                                                                                    finish();
                                                                                    Intent intent = new Intent(getApplicationContext(), Welcome.class);
                                                                                    startActivity(intent);
                                                                                } else {
                                                                                    Toast.makeText(MainActivity.this, Objects.requireNonNull(tokenSaveTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                } else {
                                                                    Toast.makeText(MainActivity.this, Objects.requireNonNull(tokenTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(MainActivity.this, Objects.requireNonNull(task2.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(MainActivity.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // handle database error
                    }
                });

            } catch (ApiException e) {
                // handle the exception
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            finish();
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
        }
    }


    //network check
    private static class CheckNetwork {
        static final String TAG = CheckNetwork.class.getSimpleName();

        static boolean isInternetAvailable(Context context) {
            NetworkInfo info = ((ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if (info == null) {
                Log.d(TAG, "no internet connection");
                return false;
            } else {
                Log.d(TAG, " internet connection available...");
                return true;
            }
        }
    }

    //get admob app id from firebase database
    private void AdMobAdAppIdFromFirebase() {
        try {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("AdMobAds");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //for app id
                    String appId = snapshot.child("AppId").getValue(String.class);
                    GlobalIdManager.appID = appId;

                    //for banner ap unit id
                    String bannerAd = snapshot.child("Banner_ad_unit_id").getValue(String.class);
                    GlobalIdManager.bannerAd = bannerAd;


                    //for interstitial ap unit id
                    String interstitialAd_img = snapshot.child("Interstitial_ad_unit_id_img").getValue(String.class);
                    GlobalIdManager.interstitialAd_img = interstitialAd_img;

                    String interstitialAd_video = snapshot.child("Interstitial_ad_unit_id_video").getValue(String.class);
                    GlobalIdManager.interstitialAd_video = interstitialAd_video;


                    String interstitialAdHome = snapshot.child("Interstitial_ad_unit_idHome").getValue(String.class);
                    GlobalIdManager.interstitialAdHome = interstitialAdHome;


                    //for native ap unit id
                    String nativeAd = snapshot.child("Native_ad_unit_id").getValue(String.class);
                    GlobalIdManager.nativeAd = nativeAd;

                    //for native small ap unit id
                    String nativeAdSmall = snapshot.child("Small_Native_ad_unit_id").getValue(String.class);
                    GlobalIdManager.nativeAdSmall = nativeAdSmall;

                    //we have to change appId with firebase
                    try {
                        ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                        Bundle bundle = appInfo.metaData;
                        appInfo.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", GlobalIdManager.appID);
                        String API_KEY = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                        Log.d("AppId", "The saved id is" + GlobalIdManager.appID);
                        Log.d("AppId", "The saved id is" + API_KEY);
                    } catch (PackageManager.NameNotFoundException |
                             NullPointerException NameNotFoundException) {
                        NameNotFoundException.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}