package com.example.sharelockv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MarketplaceActivity extends AppCompatActivity {

    Button newpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        newpost=findViewById(R.id.temporaryntn);

        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarketplaceActivity.this, CreatePostActivity.class));
            }
        });
    }
}