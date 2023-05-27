package com.champasalon.chunarughat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.champasalon.chunarughat.custom.StyleNamePrice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Style_Beard extends AppCompatActivity {
    private ImageView style_img_1, style_img_2, style_img_3, style_img_4;
    //for image
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    //for name
    DatabaseReference databaseReference;
    //person booking page
    String childStyle_1 = "style_1";
    String childStyle_2 = "style_2";
    String childStyle_3 = "style_3";
    String childStyle_4 = "style_4";
    String childTypeStyle_1 = "hair_style_1";
    String childTypeStyle_2 = "hair_style_2";
    String childTypeStyle_3 = "hair_style_3";
    String childTypeStyle_4 = "hair_style_4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_beard);

        //set title
        setTitle(R.string.beard_style);
        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //get from database area
        String referenceDBName = "Beard_Style";
        //style 1
        //get image_1
        style_img_1 = findViewById(R.id.style_img_1_id);
        firebaseDatabase.getReference(referenceDBName).child(childStyle_1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(style_img_1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get name & price 1
        databaseReference = firebaseDatabase.getReference(referenceDBName);
        databaseReference.child(childTypeStyle_1).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StyleNamePrice styleNamePrice = snapshot.getValue(StyleNamePrice.class);
                if (styleNamePrice != null) {
                    String nameStyle = styleNamePrice.getStyleName();
                    String priceStyle = styleNamePrice.getStylePrice();
                    TextView name = findViewById(R.id.styleNameId_1);
                    TextView price = findViewById(R.id.stylePriceId_1);
                    name.setText(getString(R.string.name)+": " + nameStyle);
                    price.setText("৳" + priceStyle);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.sw_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //style 2
        //get image
        style_img_2 = findViewById(R.id.style_img_2_id);
        firebaseDatabase.getReference(referenceDBName).child(childStyle_2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(style_img_2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get name & price
        databaseReference = firebaseDatabase.getReference(referenceDBName);
        databaseReference.child(childTypeStyle_2).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StyleNamePrice styleNamePrice = snapshot.getValue(StyleNamePrice.class);
                if (styleNamePrice != null) {
                    String nameStyle = styleNamePrice.getStyleName();
                    String priceStyle = styleNamePrice.getStylePrice();
                    TextView name = findViewById(R.id.styleNameId_2);
                    TextView price = findViewById(R.id.stylePriceId_2);
                    name.setText(getString(R.string.name)+": " + nameStyle);
                    price.setText("৳" + priceStyle);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.sw_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //style 3
        //get image
        style_img_3 = findViewById(R.id.style_img_3_id);
        firebaseDatabase.getReference(referenceDBName).child(childStyle_3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(style_img_3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get name & price
        databaseReference = firebaseDatabase.getReference(referenceDBName);
        databaseReference.child(childTypeStyle_3).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StyleNamePrice styleNamePrice = snapshot.getValue(StyleNamePrice.class);
                if (styleNamePrice != null) {
                    String nameStyle = styleNamePrice.getStyleName();
                    String priceStyle = styleNamePrice.getStylePrice();
                    TextView name = findViewById(R.id.styleNameId_3);
                    TextView price = findViewById(R.id.stylePriceId_3);
                    name.setText(getString(R.string.name)+": " + nameStyle);
                    price.setText("৳" + priceStyle);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.sw_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //style 4
        //get image
        style_img_4 = findViewById(R.id.style_img_4_id);
        firebaseDatabase.getReference(referenceDBName).child(childStyle_4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .into(style_img_4);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get name & price
        databaseReference = firebaseDatabase.getReference(referenceDBName);
        databaseReference.child(childTypeStyle_4).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StyleNamePrice styleNamePrice = snapshot.getValue(StyleNamePrice.class);
                if (styleNamePrice != null) {
                    String nameStyle = styleNamePrice.getStyleName();
                    String priceStyle = styleNamePrice.getStylePrice();
                    TextView name = findViewById(R.id.styleNameId_4);
                    TextView price = findViewById(R.id.stylePriceId_4);
                    name.setText(getString(R.string.name)+": " + nameStyle);
                    price.setText("৳" + priceStyle);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.sw_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

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