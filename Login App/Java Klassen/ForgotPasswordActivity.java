package com.example.sharelock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button submit= findViewById(R.id.submit);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText fpemail= findViewById(R.id.forgotemail);
                String fpemail2=fpemail.getText().toString();

                mAuth.sendPasswordResetEmail(fpemail2);
                Toast.makeText(ForgotPasswordActivity.this, "If u have a verified User Account we sended a Password Reset Mail to the following E-Mail: "+fpemail2,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
            }
        });
    }
}