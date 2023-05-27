package com.champasalon.chunarughat.navigation_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.R;
import com.champasalon.chunarughat.custom.CustomLinkManage;
import com.champasalon.chunarughat.custom.GlobalIdManager;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(requireActivity(), "Coming Soon...", Toast.LENGTH_SHORT).show();


       //booking native medium ad
        AdLoader adLoaderM = new AdLoader.Builder(requireActivity(), GlobalIdManager.nativeAd)
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
                    }
                }).build();
        adLoaderM.loadAd(new AdRequest.Builder().build());


    }
}