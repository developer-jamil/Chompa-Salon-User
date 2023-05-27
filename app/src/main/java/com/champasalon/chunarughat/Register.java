package com.champasalon.chunarughat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText fullName, email, dob, mobile, newPass, confirmPass;
    private ProgressBar progressBar;
    private String selectedGender;
    private Calendar selectedDate;
    private static final String TAG = "Register";
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

         //initialize all
        fullName = findViewById(R.id.fullNameEditTextId);
        email = findViewById(R.id.emailEditTextId);
        dob = findViewById(R.id.birthDateEditTextId);
        mobile = findViewById(R.id.mobileEditTextId);
        newPass = findViewById(R.id.newPassEditTextId);
        confirmPass = findViewById(R.id.confirmPassEditTextId);

        progressBar = findViewById(R.id.progressBarId);

        //radio group - select gender
        RadioGroup radioGroup = findViewById(R.id.radioGroupId);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = radioGroup.findViewById(checkedId);
            selectedGender = radioButton.getText().toString();
        });

        Button register = findViewById(R.id.registerButtonId);
        register.setOnClickListener(this);

        //selected date
        selectedDate = Calendar.getInstance();
        dob.setOnClickListener(this);
        updateBirthDateEditText();



    }

    //onClick event
    @Override
    public void onClick(View v) {

        //date of birth
        if (v.getId() == R.id.birthDateEditTextId) {
            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // update selected date
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        // update birth date EditText
                        updateBirthDateEditText();
                    },
                    selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH),
                    selectedDate.get(Calendar.DAY_OF_MONTH));
            // show date picker dialog
            datePickerDialog.show();
        }

        //register user
        if (v.getId() == R.id.registerButtonId) {

            // get the selected gender value from the radio group
            RadioGroup radioGroup = findViewById(R.id.radioGroupId);
            int selectedGenderId = radioGroup.getCheckedRadioButtonId();
            if (selectedGenderId == -1) {
                // no RadioButton is selected, display an error message
                Toast.makeText(getApplicationContext(), getString(R.string.select_a_gender), Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
            selectedGender = selectedGenderRadioButton.getText().toString();


            //get all entered data
            String getFullName = fullName.getText().toString().trim();
            String getEmail = email.getText().toString().trim();
            String getDob = dob.getText().toString().trim();
            String getMobile = mobile.getText().toString().trim();
            String getNewPass = newPass.getText().toString().trim();
            String getConfirmedPass = confirmPass.getText().toString().trim();

            //validate mobile number using matcher and pattern
            String mobileRegex = "01[0-9]{9}";
            //First two digits must be 01 and rest 9 digits can be any digit.
            Pattern mobilePattern = Pattern.compile(mobileRegex);
            Matcher mobileMatcher = mobilePattern.matcher(getMobile);

            // perform registration logic here
            if (TextUtils.isEmpty(getFullName)) {
                fullName.setError(getString(R.string.enter_your_full_name));
                fullName.requestFocus();
            } else if (TextUtils.isEmpty(getEmail)) {
                email.setError(getString(R.string.enter_email));
                email.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()) {
                email.setError(getString(R.string.enter_valid_email));
                email.requestFocus();
            } else if (TextUtils.isEmpty(getDob)) {
                dob.setError(getString(R.string.date_of_birth_req));
                dob.requestFocus();
            } else if (TextUtils.isEmpty(getMobile)) {
                mobile.setError(getString(R.string.enter_your_mobile_number));
                mobile.requestFocus();
            } else if (getMobile.length() != 11) {
                mobile.setError(getString(R.string.enter_a_valid_number));
                mobile.requestFocus();
            } else if (!mobileMatcher.find()) {
                mobile.setError(getString(R.string.enter_a_valid_number));
                mobile.requestFocus();
            } else if (TextUtils.isEmpty(getNewPass)) {
                newPass.setError(getString(R.string.create_a_new_password));
                newPass.requestFocus();
            } else if (newPass.length() < 6) {
                newPass.setError(getString(R.string.pass_to_weak));
                newPass.requestFocus();
            } else if (TextUtils.isEmpty(getConfirmedPass)) {
                confirmPass.setError(getString(R.string.confirm_password));
                confirmPass.requestFocus();
            } else if (!getNewPass.equals(getConfirmedPass)) {
                confirmPass.setError(getString(R.string.not_matching_pass));
                confirmPass.requestFocus();
            } else {
                register(getFullName, getEmail, getDob, selectedGender, getMobile, getNewPass);
            }
        }


    }

    //chose dob from user - save previous chosen dob
    private void updateBirthDateEditText() {
        // format selected date as "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String formattedDate = sdf.format(selectedDate.getTime());
        // update birth date EditText
        dob.setText(formattedDate);
    }

    //register method
    private void register(String getFullName, String getEmail, String getDob, String selectedGender, String getMobile, String getNewPass) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(getEmail, getNewPass).addOnCompleteListener(Register.this, task -> {
            //if sign up successfully done
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                //access user data
                //Enter user data into the firebase realtime database
                String userProfileBudgetStatus = "Regular";
                String profile_pic_link = "";
                String userCode = "user";

                // Retrieve the FCM token
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(tokenTask -> {
                            if (tokenTask.isSuccessful()) {
                                String userToken = tokenTask.getResult();

                                UserInfo userInfo = new UserInfo(getFullName, getEmail, getMobile, selectedGender, getDob, userProfileBudgetStatus, profile_pic_link, userCode, userToken);

                                String modifiedEmail = Objects.requireNonNull(getEmail).replace(".", "-");
                                // Check if the user already exists in the Realtime Database
                                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                                referenceProfile.child("All Users").child(modifiedEmail).setValue(userInfo).addOnCompleteListener(task1 -> {
                                    //send verification email
                                    assert firebaseUser != null;
                                    firebaseUser.sendEmailVerification();
                                    Toast.makeText(Register.this, getString(R.string.successfully_registered), Toast.LENGTH_SHORT).show();
                                    //open user profile after successfully registered
                                    Intent intent = new Intent(Register.this, Welcome.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                });
                            } else {
                                Toast.makeText(Register.this, Objects.requireNonNull(tokenTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                progressBar.setVisibility(View.GONE);
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthWeakPasswordException e) {
                    newPass.setError(getString(R.string.weak_pass));
                    newPass.requestFocus();
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    email.setError(getString(R.string.invalid_pass));
                    email.requestFocus();
                } catch (FirebaseAuthUserCollisionException e) {
                    email.setError(getString(R.string.already_registered));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}


