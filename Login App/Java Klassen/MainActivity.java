package com.example.sharelock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView email, password, register;
    private Button but1;
    private FirebaseAuth mAuth;
    private EditText editemail, editpassword;
    final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password=(TextView)findViewById(R.id.textview3);
        email= (TextView)findViewById(R.id.eintext) ;
        but1= (Button)findViewById(R.id.registerButton);
        register= (TextView)findViewById(R.id.registerLink);
        final EditText editemail = findViewById(R.id.editemail);
        final EditText editpassword = findViewById(R.id.edit_password2);

        TextView forgotpw= (TextView)findViewById(R.id.forgotpassword);
        mAuth = FirebaseAuth.getInstance();

        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editemail.getText().toString().equals("") || editpassword.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    login(editemail.getText().toString(), editpassword.getText().toString());
                }
            }
        });
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
                                Toast.makeText(MainActivity.this, "Authentication Success! "+ user.getEmail(),
                                        Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));

                                 finish();
                            }
                            else{
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Pleas Verfifiy your E-mail",
                                        Toast.LENGTH_SHORT).show();
                            }



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user !=null){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            finish();
        }
    }

}