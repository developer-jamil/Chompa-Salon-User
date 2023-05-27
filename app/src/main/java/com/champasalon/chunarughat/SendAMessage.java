package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.CustomMessage;
import com.champasalon.chunarughat.custom.GlobalIdManager;
import com.champasalon.chunarughat.pushNotification.NotificationData;
import com.champasalon.chunarughat.pushNotification.PushNotification;
import com.champasalon.chunarughat.pushNotification.RetrofitInstance;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendAMessage extends AppCompatActivity {
    EditText nameE, subjectE, messageE;
    Button sendButton;
    String uid;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    //ads
    private InterstitialAd mInterstitialAd;

    //push notification
    private static final String TOPIC = "/topics/myTopic2";
    private static final String TAG = "PersonOnePush";

    //String recipientToken;
    String recipientToken;

    DatabaseReference databaseRef;
    //firebase
    FirebaseDatabase firebaseDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_amessage);

        loadAd();

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.setTitle(R.string.send_a_message);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Messages_DB");
        assert firebaseUser != null;
        uid = firebaseUser.getUid();

        nameE = findViewById(R.id.nameEditTextId);
        subjectE = findViewById(R.id.subjectEditTextId);
        messageE = findViewById(R.id.messageEditTextId);
        sendButton = findViewById(R.id.sendMessageButtonId);
        sendButton.setOnClickListener(v -> sendMessage());

        // Initialize Firebase components
        firebaseDatabase = FirebaseDatabase.getInstance();

        //get person 1 token
        databaseRef = firebaseDatabase.getReference("Craft Man");
        databaseRef.child("Person_Token").child("person_one").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    recipientToken = snapshot.getValue(String.class);
                }else {
                    recipientToken = "";
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});


    }





    //message send method
    private void sendMessage() {

        loadAd();

        String senderName = nameE.getText().toString();
        String messageSubject = subjectE.getText().toString();
        String messageInfo = messageE.getText().toString();

        if (senderName.isEmpty()) {
            nameE.setError(getString(R.string.enter_your_name));
            nameE.requestFocus();
        } else if (messageSubject.isEmpty()) {
            subjectE.setError(getString(R.string.subject_text));
            subjectE.requestFocus();
        }else if (messageInfo.isEmpty()) {
            messageE.setError(getString(R.string.message_text));
            messageE.requestFocus();
        }else{
            //onclick to saved data in database
            CustomMessage customMessage = new CustomMessage(senderName, messageSubject, messageInfo, uid);
            String key = databaseReference.push().getKey();
            assert key != null;
            databaseReference.child(key).setValue(customMessage);
            nameE.setText("");
            subjectE.setText("");
            messageE.setText("");
            showAd();
            loadAd();
            sendPushToAdmin(senderName, messageInfo);
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
        }


    }

    private void sendPushToAdmin(String senderName, String messageInfo) {
        //topic
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
        String title = senderName + " "+ getString(R.string.send_a_message_by);
        String message = messageInfo;

        NotificationData notificationData = new NotificationData(title, message);
        PushNotification pushNotification = new PushNotification(notificationData, recipientToken);
        sendNotification(pushNotification);
    }

    private void sendNotification(PushNotification notification) {
        RetrofitInstance.getApi().postNotification(notification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body() != null ? response.body().string() : "";
                        Log.d(TAG, "Response: " + responseBody);
                    } catch (IOException e) {
                        Log.e(TAG, "IOException: " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, Objects.requireNonNull(response.errorBody()).toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }





    //interstitial ad
    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(getApplicationContext(), GlobalIdManager.interstitialAd_video, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }

    private void showAd(){
        //show interstitial video ads here
        if (mInterstitialAd != null) {
            mInterstitialAd.show(SendAMessage.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    loadAd();
                }
            });
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
