package com.champasalon.chunarughat.navigation_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.R;
import com.champasalon.chunarughat.custom.GlobalIdManager;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class StyleFragment extends Fragment {
    private ImageView hair_img_1, hair_img_2, hair_img_3, hair_img_4;
    private ImageView beard_img_1, beard_img_2, beard_img_3, beard_img_4;
    private ImageView hairBeard_img_1, hairBeard_img_2,hairBeard_img_3, hairBeard_img_4;
    private ImageView facial_img_1, facial_img_2, facial_img_3, facial_img_4;
    String referenceDBNameHair = "Hair_Style";
    String referenceDBNameBeard = "Beard_Style";
    String referenceDBNameHairBeard = "HairBeard_Style";
    String referenceDBNameFacial = "Facial_Style";
    String childStyle_1 = "style_1";
    String childStyle_2 = "style_2";
    String childStyle_3 = "style_3";
    String childStyle_4 = "style_4";

    //for image
    FirebaseDatabase firebaseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_style, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //id's find for hair
        hair_img_1 = view.findViewById(R.id.hairStyleId_1);
        hair_img_2 = view.findViewById(R.id.hairStyleId_2);
        hair_img_3 = view.findViewById(R.id.hairStyleId_3);
        hair_img_4 = view.findViewById(R.id.hairStyleId_4);

        //id's find for beard
        beard_img_1 = view.findViewById(R.id.beardStyleId_1);
        beard_img_2 = view.findViewById(R.id.beardStyleId_2);
        beard_img_3 = view.findViewById(R.id.beardStyleId_3);
        beard_img_4 = view.findViewById(R.id.beardStyleId_4);


        //id's find for HairBeard
        hairBeard_img_1 = view.findViewById(R.id.hairBeardStyleId_1);
        hairBeard_img_2 = view.findViewById(R.id.hairBeardStyleId_2);
        hairBeard_img_3 = view.findViewById(R.id.hairBeardStyleId_3);
        hairBeard_img_4 = view.findViewById(R.id.hairBeardStyleId_4);


        //id's find for beard
        facial_img_1 = view.findViewById(R.id.facialStyleId_1);
        facial_img_2 = view.findViewById(R.id.facialStyleId_2);
        facial_img_3 = view.findViewById(R.id.facialStyleId_3);
        facial_img_4 = view.findViewById(R.id.facialStyleId_4);


        //hair image_1
        firebaseDatabase.getReference(referenceDBNameHair).child(childStyle_1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hair_img_1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //hair image_2
        firebaseDatabase.getReference(referenceDBNameHair).child(childStyle_2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hair_img_2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //hair image_3
        firebaseDatabase.getReference(referenceDBNameHair).child(childStyle_3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hair_img_3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //hair image_4
        firebaseDatabase.getReference(referenceDBNameHair).child(childStyle_4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hair_img_4);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        //beard image_1
        firebaseDatabase.getReference(referenceDBNameBeard).child(childStyle_1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(beard_img_1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //beard image_2
        firebaseDatabase.getReference(referenceDBNameBeard).child(childStyle_2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(beard_img_2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //beard image_3
        firebaseDatabase.getReference(referenceDBNameBeard).child(childStyle_3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(beard_img_3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //beard image_4
        firebaseDatabase.getReference(referenceDBNameBeard).child(childStyle_4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(beard_img_4);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        //hairBeard image_1
        firebaseDatabase.getReference(referenceDBNameHairBeard).child(childStyle_1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hairBeard_img_1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //hairBeard image_2
        firebaseDatabase.getReference(referenceDBNameHairBeard).child(childStyle_2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hairBeard_img_2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //hairBeard image_3
        firebaseDatabase.getReference(referenceDBNameHairBeard).child(childStyle_3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hairBeard_img_3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //hairBeard image_4
        firebaseDatabase.getReference(referenceDBNameHairBeard).child(childStyle_4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(hairBeard_img_4);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        //facial image_1
        firebaseDatabase.getReference(referenceDBNameFacial).child(childStyle_1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(facial_img_1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //facial image_2
        firebaseDatabase.getReference(referenceDBNameFacial).child(childStyle_2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(facial_img_2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //facial image_3
        firebaseDatabase.getReference(referenceDBNameFacial).child(childStyle_3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(facial_img_3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //facial image_4
        firebaseDatabase.getReference(referenceDBNameFacial).child(childStyle_4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.loading_img)
                        .into(facial_img_4);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        //ads
        //style native small ad_1
        AdLoader adLoaderM = new AdLoader.Builder(requireActivity(), GlobalIdManager.nativeAdSmall)
                .forNativeAd(nativeAd -> {
                    // Show the ad.
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template = view.findViewById(R.id.small_1_id);
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

        //style native small ad_2
        AdLoader adLoaderM_2 = new AdLoader.Builder(requireActivity(), GlobalIdManager.nativeAdSmall)
                .forNativeAd(nativeAd -> {
                    // Show the ad.
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template = view.findViewById(R.id.small_2_id);
                    template.setVisibility(View.VISIBLE);
                    TextView adText = view.findViewById(R.id.adTextId_2);
                    adText.setVisibility(View.VISIBLE);
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    }
                }).build();
        adLoaderM_2.loadAd(new AdRequest.Builder().build());

        //style native small ad_3
        AdLoader adLoaderM_3 = new AdLoader.Builder(requireActivity(), GlobalIdManager.nativeAdSmall)
                .forNativeAd(nativeAd -> {
                    // Show the ad.
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template = view.findViewById(R.id.small_3_id);
                    template.setVisibility(View.VISIBLE);
                    TextView adText = view.findViewById(R.id.adTextId_3);
                    adText.setVisibility(View.VISIBLE);
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    }
                }).build();
        adLoaderM_3.loadAd(new AdRequest.Builder().build());

    }
}