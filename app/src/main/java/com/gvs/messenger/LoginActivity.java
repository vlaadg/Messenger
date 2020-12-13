package com.gvs.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity" ;
    private AppCompatButton loginButton;
    private AppCompatButton registrationButton;
    private EditText passwordEditText;
    private EditText emailEditText;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login_btn);
        registrationButton = findViewById(R.id.create_account_btn2);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = emailEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();
                Intent regIntent = new Intent(LoginActivity.this , RegistrationActivity.class);
                regIntent.putExtra(RegistrationActivity.EXTRA_MESSAGE, emailText).putExtra(RegistrationActivity.EXTRA_MESSAGE1, passwordText);
                startActivity(regIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Email and password",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                Intent regIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                                startActivity(regIntent);
                            }
                        });
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            updateUI(currentUser);
        }
    }

    public void updateUI(FirebaseUser currentUser){
        Intent chatIntent = new Intent (this, MenuActivity.class);
        chatIntent.putExtra("email", currentUser.getEmail());
        startActivity(chatIntent);

    };

}