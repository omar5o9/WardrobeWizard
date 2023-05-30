package com.example.wardrobewizard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Screen extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private TextView textViewForgotPassword;
    private static final int REQUEST_CODE_LOADING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.login_screen);

        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.editProfile);
        textViewForgotPassword = findViewById(R.id.forgotPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (validateCredentials(email, password)) {
                    loginUser(email, password);
                }
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Screen.this, Register_Screen.class);
                startActivity(intent);
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Screen.this, Forgot_Password.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateCredentials(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            return false;
        }
        return true;
    }

    private void loginUser(String email, String password) {
        Intent loadingIntent = new Intent(Login_Screen.this, LoadingScreen.class);
        startActivityForResult(loadingIntent, REQUEST_CODE_LOADING);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        finishActivity(REQUEST_CODE_LOADING);

                        if (task.isSuccessful()) {
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            retrieveUserProfile(userId);
                        } else {
                            Toast.makeText(Login_Screen.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void retrieveUserProfile(String userId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        // Load the user's previously uploaded closet based on the retrieved user profile data
                        // Implement the necessary logic here

                        // Redirect to the homepage
                        Intent homeIntent = new Intent(Login_Screen.this, homepage.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Toast.makeText(Login_Screen.this, "User profile not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login_Screen.this, "User profile not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login_Screen.this, "Failed to retrieve user profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOADING) {
            if (resultCode == RESULT_CANCELED) {
                // Handle loading cancellation if needed
            }
        }
    }
}