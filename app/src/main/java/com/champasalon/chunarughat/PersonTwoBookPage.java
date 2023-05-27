package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.CustomSpinnerAdapter;
import com.champasalon.chunarughat.custom.CustomSpinnerItem;
import com.champasalon.chunarughat.custom.Serial_Control;
import com.champasalon.chunarughat.custom.UserInfo;
import com.champasalon.chunarughat.pushNotification.NotificationData;
import com.champasalon.chunarughat.pushNotification.PushNotification;
import com.champasalon.chunarughat.pushNotification.RetrofitInstance;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonTwoBookPage extends AppCompatActivity implements View.OnClickListener {
    BottomSheetDialog sheetDialog;

    //firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference databaseRef;
    //custom spinner
    ArrayList<CustomSpinnerItem> customList;


    String available = "available";
    String booked = "booked";
    String slPerson = "Person_2_Booking_List";
    private String modifiedEmail, type;
    private String CurrentUserName, CurrentUserMobile, CurrentUserProfileImage;
    private String bookedUserName, bookedUserMobile, bookedUserProfileImage;
    private String type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21;

    private String childModifyEmail1, childModifyEmail2, childModifyEmail3, childModifyEmail4, childModifyEmail5,
            childModifyEmail6, childModifyEmail7, childModifyEmail8, childModifyEmail9, childModifyEmail10,
            childModifyEmail11, childModifyEmail12, childModifyEmail13, childModifyEmail14, childModifyEmail15,
            childModifyEmail16, childModifyEmail17, childModifyEmail18, childModifyEmail19, childModifyEmail20,
            childModifyEmail21;

    private EditText setName, setMobile;
    boolean isSerialAvailable1 = false;
    boolean isSerialAvailable2 = false;
    boolean isSerialAvailable3 = false;
    boolean isSerialAvailable4 = false;
    boolean isSerialAvailable5 = false;
    boolean isSerialAvailable6 = false;
    boolean isSerialAvailable7 = false;
    boolean isSerialAvailable8 = false;
    boolean isSerialAvailable9 = false;
    boolean isSerialAvailable10 = false;
    boolean isSerialAvailable11 = false;
    boolean isSerialAvailable12 = false;
    boolean isSerialAvailable13 = false;
    boolean isSerialAvailable14 = false;
    boolean isSerialAvailable15 = false;
    boolean isSerialAvailable16 = false;
    boolean isSerialAvailable17 = false;
    boolean isSerialAvailable18 = false;
    boolean isSerialAvailable19 = false;
    boolean isSerialAvailable20 = false;
    boolean isSerialAvailable21 = false;


    //push notification
    private static final String TOPIC = "/topics/myTopic2";
    private static final String TAG = "PersonOnePush";

    //String recipientToken;
    String recipientToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_two_book_page);


        Bundle bundle = getIntent().getExtras();
        String getTitleName = bundle.getString("person_2");
        this.setTitle(getString(R.string.serials) + " - " + getTitleName);


        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Firebase components
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        //cardView initialize
        CardView serialId_1 = findViewById(R.id.serialId_1);
        CardView serialId_2 = findViewById(R.id.serialId_2);
        CardView serialId_3 = findViewById(R.id.serialId_3);
        CardView serialId_4 = findViewById(R.id.serialId_4);
        CardView serialId_5 = findViewById(R.id.serialId_5);
        CardView serialId_6 = findViewById(R.id.serialId_6);
        CardView serialId_7 = findViewById(R.id.serialId_7);
        CardView serialId_8 = findViewById(R.id.serialId_8);
        CardView serialId_9 = findViewById(R.id.serialId_9);
        CardView serialId_10 = findViewById(R.id.serialId_10);
        CardView serialId_11 = findViewById(R.id.serialId_11);
        CardView serialId_12 = findViewById(R.id.serialId_12);
        CardView serialId_13 = findViewById(R.id.serialId_13);
        CardView serialId_14 = findViewById(R.id.serialId_14);
        CardView serialId_15 = findViewById(R.id.serialId_15);
        CardView serialId_16 = findViewById(R.id.serialId_16);
        CardView serialId_17 = findViewById(R.id.serialId_17);
        CardView serialId_18 = findViewById(R.id.serialId_18);
        CardView serialId_19 = findViewById(R.id.serialId_19);
        CardView serialId_20 = findViewById(R.id.serialId_20);
        CardView serialId_21 = findViewById(R.id.serialId_21);

        serialId_1.setOnClickListener(this);
        serialId_2.setOnClickListener(this);
        serialId_3.setOnClickListener(this);
        serialId_4.setOnClickListener(this);
        serialId_5.setOnClickListener(this);
        serialId_6.setOnClickListener(this);
        serialId_7.setOnClickListener(this);
        serialId_8.setOnClickListener(this);
        serialId_9.setOnClickListener(this);
        serialId_10.setOnClickListener(this);
        serialId_11.setOnClickListener(this);
        serialId_12.setOnClickListener(this);
        serialId_13.setOnClickListener(this);
        serialId_14.setOnClickListener(this);
        serialId_15.setOnClickListener(this);
        serialId_16.setOnClickListener(this);
        serialId_17.setOnClickListener(this);
        serialId_18.setOnClickListener(this);
        serialId_19.setOnClickListener(this);
        serialId_20.setOnClickListener(this);
        serialId_21.setOnClickListener(this);


        //ImageView initialize
        ImageView sl_img_1 = findViewById(R.id.sl_img_1_id);
        ImageView sl_img_2 = findViewById(R.id.sl_img_2_id);
        ImageView sl_img_3 = findViewById(R.id.sl_img_3_id);
        ImageView sl_img_4 = findViewById(R.id.sl_img_4_id);
        ImageView sl_img_5 = findViewById(R.id.sl_img_5_id);
        ImageView sl_img_6 = findViewById(R.id.sl_img_6_id);
        ImageView sl_img_7 = findViewById(R.id.sl_img_7_id);
        ImageView sl_img_8 = findViewById(R.id.sl_img_8_id);
        ImageView sl_img_9 = findViewById(R.id.sl_img_9_id);
        ImageView sl_img_10 = findViewById(R.id.sl_img_10_id);
        ImageView sl_img_11 = findViewById(R.id.sl_img_11_id);
        ImageView sl_img_12 = findViewById(R.id.sl_img_12_id);
        ImageView sl_img_13 = findViewById(R.id.sl_img_13_id);
        ImageView sl_img_14 = findViewById(R.id.sl_img_14_id);
        ImageView sl_img_15 = findViewById(R.id.sl_img_15_id);
        ImageView sl_img_16 = findViewById(R.id.sl_img_16_id);
        ImageView sl_img_17 = findViewById(R.id.sl_img_17_id);
        ImageView sl_img_18 = findViewById(R.id.sl_img_18_id);
        ImageView sl_img_19 = findViewById(R.id.sl_img_19_id);
        ImageView sl_img_20 = findViewById(R.id.sl_img_20_id);
        ImageView sl_img_21 = findViewById(R.id.sl_img_21_id);

        //TextView initialize
        TextView sl_text_1 = findViewById(R.id.sl_1_textViewId);
        TextView sl_text_2 = findViewById(R.id.sl_2_textViewId);
        TextView sl_text_3 = findViewById(R.id.sl_3_textViewId);
        TextView sl_text_4 = findViewById(R.id.sl_4_textViewId);
        TextView sl_text_5 = findViewById(R.id.sl_5_textViewId);
        TextView sl_text_6 = findViewById(R.id.sl_6_textViewId);
        TextView sl_text_7 = findViewById(R.id.sl_7_textViewId);
        TextView sl_text_8 = findViewById(R.id.sl_8_textViewId);
        TextView sl_text_9 = findViewById(R.id.sl_9_textViewId);
        TextView sl_text_10 = findViewById(R.id.sl_10_textViewId);
        TextView sl_text_11 = findViewById(R.id.sl_11_textViewId);
        TextView sl_text_12 = findViewById(R.id.sl_12_textViewId);
        TextView sl_text_13 = findViewById(R.id.sl_13_textViewId);
        TextView sl_text_14 = findViewById(R.id.sl_14_textViewId);
        TextView sl_text_15 = findViewById(R.id.sl_15_textViewId);
        TextView sl_text_16 = findViewById(R.id.sl_16_textViewId);
        TextView sl_text_17 = findViewById(R.id.sl_17_textViewId);
        TextView sl_text_18 = findViewById(R.id.sl_18_textViewId);
        TextView sl_text_19 = findViewById(R.id.sl_19_textViewId);
        TextView sl_text_20 = findViewById(R.id.sl_20_textViewId);
        TextView sl_text_21 = findViewById(R.id.sl_21_textViewId);


        //get person 1 token
        databaseRef = firebaseDatabase.getReference("Craft Man");
        databaseRef.child("Person_Token").child("person_two").addListenerForSingleValueEvent(new ValueEventListener() {
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


        //modify email
        String email = firebaseUser.getEmail();
        modifiedEmail = Objects.requireNonNull(email).replace(".", "-");

        //current user info
        databaseRef = firebaseDatabase.getReference("Registered Users");
        databaseRef.child("All Users").child(modifiedEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                if (userInfo != null) {
                    CurrentUserName = userInfo.userName;
                    CurrentUserMobile = userInfo.userMobile;
                    CurrentUserProfileImage = userInfo.profile_pic_link;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //check serial available or not
        databaseReference = firebaseDatabase.getReference(slPerson);

        //serial_check_1
        databaseReference.child("Serial_1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    childModifyEmail1 = serial_control.email;
                    type = serial_control.type;
                    type1 = serial_control.type;
                    isSerialAvailable1 = serialCheck.equals(available);
                    if (isSerialAvailable1) {
                        sl_img_1.setImageResource(R.drawable.available);
                        sl_text_1.setText(R.string.click_to_book);
                    } else {
                        sl_img_1.setImageResource(R.drawable.booked);
                        sl_text_1.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //serial_check_2
        databaseReference.child("Serial_2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    childModifyEmail2 = serial_control.email;
                    type = serial_control.type;
                    type2 = serial_control.type;
                    isSerialAvailable2 = serialCheck.equals(available);
                    if (isSerialAvailable2) {
                        sl_img_2.setImageResource(R.drawable.available);
                        sl_text_2.setText(R.string.click_to_book);
                    } else {
                        sl_img_2.setImageResource(R.drawable.booked);
                        sl_text_2.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 3
        databaseReference.child("Serial_3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 3
                    // Modify the variable names according to your requirement
                    childModifyEmail3 = serial_control.email;
                    type3 = serial_control.type;
                    isSerialAvailable3 = serialCheck.equals(available);
                    if (isSerialAvailable3) {
                        sl_img_3.setImageResource(R.drawable.available);
                        sl_text_3.setText(R.string.click_to_book);
                    } else {
                        sl_img_3.setImageResource(R.drawable.booked);
                        sl_text_3.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 4
        databaseReference.child("Serial_4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 4
                    // Modify the variable names according to your requirement
                    childModifyEmail4 = serial_control.email;
                    type4 = serial_control.type;
                    isSerialAvailable4 = serialCheck.equals(available);
                    if (isSerialAvailable4) {
                        sl_img_4.setImageResource(R.drawable.available);
                        sl_text_4.setText(R.string.click_to_book);
                    } else {
                        sl_img_4.setImageResource(R.drawable.booked);
                        sl_text_4.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 5
        databaseReference.child("Serial_5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 5
                    // Modify the variable names according to your requirement
                    childModifyEmail5 = serial_control.email;
                    type5 = serial_control.type;
                    isSerialAvailable5 = serialCheck.equals(available);
                    if (isSerialAvailable5) {
                        sl_img_5.setImageResource(R.drawable.available);
                        sl_text_5.setText(R.string.click_to_book);
                    } else {
                        sl_img_5.setImageResource(R.drawable.booked);
                        sl_text_5.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 6
        databaseReference.child("Serial_6").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 6
                    // Modify the variable names according to your requirement
                    childModifyEmail6 = serial_control.email;
                    type6 = serial_control.type;
                    isSerialAvailable6 = serialCheck.equals(available);
                    if (isSerialAvailable6) {
                        sl_img_6.setImageResource(R.drawable.available);
                        sl_text_6.setText(R.string.click_to_book);
                    } else {
                        sl_img_6.setImageResource(R.drawable.booked);
                        sl_text_6.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 7
        databaseReference.child("Serial_7").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 7
                    // Modify the variable names according to your requirement
                    childModifyEmail7 = serial_control.email;
                    type7 = serial_control.type;
                    isSerialAvailable7 = serialCheck.equals(available);
                    if (isSerialAvailable7) {
                        sl_img_7.setImageResource(R.drawable.available);
                        sl_text_7.setText(R.string.click_to_book);
                    } else {
                        sl_img_7.setImageResource(R.drawable.booked);
                        sl_text_7.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 8
        databaseReference.child("Serial_8").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 8
                    // Modify the variable names according to your requirement
                    childModifyEmail8 = serial_control.email;
                    type8 = serial_control.type;
                    isSerialAvailable8 = serialCheck.equals(available);
                    if (isSerialAvailable8) {
                        sl_img_8.setImageResource(R.drawable.available);
                        sl_text_8.setText(R.string.click_to_book);
                    } else {
                        sl_img_8.setImageResource(R.drawable.booked);
                        sl_text_8.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 9
        databaseReference.child("Serial_9").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 9
                    // Modify the variable names according to your requirement
                    childModifyEmail9 = serial_control.email;
                    type9 = serial_control.type;
                    isSerialAvailable9 = serialCheck.equals(available);
                    if (isSerialAvailable9) {
                        sl_img_9.setImageResource(R.drawable.available);
                        sl_text_9.setText(R.string.click_to_book);
                    } else {
                        sl_img_9.setImageResource(R.drawable.booked);
                        sl_text_9.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 10
        databaseReference.child("Serial_10").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 10
                    // Modify the variable names according to your requirement
                    childModifyEmail10 = serial_control.email;
                    type10 = serial_control.type;
                    isSerialAvailable10 = serialCheck.equals(available);
                    if (isSerialAvailable10) {
                        sl_img_10.setImageResource(R.drawable.available);
                        sl_text_10.setText(R.string.click_to_book);
                    } else {
                        sl_img_10.setImageResource(R.drawable.booked);
                        sl_text_10.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 11
        databaseReference.child("Serial_11").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 11
                    // Modify the variable names according to your requirement
                    childModifyEmail11 = serial_control.email;
                    type11 = serial_control.type;
                    isSerialAvailable11 = serialCheck.equals(available);
                    if (isSerialAvailable11) {
                        sl_img_11.setImageResource(R.drawable.available);
                        sl_text_11.setText(R.string.click_to_book);
                    } else {
                        sl_img_11.setImageResource(R.drawable.booked);
                        sl_text_11.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 12
        databaseReference.child("Serial_12").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 12
                    // Modify the variable names according to your requirement
                    childModifyEmail12 = serial_control.email;
                    type12 = serial_control.type;
                    isSerialAvailable12 = serialCheck.equals(available);
                    if (isSerialAvailable12) {
                        sl_img_12.setImageResource(R.drawable.available);
                        sl_text_12.setText(R.string.click_to_book);
                    } else {
                        sl_img_12.setImageResource(R.drawable.booked);
                        sl_text_12.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 13
        databaseReference.child("Serial_13").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 13
                    // Modify the variable names according to your requirement
                    childModifyEmail13 = serial_control.email;
                    type13 = serial_control.type;
                    isSerialAvailable13 = serialCheck.equals(available);
                    if (isSerialAvailable13) {
                        sl_img_13.setImageResource(R.drawable.available);
                        sl_text_13.setText(R.string.click_to_book);
                    } else {
                        sl_img_13.setImageResource(R.drawable.booked);
                        sl_text_13.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 14
        databaseReference.child("Serial_14").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 14
                    // Modify the variable names according to your requirement
                    childModifyEmail14 = serial_control.email;
                    type14 = serial_control.type;
                    isSerialAvailable14 = serialCheck.equals(available);
                    if (isSerialAvailable14) {
                        sl_img_14.setImageResource(R.drawable.available);
                        sl_text_14.setText(R.string.click_to_book);
                    } else {
                        sl_img_14.setImageResource(R.drawable.booked);
                        sl_text_14.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 15
        databaseReference.child("Serial_15").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 15
                    // Modify the variable names according to your requirement
                    childModifyEmail15 = serial_control.email;
                    type15 = serial_control.type;
                    isSerialAvailable15 = serialCheck.equals(available);
                    if (isSerialAvailable15) {
                        sl_img_15.setImageResource(R.drawable.available);
                        sl_text_15.setText(R.string.click_to_book);
                    } else {
                        sl_img_15.setImageResource(R.drawable.booked);
                        sl_text_15.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 16
        databaseReference.child("Serial_16").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 16
                    // Modify the variable names according to your requirement
                    childModifyEmail16 = serial_control.email;
                    type16 = serial_control.type;
                    isSerialAvailable16 = serialCheck.equals(available);
                    if (isSerialAvailable16) {
                        sl_img_16.setImageResource(R.drawable.available);
                        sl_text_16.setText(R.string.click_to_book);
                    } else {
                        sl_img_16.setImageResource(R.drawable.booked);
                        sl_text_16.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 17
        databaseReference.child("Serial_17").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 17
                    // Modify the variable names according to your requirement
                    childModifyEmail17 = serial_control.email;
                    type17 = serial_control.type;
                    isSerialAvailable17 = serialCheck.equals(available);
                    if (isSerialAvailable17) {
                        sl_img_17.setImageResource(R.drawable.available);
                        sl_text_17.setText(R.string.click_to_book);
                    } else {
                        sl_img_17.setImageResource(R.drawable.booked);
                        sl_text_17.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 18
        databaseReference.child("Serial_18").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 18
                    // Modify the variable names according to your requirement
                    childModifyEmail18 = serial_control.email;
                    type18 = serial_control.type;
                    isSerialAvailable18 = serialCheck.equals(available);
                    if (isSerialAvailable18) {
                        sl_img_18.setImageResource(R.drawable.available);
                        sl_text_18.setText(R.string.click_to_book);
                    } else {
                        sl_img_18.setImageResource(R.drawable.booked);
                        sl_text_18.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 19
        databaseReference.child("Serial_19").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 19
                    // Modify the variable names according to your requirement
                    childModifyEmail19 = serial_control.email;
                    type19 = serial_control.type;
                    isSerialAvailable19 = serialCheck.equals(available);
                    if (isSerialAvailable19) {
                        sl_img_19.setImageResource(R.drawable.available);
                        sl_text_19.setText(R.string.click_to_book);
                    } else {
                        sl_img_19.setImageResource(R.drawable.booked);
                        sl_text_19.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 20
        databaseReference.child("Serial_20").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 20
                    // Modify the variable names according to your requirement
                    childModifyEmail20 = serial_control.email;
                    type20 = serial_control.type;
                    isSerialAvailable20 = serialCheck.equals(available);
                    if (isSerialAvailable20) {
                        sl_img_20.setImageResource(R.drawable.available);
                        sl_text_20.setText(R.string.click_to_book);
                    } else {
                        sl_img_20.setImageResource(R.drawable.booked);
                        sl_text_20.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Serial Check 21
        databaseReference.child("Serial_21").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                if (serial_control != null) {
                    String serialCheck = serial_control.position;
                    // Assign values to variables for position 21
                    // Modify the variable names according to your requirement
                    childModifyEmail21 = serial_control.email;
                    type21 = serial_control.type;
                    isSerialAvailable21 = serialCheck.equals(available);
                    if (isSerialAvailable21) {
                        sl_img_21.setImageResource(R.drawable.available);
                        sl_text_21.setText(R.string.click_to_book);
                    } else {
                        sl_img_21.setImageResource(R.drawable.booked);
                        sl_text_21.setText(R.string.already_booked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    //onclick action
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public void onClick(View v) {

        View sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_view, findViewById(R.id.dialog_container));
        setName = sheetView.findViewById(R.id.userFullNameId);
        setMobile = sheetView.findViewById(R.id.userMobileId);
        CircleImageView setImage = sheetView.findViewById(R.id.serialImageViewId);
        Button bookNow = sheetView.findViewById(R.id.bookNowId);
        Button bookCancel = sheetView.findViewById(R.id.cancelBookId);
        TextView textView1 = sheetView.findViewById(R.id.bookTextViewId_1);
        setImage.setOnClickListener(v1 -> startActivity(new Intent(PersonTwoBookPage.this, UploadProfileImage.class)));


        //serial_1
        if (v.getId() == R.id.serialId_1) {
            dialogShowFun(sheetView);
            if (isSerialAvailable1) {
                //set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    //enter name, phone and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable1) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_1").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);


                            //sent push to the admin 1
                            sentPushAdmin1(getName);

                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                //matching the child(modifyEmail) with current user email
                if (childModifyEmail1.equals(modifiedEmail)) {

                    //set user info
                    setName.setText(CurrentUserName);
                    setMobile.setText(CurrentUserMobile);
                    if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                        setImage.setImageResource(R.drawable.image_placeholder);
                    } else {
                        Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                    }

                    textView1.setText(R.string.your_serial);
                    bookCancel.setVisibility(View.VISIBLE);
                    bookNow.setText(R.string.update_button);
                    //cancel booking
                    bookCancel.setOnClickListener(v13 -> {
                        sheetDialog.hide();
                        cancelBooking("Serial_1");

                        //sent push to the admin 1
                        sentCancelPushAdmin1(CurrentUserName);
                    });

                    //update serial
                    TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                    typeText.setText(getString(R.string.selected_type) + ": " + type);

                    customSpinner(sheetView);
                    bookNow.setOnClickListener(v12 -> {
                        String getName = setName.getText().toString();
                        String getMobile = setMobile.getText().toString();
                        boolean registrationSuccessful = bookNowFun(getName, getMobile);
                        //enter name, phone and not null profile image then call this function
                        if (registrationSuccessful) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_1").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);
                        }
                    });
                } else {
                    LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                    LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                    area1.setVisibility(View.GONE);
                    area2.setVisibility(View.VISIBLE);
                    EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                    EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                    CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                    TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                    //booked user info 1
                    databaseRef.child("All Users").child(childModifyEmail1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);
                            if (userInfo != null) {
                                bookedUserName = userInfo.userName;
                                bookedUserMobile = userInfo.userMobile;
                                bookedUserProfileImage = userInfo.profile_pic_link;

                                setBookedUserName.setText(bookedUserName);
                                setBookedUserMobile.setText(bookedUserMobile);
                                if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                    setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                } else {
                                    Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    //type_check_1
                    databaseReference.child("Serial_1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                            if (serial_control != null) {
                                String getBookedType = serial_control.type;
                                userType2.setText(getBookedType);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });


                }
            }
        }
        // Serial 2
        if (v.getId() == R.id.serialId_2) {
            dialogShowFun(sheetView);
            if (isSerialAvailable2) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable2) {
                            sheetDialog.hide();
                            Serial_Control serial_control2 = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_2").setValue(serial_control2);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 2
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child(modifyEmail) with current user email
                    if (childModifyEmail2.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v14 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_2");

                            // Send cancel push to admin 2
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control2 = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_2").setValue(serial_control2);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 2
                        databaseRef.child("All Users").child(childModifyEmail2).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                        // Type check for Serial 2
                        databaseReference.child("Serial_2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 3
        if (v.getId() == R.id.serialId_3) {
            dialogShowFun(sheetView);
            if (isSerialAvailable3) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable3) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_3").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 3
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail3.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_3");

                            // send cancel push to admin 3
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_3").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 3
                        databaseRef.child("All Users").child(childModifyEmail3).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_3
                        databaseReference.child("Serial_3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 4
        if (v.getId() == R.id.serialId_4) {
            dialogShowFun(sheetView);
            if (isSerialAvailable4) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable4) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_4").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 4
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail4.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_4");

                            // send cancel push to admin 4
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_4").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 4
                        databaseRef.child("All Users").child(childModifyEmail4).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_4
                        databaseReference.child("Serial_4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 5
        if (v.getId() == R.id.serialId_5) {
            dialogShowFun(sheetView);
            if (isSerialAvailable5) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable5) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_5").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 5
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail5.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_5");

                            // send cancel push to admin 5
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_5").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 5
                        databaseRef.child("All Users").child(childModifyEmail5).child("User Info").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_5
                        databaseReference.child("Serial_5").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 6
        if (v.getId() == R.id.serialId_6) {
            dialogShowFun(sheetView);
            if (isSerialAvailable6) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable6) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_6").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 6
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail6.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_6");

                            // send cancel push to admin 6
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_6").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 6
                        databaseRef.child("All Users").child(childModifyEmail6).child("User Info").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_6
                        databaseReference.child("Serial_6").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 7
        if (v.getId() == R.id.serialId_7) {
            dialogShowFun(sheetView);
            if (isSerialAvailable7) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable7) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_7").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 7
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail7.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_7");

                            // send cancel push to admin 7
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_7").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 7
                        databaseRef.child("All Users").child(childModifyEmail7).child("User Info").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_7
                        databaseReference.child("Serial_7").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 8
        if (v.getId() == R.id.serialId_8) {
            dialogShowFun(sheetView);
            if (isSerialAvailable8) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable8) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_8").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 8
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail8.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_8");

                            // send cancel push to admin 8
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_8").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 8
                        databaseRef.child("All Users").child(childModifyEmail8).child("User Info").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_8
                        databaseReference.child("Serial_8").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 9
        if (v.getId() == R.id.serialId_9) {
            dialogShowFun(sheetView);
            if (isSerialAvailable9) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable9) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_9").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 9
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail9.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_9");

                            // send cancel push to admin 9
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_9").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 9
                        databaseRef.child("All Users").child(childModifyEmail9).child("User Info").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_9
                        databaseReference.child("Serial_9").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 10
        if (v.getId() == R.id.serialId_10) {
            dialogShowFun(sheetView);
            if (isSerialAvailable10) {
                // set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // enter name, phone, and not null profile image then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable10) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_10").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // send push to admin 10
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // matching the child(modifyEmail) with current user email
                    if (childModifyEmail10.equals(modifiedEmail)) {
                        // set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_10");

                            // send cancel push to admin 10
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // enter name, phone, and not null profile image then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_10").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // booked user info 10
                        databaseRef.child("All Users").child(childModifyEmail10).child("User Info").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;

                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // type_check_10
                        databaseReference.child("Serial_10").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 11
        if (v.getId() == R.id.serialId_11) {
            dialogShowFun(sheetView);
            if (isSerialAvailable11) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable11) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_11").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 11
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail11.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_11");

                            // Send cancel push to admin 11
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_11").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 11
                        databaseRef.child("All Users").child(childModifyEmail11).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 11
                        databaseReference.child("Serial_11").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 12
        if (v.getId() == R.id.serialId_12) {
            dialogShowFun(sheetView);
            if (isSerialAvailable12) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v12 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable12) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_12").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 12
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail12.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v13 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_12");

                            // Send cancel push to admin 12
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v12 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_12").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 12
                        databaseRef.child("All Users").child(childModifyEmail12).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 12
                        databaseReference.child("Serial_12").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 13
        if (v.getId() == R.id.serialId_13) {
            dialogShowFun(sheetView);
            if (isSerialAvailable13) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v13 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable13) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_13").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 13
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail13.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v14 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_13");

                            // Send cancel push to admin 13
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v13 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_13").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 13
                        databaseRef.child("All Users").child(childModifyEmail13).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 13
                        databaseReference.child("Serial_13").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 14
        if (v.getId() == R.id.serialId_14) {
            dialogShowFun(sheetView);
            if (isSerialAvailable14) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v14 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable14) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_14").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 14
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail14.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v15 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_14");

                            // Send cancel push to admin 14
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v14 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_14").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 14
                        databaseRef.child("All Users").child(childModifyEmail14).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 14
                        databaseReference.child("Serial_14").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 15
        if (v.getId() == R.id.serialId_15) {
            dialogShowFun(sheetView);
            if (isSerialAvailable15) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v15 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable15) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_15").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 15
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail15.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v16 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_15");

                            // Send cancel push to admin 15
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v15 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_15").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 15
                        databaseRef.child("All Users").child(childModifyEmail15).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 15
                        databaseReference.child("Serial_15").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 16
        if (v.getId() == R.id.serialId_16) {
            dialogShowFun(sheetView);
            if (isSerialAvailable16) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v16 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable16) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_16").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 16
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail16.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v17 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_16");

                            // Send cancel push to admin 16
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v16 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_16").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 16
                        databaseRef.child("All Users").child(childModifyEmail16).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 16
                        databaseReference.child("Serial_16").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 17
        if (v.getId() == R.id.serialId_17) {
            dialogShowFun(sheetView);
            if (isSerialAvailable17) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v17 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable17) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_17").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 17
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail17.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v18 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_17");

                            // Send cancel push to admin 17
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v17 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_17").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 17
                        databaseRef.child("All Users").child(childModifyEmail17).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 17
                        databaseReference.child("Serial_17").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 18
        if (v.getId() == R.id.serialId_18) {
            dialogShowFun(sheetView);
            if (isSerialAvailable18) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v18 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable18) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_18").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 18
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail18.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v19 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_18");

                            // Send cancel push to admin 18
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v18 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_18").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 18
                        databaseRef.child("All Users").child(childModifyEmail18).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 18
                        databaseReference.child("Serial_18").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 19
        if (v.getId() == R.id.serialId_19) {
            dialogShowFun(sheetView);
            if (isSerialAvailable19) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v19 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable19) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_19").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 19
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail19.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v19 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_19");

                            // Send cancel push to admin 19
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v19 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_19").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 19
                        databaseRef.child("All Users").child(childModifyEmail19).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 19
                        databaseReference.child("Serial_19").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 20
        if (v.getId() == R.id.serialId_20) {
            dialogShowFun(sheetView);
            if (isSerialAvailable20) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v20 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable20) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_20").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 20
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail20.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v20 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_20");

                            // Send cancel push to admin 20
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v20 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_20").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 20
                        databaseRef.child("All Users").child(childModifyEmail20).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 20
                        databaseReference.child("Serial_20").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Serial 21
        if (v.getId() == R.id.serialId_21) {
            dialogShowFun(sheetView);
            if (isSerialAvailable21) {
                // Set user info
                setName.setText(CurrentUserName);
                setMobile.setText(CurrentUserMobile);
                if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                    setImage.setImageResource(R.drawable.image_placeholder);
                } else {
                    Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                }

                customSpinner(sheetView);
                bookNow.setOnClickListener(v21 -> {
                    String getName = setName.getText().toString();
                    String getMobile = setMobile.getText().toString();
                    boolean registrationSuccessful = bookNowFun(getName, getMobile);
                    // Enter name, phone, and non-null profile image, then call this function
                    if (registrationSuccessful) {
                        if (isSerialAvailable21) {
                            sheetDialog.hide();
                            Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                            databaseReference.child("Serial_21").setValue(serial_control);
                            updateNamePhone(modifiedEmail, getName, getMobile);

                            // Send push to admin 21
                            sentPushAdmin1(getName);
                        } else {
                            Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                try {
                    // Matching the child (modifyEmail) with the current user email
                    if (childModifyEmail21.equals(modifiedEmail)) {
                        // Set user info
                        setName.setText(CurrentUserName);
                        setMobile.setText(CurrentUserMobile);
                        if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                            setImage.setImageResource(R.drawable.image_placeholder);
                        } else {
                            Picasso.get().load(CurrentUserProfileImage).placeholder(R.drawable.image_placeholder).into(setImage);
                        }

                        textView1.setText(R.string.your_serial);
                        bookCancel.setVisibility(View.VISIBLE);
                        bookNow.setText(R.string.update_button);
                        // Cancel booking
                        bookCancel.setOnClickListener(v21 -> {
                            sheetDialog.hide();
                            cancelBooking("Serial_21");

                            // Send cancel push to admin 21
                            sentCancelPushAdmin1(CurrentUserName);
                        });

                        // Update serial
                        TextView typeText = sheetView.findViewById(R.id.selectedTypeId);
                        typeText.setText(getString(R.string.selected_type) + ": " + type);

                        customSpinner(sheetView);
                        bookNow.setOnClickListener(v21 -> {
                            String getName = setName.getText().toString();
                            String getMobile = setMobile.getText().toString();
                            boolean registrationSuccessful = bookNowFun(getName, getMobile);
                            // Enter name, phone, and non-null profile image, then call this function
                            if (registrationSuccessful) {
                                sheetDialog.hide();
                                Serial_Control serial_control = new Serial_Control(booked, modifiedEmail, Serial_Control.chooseType);
                                databaseReference.child("Serial_21").setValue(serial_control);
                                updateNamePhone(modifiedEmail, getName, getMobile);
                            }
                        });
                    } else {
                        LinearLayout area1 = sheetView.findViewById(R.id.availableAreaId);
                        LinearLayout area2 = sheetView.findViewById(R.id.bookedAreaId);
                        area1.setVisibility(View.GONE);
                        area2.setVisibility(View.VISIBLE);
                        EditText setBookedUserName = sheetView.findViewById(R.id.userFullNameId2);
                        EditText setBookedUserMobile = sheetView.findViewById(R.id.userMobileId2);
                        CircleImageView setBookedUserImage = sheetView.findViewById(R.id.serialImageViewId2);
                        TextView userType2 = sheetView.findViewById(R.id.typeTextViewId);

                        // Booked user info 21
                        databaseRef.child("All Users").child(childModifyEmail21).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                if (userInfo != null) {
                                    bookedUserName = userInfo.userName;
                                    bookedUserMobile = userInfo.userMobile;
                                    bookedUserProfileImage = userInfo.profile_pic_link;
                                    setBookedUserName.setText(bookedUserName);
                                    setBookedUserMobile.setText(bookedUserMobile);
                                    if (bookedUserProfileImage == null || bookedUserProfileImage.isEmpty()) {
                                        setBookedUserImage.setImageResource(R.drawable.image_placeholder);
                                    } else {
                                        Picasso.get().load(bookedUserProfileImage).placeholder(R.drawable.image_placeholder).into(setBookedUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // Type check for Serial 21
                        databaseReference.child("Serial_21").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Serial_Control serial_control = snapshot.getValue(Serial_Control.class);
                                if (serial_control != null) {
                                    String getBookedType = serial_control.type;
                                    userType2.setText(getBookedType);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }



    }

    private void sentCancelPushAdmin1(String currentUserName) {
        //topic
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
        String title = getString(R.string.serial_book_canceled);
        String message = currentUserName+" "+getString(R.string.just_canceled_book_serial);

        NotificationData notificationData = new NotificationData(title, message);
        PushNotification pushNotification = new PushNotification(notificationData, recipientToken);
        sendNotification(pushNotification);
    }

    //sent push notification to the admin 1
    private void sentPushAdmin1(String getName) {
        //topic
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
        String title = getString(R.string.new_serial_booked);
        String message = getName+" "+getString(R.string.just_booked_a_serial);

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



    // Common method to cancel booking
    private void cancelBooking(String childNode) {
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference(slPerson);
        referenceProfile.child(childNode)
                .updateChildren(new HashMap<String, Object>() {{
                    put("email", "");
                    put("position", available);
                    put("type", "");
                }});
    }

    //custom spinner
    public void customSpinner(View sheetView) {
        Spinner customSpinner = sheetView.findViewById(R.id.spinnerId);
        customList = getCustomList();
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, customList);
        if (customSpinner != null) {
            customSpinner.setAdapter(adapter);
            customSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomSpinnerItem item = (CustomSpinnerItem) parent.getSelectedItem();
                    Serial_Control.chooseType = item.getSpinnerItemName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    //custom spinner
    public ArrayList<CustomSpinnerItem> getCustomList() {
        customList = new ArrayList<>();
        customList.add(new CustomSpinnerItem(getString(R.string.hair_style), R.drawable.hair_style));
        customList.add(new CustomSpinnerItem(getString(R.string.beard_style), R.drawable.beard_style));
        customList.add(new CustomSpinnerItem(getString(R.string.hair_and_beard), R.drawable.hair_beard));
        customList.add(new CustomSpinnerItem(getString(R.string.facial), R.drawable.facial));
        customList.add(new CustomSpinnerItem(getString(R.string.hair_state), R.drawable.hair_state));
        return customList;
    }

    //book now code shorter using this function
    private boolean bookNowFun(String name, String mobile) {
        String mobileRegex = "01[0-9]{9}";
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        Matcher mobileMatcher = mobilePattern.matcher(mobile);

        if (TextUtils.isEmpty(name)) {
            setName.setError(getString(R.string.enter_your_full_name));
            setName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            setMobile.setError(getString(R.string.enter_your_mobile_number));
            setMobile.requestFocus();
            return false;
        } else if (mobile.length() != 11) {
            setMobile.setError(getString(R.string.enter_a_valid_number));
            setMobile.requestFocus();
            return false;
        } else if (!mobileMatcher.find()) {
            setMobile.setError(getString(R.string.enter_a_valid_number));
            setMobile.requestFocus();
            return false;
        } else {
            if (CurrentUserProfileImage == null || CurrentUserProfileImage.isEmpty()) {
                startActivity(new Intent(PersonTwoBookPage.this, UploadProfileImage.class));
            }
            return true;
        }
    }

    //update info - name and mobile
    private void updateNamePhone(String modifiedEmail, String getName, String getMobile) {
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child("All Users")
                .child(modifiedEmail)
                .updateChildren(new HashMap<String, Object>() {{
                    put("userName", getName);
                    put("userMobile", getMobile);
                }});

    }

    //dialog show for short codes
    private void dialogShowFun(View sheetView) {
        sheetDialog = new BottomSheetDialog(this, com.google.android.material.R.style.Theme_Design_BottomSheetDialog);
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
        sheetView.findViewById(R.id.availableAreaId).setVisibility(View.VISIBLE);
        sheetView.findViewById(R.id.bookedAreaId).setVisibility(View.GONE);
    }

    //creating actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu items
        getMenuInflater().inflate(R.menu.menu_layout_sc, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //refresh
        if (item.getItemId() == R.id.menuRefreshId) {
            recreate();
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

}