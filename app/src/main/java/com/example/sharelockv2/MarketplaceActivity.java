package com.example.sharelockv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharelockv2.Helperclasses.ImageAdapter;
import com.example.sharelockv2.Helperclasses.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceActivity extends AppCompatActivity {

    FloatingActionButton newpost;
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    ImageView  goback;
    DatabaseReference databaseReference;
    List<Model> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        newpost=findViewById(R.id.temporarybtn);

        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarketplaceActivity.this, CreatePostActivity.class));
            }
        });
        recyclerView = findViewById(R.id.recyclerViewMarketplace);
        TextView title = findViewById(R.id.title);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();
        goback = findViewById(R.id.backbutton);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarketplaceActivity.this, MenuActivity.class));
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren() ){
                    Model upload = postSnapshot.getValue(Model.class);
                    uploads.add(upload);
                }
                imageAdapter = new ImageAdapter(MarketplaceActivity.this, uploads);
                recyclerView.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MarketplaceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}