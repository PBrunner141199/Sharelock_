package com.example.sharelockv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharelockv2.Helperclasses.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Marketplace extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;

    DatabaseReference databaseReference;
    List<Model> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);

        recyclerView = findViewById(R.id.recyclerViewMarketplace);
        TextView title = findViewById(R.id.title);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren() ){
                    Model upload = postSnapshot.getValue(Model.class);
                    uploads.add(upload);
                }
                imageAdapter = new ImageAdapter(Marketplace.this, uploads);
                recyclerView.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Marketplace.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RadioButton nButton = (RadioButton) findViewById(R.id.nachfragebtn);

        nButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    title.setBackgroundColor();
                }
            }
        });
    }
}