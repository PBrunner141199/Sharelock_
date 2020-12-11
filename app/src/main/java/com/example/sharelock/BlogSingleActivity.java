package com.example.sharelock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class BlogSingleActivity extends AppCompatActivity {

    private String mPost_key = null;
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private ImageView mBlogSingleImage;
    private TextView mBlogSingleTitle;
    private TextView mBlogSingleDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);
        DatabaseReference postRef= database.getReference("Posts");
        mPost_key=getIntent().getExtras().getString("blog_id");
        mBlogSingleImage= findViewById(R.id.titleImage);
        mBlogSingleDesc= findViewById(R.id.postDescription);
        mBlogSingleTitle = findViewById(R.id.postTitle);

        postRef.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String post_title= (String) snapshot.child("title").getValue();
                String post_desc = (String) snapshot.child("desc").getValue();
                String post_image = (String) snapshot.child("image").getValue();
                String post_uid =  (String) snapshot.child ("uid").getValue();

                mBlogSingleTitle.setText(post_title);
                mBlogSingleDesc.setText(post_desc);
                Picasso.with(BlogSingleActivity.this).load(post_image).into(mBlogSingleImage);


             }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}