package com.champasalon.chunarughat.custom;

import android.content.Context;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class CustomGlobalBanner {
    public static void loadBannerAd(RelativeLayout relativeLayout, Context context){

        AdView adView = new AdView(context);
        adView.setAdUnitId(GlobalIdManager.bannerAd);
        relativeLayout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.loadAd(adRequest);

    }
}
