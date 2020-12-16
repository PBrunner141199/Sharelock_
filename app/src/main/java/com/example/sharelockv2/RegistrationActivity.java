package com.example.sharelockv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharelockv2.Helperclasses.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference("Nutzerdaten");




    final String TAG="RegistrationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        Button register=findViewById(R.id.registration2);
        final EditText email2= findViewById(R.id.email3);
        final EditText password= findViewById(R.id.password3);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email2.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(RegistrationActivity.this, "Registration failed.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                }
                else {
                    signUp(email2.getText().toString(), password.getText().toString());



                }
            }
        });
    }






    public void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            })
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });


                            String email= user.getEmail();
                            String id= user.getUid();
                            final EditText name1=findViewById(R.id.username4);
                            String username = name1.getText().toString();


                            UserHelperClass helperClass = new UserHelperClass(email,username, password);
                            myRef.child(id).setValue(helperClass);


                            Toast.makeText(RegistrationActivity.this, "Registration successful! Please Verify your E-Mail Adress "+ user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        finish();
                                        mAuth.signOut();
                                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                    }

                                }
                            });




                        }
                    }
                });
    }}


