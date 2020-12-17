package com.example.sharelockv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {
    ImageButton marketplace;
    ImageButton profile;
    TextView welcomeMenue;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menue);
        marketplace= findViewById(R.id.sharelockMenue);
        profile = findViewById(R.id.profileMenue);
        welcomeMenue = findViewById(R.id.welcomeMenue);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        logout = findViewById(R.id.logout2);

        welcomeMenue.setText("Hallo "+user.getDisplayName()+"! Schön, dass du da bist");


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent (MenuActivity.this, MainActivity.class));
            }
        });

        marketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MarketplaceActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            }
        });
    }
}