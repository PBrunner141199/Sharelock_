package com.example.sharelockv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    TextView name,email;
    FloatingActionButton setProfilePicture;
    ImageView profilePicture;
    Button zurueck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name);
        email = findViewById(R.id.emailadresse);
        setProfilePicture = findViewById(R.id.setProfilePicture);
        profilePicture = findViewById(R.id.profile_image);
        zurueck = findViewById(R.id.zurueck);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Nutzerdaten");

        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        zurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProfileActivity.this, MenuActivity.class));
            }
        });
        setProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SetProfilePictureActivity.class));
            }
        });

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("ProfileImage").child("imageUrl").getValue()!=null){
                String url = snapshot.child("ProfileImage").child("imageUrl").getValue().toString();

                Picasso.get().load(url).into(profilePicture);}
                else{
                    profilePicture.setImageResource(R.drawable.viewholder3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Huston wir haben ein Problem!", Toast.LENGTH_LONG).show();
            }
        });
    }
}