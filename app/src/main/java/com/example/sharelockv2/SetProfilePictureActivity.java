package com.example.sharelockv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sharelockv2.Helperclasses.Model;
import com.example.sharelockv2.Helperclasses.ProfileHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SetProfilePictureActivity extends AppCompatActivity {

    de.hdodenhof.circleimageview.CircleImageView displayimageView;
    private static final int REQUEST_CODE = 1;
    Button upload;
    ImageButton gallery,camera;
    ImageView goback;
    private Uri imageUri;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference root;
    private StorageReference reference;
    FirebaseUser user;
    private static final String TAG="SetProfileActivity";
    private Bitmap image;
    public boolean Camera;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile_picture);

        goback = findViewById(R.id.ButtonBack);
        displayimageView = findViewById(R.id.displayImageView);
        gallery = findViewById(R.id.galleryBtn);
        camera = findViewById(R.id.cameraBtn);
        upload = findViewById(R.id.upload);
        progressBar = findViewById(R.id.progressBar2);
        root = FirebaseDatabase.getInstance().getReference("Nutzerdaten");
        reference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        progressBar.setVisibility(View.INVISIBLE);
        username = findViewById(R.id.benutzername);
        username.setText(user.getDisplayName());

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (SetProfilePictureActivity.this, ProfileActivity.class));
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPermissions();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }

        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText name1=findViewById(R.id.benutzername);
                String username = name1.getText().toString();
                if (Camera == false){
                    if (imageUri != null){
                        uploadToFirebase(imageUri);
                        if (username != null) {
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(SetProfilePictureActivity.this, "Profil wurde erfolgreich aktualisiert", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SetProfilePictureActivity.this, ProfileActivity.class));
                                        finish();
                                    }

                                }
                            });
                        }
                    }else{
                        if (username != null) {
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(SetProfilePictureActivity.this, "Profil wurde erfolgreich aktualisiert", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SetProfilePictureActivity.this, ProfileActivity.class));
                                        finish();
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(SetProfilePictureActivity.this, "Bitte wählen sie ein Bild aus oder ändern sie ihren Benutzernamen", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(Camera == true){

                    upload();
                    final EditText name2=findViewById(R.id.benutzername);
                    String username2 = name2.getText().toString();
                    if (username2 !="") {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(SetProfilePictureActivity.this, "Profil wurde erfolgreich aktualisiert", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SetProfilePictureActivity.this, ProfileActivity.class));
                                    finish();
                                }

                            }
                        });
                    }else{
                        Toast.makeText(SetProfilePictureActivity.this, "Profil wurde erfolgreich aktualisiert", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SetProfilePictureActivity.this, ProfileActivity.class));
                        finish();
                    }

                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            displayimageView.setImageURI(imageUri);
            Camera = false;

        }
        else if (requestCode ==0 && resultCode== RESULT_OK){
            image = (Bitmap) data.getExtras().get("data");
            displayimageView.setImageBitmap(image);
            Camera = true;
        }
    }
    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        ProfileHelper profilepicture = new ProfileHelper(uri.toString());

                        root.child(user.getUid()).child("ProfileImage").setValue(profilepicture);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SetProfilePictureActivity.this, "Profil wurde erfolgreich aktualisiert", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent( SetProfilePictureActivity.this, ProfileActivity.class));
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SetProfilePictureActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
    private void verifyPermissions(){
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            takePicture();
        }
        else{
            ActivityCompat.requestPermissions(SetProfilePictureActivity.this,
                    permissions,
                    REQUEST_CODE);
        }
    }
    private void openGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent , 2);
    }
    public void takePicture(){
        Intent camera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 0);
    }
    private void upload() {
        final ProgressBar p = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        final String random = UUID.randomUUID().toString();
        StorageReference imageRef = reference.child("image" + random);

        byte[] b = stream.toByteArray();
        imageRef.putBytes(b)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                FirebaseUser user = mAuth.getCurrentUser();

                                Uri imageUrl = uri;

                                    root.child(user.getUid()).child("ProfileImage").child("imageUrl").setValue(imageUrl.toString());



                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Toast.makeText(SetProfilePictureActivity.this, "Leider fehlgeschlagen. Bitte versuchen sie es erneut.", Toast.LENGTH_SHORT).show();
                    }
                });


    }





}
