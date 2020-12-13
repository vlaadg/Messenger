package com.gvs.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import Model.User;


public class RegistrationActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_MESSAGE1 = "message";
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private AppCompatButton registerButton;
    private AppCompatButton cancelButton;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USER = "user";
    private static final String TAG = "RegisterActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        // Интент передающий значение с полей EditText в ActivityLogin
        Intent intent = getIntent();
        String messageText1 = intent.getStringExtra(EXTRA_MESSAGE);
        String messageText2 = intent.getStringExtra(EXTRA_MESSAGE1);
        EditText emailText = findViewById(R.id.mail_text);
        EditText passwordText = findViewById(R.id.password_text);
        emailText.setText(messageText1);
        passwordText.setText(messageText2);

        //
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.create_account_btn1);
        cancelButton = findViewById(R.id.cancel_btn);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Email and password",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String username = usernameEditText.getText().toString();
                user = new User(username, email, password);
                registerUser(email,password);
            }
        });
    }

    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    public void updateUI(FirebaseUser currentUser){
        String keyId= mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}