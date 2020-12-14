package com.example.sharelockv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private TextView logintext,forgotpw;
    final String TAG="LoginActivtiy";
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginbtn=(Button)findViewById(R.id.login);
        forgotpw=(TextView)findViewById(R.id.forgotpw);
        final EditText editemail = (EditText)findViewById(R.id.editEmail);
        final EditText editpassword = (EditText)findViewById(R.id.editPassword);
        mAuth= FirebaseAuth.getInstance();

        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editemail.getText().toString().equals("") || editpassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    login(editemail.getText().toString(), editpassword.getText().toString());
                }
            }
        };
        loginbtn.setOnClickListener(btnListener);

        /*loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editemail.getText().toString().equals("") || editpassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    login(editemail.getText().toString(), editpassword.getText().toString());
                }
            }
        });*/

    }
    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()){
                                Toast.makeText(LoginActivity.this, "Authentication Success! "+ user.getEmail(),
                                        Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(LoginActivity.this, MenuActivity.class));

                                finish();
                            }
                            else{
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Pleas Verfifiy your E-mail",
                                        Toast.LENGTH_SHORT).show();
                            }



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            finish();
        }
    }
}