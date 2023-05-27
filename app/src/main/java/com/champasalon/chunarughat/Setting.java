package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.CustomGlobalBanner;
import com.champasalon.chunarughat.custom.GlobalIdManager;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;

import java.util.Locale;
import java.util.Objects;

public class Setting extends AppCompatActivity implements View.OnClickListener {
    SwitchCompat switchCompat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //title
        this.setTitle(R.string.setting);

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout language = findViewById(R.id.languageId);
        language.setOnClickListener(this);

      /*Night Mode*/
        switchCompat = findViewById(R.id.switchCompatId);
        SharedPreferences sh = this.getSharedPreferences("night", 0);
        boolean booleanValue = sh.getBoolean("night_mode", false);
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        }
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                switchCompat.setChecked(true);
                SharedPreferences.Editor editor = sh.edit();
                editor.putBoolean("night_mode", true);
                this.recreate();
                editor.apply();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                switchCompat.setChecked(false);
                SharedPreferences.Editor editor = sh.edit();
                editor.putBoolean("night_mode", false);
                editor.apply();
                this.recreate();
            }
        });
        /*End Night Mode*/



        //ads
        //home native medium ad
        AdLoader adLoaderM = new AdLoader.Builder(getApplicationContext(), GlobalIdManager.nativeAd)
                .forNativeAd(nativeAd -> {
                    // Show the ad.
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template = findViewById(R.id.templateHomeMedium);
                    template.setVisibility(View.VISIBLE);
                    TextView adText = findViewById(R.id.adTextId);
                    adText.setVisibility(View.VISIBLE);
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    }
                }).build();
        adLoaderM.loadAd(new AdRequest.Builder().build());

        //for global banner ad
        RelativeLayout bannerContainer = findViewById(R.id.global_banner_container_id);
        CustomGlobalBanner.loadBannerAd(bannerContainer, getApplicationContext());


    }

    //onclick to action
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.languageId) {
            languageSettings();
        }
    }

    //language setting
    private void languageSettings() {
        final String[] languages = {"English", "বাংলা"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(R.string.language_setting);
        mBuilder.setSingleChoiceItems(languages, -1, (dialogInterface, i) -> {

            if (i == 0) {
                setLocalLandLang("");
                restartSelf();
            } else {
                setLocalLandLang("bn");
                restartSelf();
            }
        });
        mBuilder.create();
        mBuilder.show();
    }
    //restart method
    private void restartSelf() {
        recreate();
        this.finish();
        startActivity(new Intent(this, Setting.class));
    }
    //language setting
    private void setLocalLandLang(String language) {
        Locale localeMS = new Locale(language);
        Locale.setDefault(localeMS);

        Configuration configuration = new Configuration();
        configuration.locale = localeMS;
        getResources().updateConfiguration(configuration, this.getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences sharedPreferences = this.getSharedPreferences("SettingLandCal", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_SettingLandCal", language);
        editor.apply();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            recreate();
            startActivity(new Intent(Setting.this, Welcome.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        recreate();
        startActivity(new Intent(Setting.this, Welcome.class));
    }
}