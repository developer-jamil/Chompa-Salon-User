package com.champasalon.chunarughat.navigation_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.MainActivity;
import com.champasalon.chunarughat.R;
import com.champasalon.chunarughat.SerialHome;
import com.champasalon.chunarughat.Style_Beard;
import com.champasalon.chunarughat.Style_Facial;
import com.champasalon.chunarughat.Style_Hair;
import com.champasalon.chunarughat.Style_HairBeard;
import com.champasalon.chunarughat.custom.GlobalIdManager;
import com.champasalon.chunarughat.custom.ReviewControl;
import com.champasalon.chunarughat.custom.UserInfo;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private ImageView imageView1, imageView2, imageView3;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    TextInputEditText reviewText;
    TextView happyCustomers, personReviewName;
    LinearLayout reviewArea;
    Button reviewArea2;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String modifiedEmail;

    //previous and next review
    TextView textViewReview;
    List<String> keys;
    int currentIndex;
    CircleImageView reviewImageView;
    String uId2;

    //ads
    private InterstitialAd mInterstitialAd;

    //get review ans next prv button
    private List<String> reviewKeys = new ArrayList<>();
    // Declare currentReviewIndex as a class-level variable
    private int currentReviewIndex = 0;
    // Declare referenceReviewFun as a class-level variable
    private DatabaseReference referenceReviewFun;
    private String currentChildReview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //language saved
        loadLocalLandLang();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAd();

        requireActivity().setTitle(R.string.app_name);

        //auth get current user
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        //review person profile pic
        reviewImageView = view.findViewById(R.id.reviewImageViewId);

        happyCustomers = view.findViewById(R.id.happyCustomerId);
        TextView reviewCustomers = view.findViewById(R.id.reviewCustomerId);
        TextView totalCustomers = view.findViewById(R.id.totalCustomerId);

        reviewCustomers.setText("5★");

        Button submitButton = view.findViewById(R.id.submitReviewId);
        submitButton.setOnClickListener(this);
        reviewText = view.findViewById(R.id.reviewTextId);
        reviewArea = view.findViewById(R.id.reviewAreaId);
        reviewArea2 = view.findViewById(R.id.reviewAreaId_2);
        reviewArea2.setOnClickListener(this);
        personReviewName = view.findViewById(R.id.personReviewNameId);

        //internet Connection
        CardView ifNoInternet = view.findViewById(R.id.ifNoInternetId);
        if (isConnected()) {
            ifNoInternet.setVisibility(View.GONE);
        } else {
            ifNoInternet.setVisibility(View.VISIBLE);
        }

        Button serialHome = view.findViewById(R.id.serialHomeId);
        serialHome.setOnClickListener(this);

        //hair style
        CardView takeSerial = view.findViewById(R.id.takeSerialId);
        takeSerial.setOnClickListener(this);

        //hair style
        CardView hairStyle = view.findViewById(R.id.hairStyleId);
        hairStyle.setOnClickListener(this);
        //beard style
        CardView beardStyle = view.findViewById(R.id.beardStyleId);
        beardStyle.setOnClickListener(this);
        //hair and beard style
        CardView hairBeardStyle = view.findViewById(R.id.hairBeardStyleId);
        hairBeardStyle.setOnClickListener(this);
        //facial style
        CardView facialStyle = view.findViewById(R.id.facialStyleId);
        facialStyle.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        imageView1 = view.findViewById(R.id.imageView1Id);
        imageView2 = view.findViewById(R.id.imageView2Id);
        imageView3 = view.findViewById(R.id.imageView3Id);


        textViewReview = view.findViewById(R.id.txt_review);
        Button previousButton = view.findViewById(R.id.btn_prev_review);
        Button nextButton = view.findViewById(R.id.btn_next_review);

        //modify email
        String email = firebaseUser.getEmail();
        modifiedEmail = Objects.requireNonNull(email).replace(".", "-");


        //get reviews number in happy textView
        referenceReviewFun = FirebaseDatabase.getInstance().getReference("Customers Review").child("Happy");
        referenceReviewFun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //review count
                String countReview = String.valueOf(snapshot.getChildrenCount());
                happyCustomers.setText(countReview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //review control get set etc

        //get current user review - match current user modifiedEmail to database child (modifiedEmail)
        referenceReviewFun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {





                boolean isMatched = false;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    //next prev button
                    String reviewKey = childSnapshot.getKey();
                    reviewKeys.add(reviewKey);

                    //control review
                    ReviewControl reviewControl = childSnapshot.getValue(ReviewControl.class);
                    if (reviewControl != null && reviewControl.getModifiedEmail().equals(modifiedEmail)) {
                        // Match found
                        isMatched = true;
                        String matchedModifiedEmail = reviewControl.getModifiedEmail();

                        //if match modifiedEmail == current modifiedEmail - your review
                        if (modifiedEmail.equals(matchedModifiedEmail)) {
                            reviewArea.setVisibility(View.GONE);
                            reviewArea2.setVisibility(View.VISIBLE);
                            //get the review and set it
                            referenceReviewFun.child(matchedModifiedEmail).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userReviewText = reviewControl.userReview;
                                    reviewText.setText(userReviewText);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }else {
                            //not current user's review
                            reviewArea.setVisibility(View.VISIBLE);
                            reviewArea2.setVisibility(View.GONE);
                        }

                        break;
                    }
                }
                if (!isMatched) {
                    // No match found
                    Toast.makeText(requireActivity(), "No match found for your modifiedEmail", Toast.LENGTH_SHORT).show();
                }

                // Start review cycle if there are reviews - next prev button
                if (!reviewKeys.isEmpty()) {
                    currentReviewIndex = 0;
                    displayReviewByKey(referenceReviewFun, reviewKeys.get(currentReviewIndex));
                } else {
                    Toast.makeText(requireActivity(), "No reviews available", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });


        nextButton.setOnClickListener(v -> {
            if (currentReviewIndex < reviewKeys.size() - 1) {
                currentReviewIndex++;
                displayReviewByKey(referenceReviewFun, reviewKeys.get(currentReviewIndex));
            } else {
                Toast.makeText(requireActivity(), "No more reviews", Toast.LENGTH_SHORT).show();
            }
        });

        previousButton.setOnClickListener(v -> {
            if (currentReviewIndex > 0) { //else  Already at the first review
                currentReviewIndex--;
                displayReviewByKey(referenceReviewFun, reviewKeys.get(currentReviewIndex));
            }
        });




        try {
            //get image_1
            firebaseDatabase.getReference("Home_Cover_Img").child("image_1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String image = snapshot.getValue(String.class);
                    Picasso.get()
                            .load(image)
                            .placeholder(R.drawable.image_placeholder)
                            .into(imageView1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            //get image_2
            firebaseDatabase.getReference("Home_Cover_Img").child("image_2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String image = snapshot.getValue(String.class);
                    Picasso.get()
                            .load(image)
                            .placeholder(R.drawable.image_placeholder)
                            .into(imageView2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            //get image_3
            firebaseDatabase.getReference("Home_Cover_Img").child("image_3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String image = snapshot.getValue(String.class);
                    Picasso.get()
                            .load(image)
                            .placeholder(R.drawable.image_placeholder)
                            .into(imageView3);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error Home Cover Image Load: \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child("All Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String totalUser = String.valueOf(snapshot.getChildrenCount());
                totalCustomers.setText(totalUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //ads
        //home native small ad
        AdLoader adLoaderM = new AdLoader.Builder(requireActivity(), GlobalIdManager.smallNative)
                .forNativeAd(nativeAd -> {
                    // Show the ad.
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template = view.findViewById(R.id.templateHomeMedium);
                    template.setVisibility(View.VISIBLE);
                    TextView adText = view.findViewById(R.id.adTextId);
                    adText.setVisibility(View.VISIBLE);
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        loadAd();
                    }
                }).build();
        adLoaderM.loadAd(new AdRequest.Builder().build());

        //home native medium ad_2
        AdLoader adLoaderM2 = new AdLoader.Builder(requireActivity(), GlobalIdManager.mediumNative)
                .forNativeAd(nativeAd -> {
                    // Show the ad.
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template = view.findViewById(R.id.templateHomeMedium2);
                    template.setVisibility(View.VISIBLE);
                    TextView adText = view.findViewById(R.id.adTextId2);
                    adText.setVisibility(View.VISIBLE);
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        loadAd();
                    }
                }).build();
        adLoaderM2.loadAd(new AdRequest.Builder().build());


    }

    private void displayReviewByKey(DatabaseReference referenceReviewFun, String reviewKey) {

        //get the review using the current key
        referenceReviewFun = FirebaseDatabase.getInstance().getReference("Customers Review").child("Happy");
        referenceReviewFun.child(reviewKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReviewControl reviewControl = snapshot.getValue(ReviewControl.class);
                if (reviewControl != null) {
                    String currentChildReview = reviewControl.userReview;
                    textViewReview.setText(currentChildReview);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});

        //get user info from current child key
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child("All Users").child(reviewKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                if (userInfo != null) {
                    String currentChildName = userInfo.userName;
                    String currentChildPhotoLink = userInfo.profile_pic_link;
                    personReviewName.setText(currentChildName);
                    // Get  image
                    if (currentChildPhotoLink == null || currentChildPhotoLink.isEmpty()) {
                        Toast.makeText(requireActivity(), "No Image Found", Toast.LENGTH_SHORT).show();
                    } else {
                        Picasso.get().load(currentChildPhotoLink).placeholder(R.drawable.image_placeholder).into(reviewImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //onclick to
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.serialHomeId) {
            //book a serial
            assert firebaseUser != null;
            if (firebaseUser.isEmailVerified()) {
                startActivity(new Intent(getActivity(), SerialHome.class));
            } else {
                showAlertDialog();
            }
        }

        //review text
        if (v.getId() == R.id.submitReviewId) {
            String getReviewText = Objects.requireNonNull(reviewText.getText()).toString();
            if (getReviewText.isEmpty()) {
                reviewText.setError(getString(R.string.enter_your_review));
                reviewText.requestFocus();
                //loadAd();
            } else {
                try {
                    DatabaseReference referenceReviewR = FirebaseDatabase.getInstance().getReference("Customers Review").child("Happy").child(modifiedEmail);
                    referenceReviewR.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ReviewControl reviewControl = snapshot.getValue(ReviewControl.class);
                            if (reviewControl != null) {
                                //set
                                ReviewControl review = new ReviewControl(modifiedEmail, getReviewText);
                                referenceReviewR.setValue(review);
                                //get
                                String userReview = review.userReview;
                                reviewText.setText(userReview);
                                Toast.makeText(requireActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                                reviewArea.setVisibility(View.GONE);
                                reviewArea2.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    textViewReview.setText(e.getMessage());
                }
                interstitialAdCon();
                loadAd();
            }
        }
        //edit review
        if (v.getId() == R.id.reviewAreaId_2) {
            reviewArea2.setVisibility(View.GONE);
            reviewArea.setVisibility(View.VISIBLE);
            interstitialAdCon();
            loadAd();
        }


        //hair style
        if (v.getId() == R.id.takeSerialId) {
            //book a serial
            assert firebaseUser != null;
            if (firebaseUser.isEmailVerified()) {
                startActivity(new Intent(getActivity(), SerialHome.class));
            } else {
                showAlertDialog();
            }
        }//hair style
        if (v.getId() == R.id.hairStyleId) {
            startActivity(new Intent(requireActivity(), Style_Hair.class));
        }//beard style
        if (v.getId() == R.id.beardStyleId) {
            startActivity(new Intent(requireActivity(), Style_Beard.class));
        }//hairBeard Style
        if (v.getId() == R.id.hairBeardStyleId) {
            startActivity(new Intent(requireActivity(), Style_HairBeard.class));
        }//facial style
        if (v.getId() == R.id.facialStyleId) {
            startActivity(new Intent(requireActivity(), Style_Facial.class));
        }

    }


    //interstitial ad
    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(requireActivity(), GlobalIdManager.interstitialAd_video, adRequest,
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

    private void interstitialAdCon() {
        //show interstitial video ads here
        if (mInterstitialAd != null) {
            mInterstitialAd.show(requireActivity());
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

    //not verified email show alert dialog
    private void showAlertDialog() {
        //setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.email_not_verified);
        builder.setMessage(R.string.verify_msg_text);

        //open email app if user Click continue button
        builder.setPositiveButton(R.string.continue_text, (dialogInterface, i) -> {
            firebaseUser.sendEmailVerification();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //to email app in new window and not within our app
            startActivity(intent);
        });
        builder.setNeutralButton(R.string.login_again, (dialog, which) -> {
            requireActivity().finish();
            startActivity(new Intent(requireActivity(), MainActivity.class));
        });

        //create the alertdialog
        AlertDialog alertDialog = builder.create();

        //show the alertDialog
        alertDialog.show();

    }

    //internet Connection
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    //language setting
    private void setLocalLandLang(String language) {

        Locale localeMS = new Locale(language);
        Locale.setDefault(localeMS);

        Configuration configuration = new Configuration();
        configuration.locale = localeMS;
        getResources().updateConfiguration(configuration, requireActivity().getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("SettingLandCal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_SettingLandCal", language);
        editor.apply();
    }

    private void loadLocalLandLang() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("SettingLandCal", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("app_SettingLandCal", "0");
        setLocalLandLang(language);
    }


}