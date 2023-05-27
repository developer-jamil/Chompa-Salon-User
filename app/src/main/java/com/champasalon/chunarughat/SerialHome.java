package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.CustomAdapter;
import com.champasalon.chunarughat.custom.CustomGlobalBanner;
import com.champasalon.chunarughat.custom.GlobalIdManager;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SerialHome extends AppCompatActivity implements View.OnClickListener {
    private ImageView personImage1, personImage2, personImage3, personImage4;
    private TextView namePerson1, namePerson2, namePerson3, namePerson4;
    private String name1, name2, name3, name4;

    //for image
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    //for name
    DatabaseReference databaseReference;

    //person booking page
    CardView person_1, person_2, person_3, person_4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serial_home);

        //title
        this.setTitle(R.string.serialHomeTitle);
        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        namePerson1 = findViewById(R.id.person1NameTextViewId);
        namePerson2 = findViewById(R.id.person2NameTextViewId);
        namePerson3 = findViewById(R.id.person3NameTextViewId);
        namePerson4 = findViewById(R.id.person4NameTextViewId);


        personImage1 = findViewById(R.id.person1ImageView1Id);
        personImage2 = findViewById(R.id.person2ImageView1Id);
        personImage3 = findViewById(R.id.person3ImageView1Id);
        personImage4 = findViewById(R.id.person4ImageView1Id);


        //set name
        // person 1
        databaseReference = firebaseDatabase.getReference("Craft Man");
        databaseReference.child("Person_Name_1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                if (customAdapter != null) {
                    name1 = customAdapter.getNamePerson();
                    namePerson1.setText(name1);
                } else {
                    Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // person 2
        databaseReference.child("Person_Name_2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                if (customAdapter != null) {
                    name2 = customAdapter.getNamePerson();
                    namePerson2.setText(name2);
                } else {
                    Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // person 3
        databaseReference.child("Person_Name_3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                if (customAdapter != null) {
                    name3 = customAdapter.getNamePerson();
                    namePerson3.setText(name3);
                } else {
                    Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // person 4
        databaseReference.child("Person_Name_4").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                if (customAdapter != null) {
                    name4 = customAdapter.getNamePerson();
                    namePerson4.setText(name4);
                } else {
                    Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //get image_1
        firebaseDatabase.getReference("Craft Man").child("person_1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(personImage1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get image_2
        firebaseDatabase.getReference("Craft Man").child("person_2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(personImage2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get image_3
        firebaseDatabase.getReference("Craft Man").child("person_3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(personImage3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get image_4
        firebaseDatabase.getReference("Craft Man").child("person_4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(personImage4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //person 1
        person_1 = findViewById(R.id.nupurDaBookPageId);
        person_1.setOnClickListener(this);
        //person 2
        person_2 = findViewById(R.id.person2BookPageId);
        person_2.setOnClickListener(this);
        //person 3
        person_3 = findViewById(R.id.person3BookPageId);
        person_3.setOnClickListener(this);
        //person 4
        person_4 = findViewById(R.id.person4BookPageId);
        person_4.setOnClickListener(this);

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


        //global banner ads
        //for global banner ad
        RelativeLayout bannerContainer = findViewById(R.id.global_banner_container_id);
        CustomGlobalBanner.loadBannerAd(bannerContainer, getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        //person 1
        if (v.getId() == R.id.nupurDaBookPageId) {
            databaseReference.child("Person_Name_1").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                    if (customAdapter != null) {
                        name1 = customAdapter.getNamePerson();
                        Intent intent = new Intent(SerialHome.this, PersonOneBookPage.class);
                        intent.putExtra("person_1", name1);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        //person 2
        if (v.getId() == R.id.person2BookPageId) {
            databaseReference.child("Person_Name_2").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                    if (customAdapter != null) {
                        name2 = customAdapter.getNamePerson();
                        Intent intent = new Intent(SerialHome.this, PersonTwoBookPage.class);
                        intent.putExtra("person_2", name2);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        //person 3
        if (v.getId() == R.id.person3BookPageId) {
            databaseReference.child("Person_Name_3").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                    if (customAdapter != null) {
                        name3 = customAdapter.getNamePerson();
                        Intent intent = new Intent(SerialHome.this, PersonThreeBookPage.class);
                        intent.putExtra("person_3", name3);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        //person 4
        if (v.getId() == R.id.person4BookPageId) {
            databaseReference.child("Person_Name_4").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CustomAdapter customAdapter = snapshot.getValue(CustomAdapter.class);
                    if (customAdapter != null) {
                        name4 = customAdapter.getNamePerson();
                        Intent intent = new Intent(SerialHome.this, PersonFourBookPage.class);
                        intent.putExtra("person_4", name4);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SerialHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
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


}