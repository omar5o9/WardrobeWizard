package com.example.wardrobewizard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Screen extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private static final int REQUEST_CODE_LOADING = 1;

    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        firstNameEditText = findViewById(R.id.FirstName);
        lastNameEditText = findViewById(R.id.LastName);
        emailEditText = findViewById(R.id.email_Enter);
        passwordEditText = findViewById(R.id.password_Enter);
        confirmPasswordEditText = findViewById(R.id.confirm_password_enter);
        registerButton = findViewById(R.id.button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (validateRegistrationDetails(firstName, lastName, email, password, confirmPassword)) {
                    registerUser(firstName, lastName, email, password);
                }
            }
        });
    }

    private boolean validateRegistrationDetails(String firstName, String lastName, String email, String password, String confirmPassword) {
        // Validation code here...

        return true;
    }

    private void registerUser(final String firstName, final String lastName, final String email, String password) {
        Intent loadingIntent = new Intent(Register_Screen.this, LoadingScreen.class);
        startActivityForResult(loadingIntent, REQUEST_CODE_LOADING);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        finishActivity(REQUEST_CODE_LOADING);

                        if (task.isSuccessful()) {
                            Toast.makeText(Register_Screen.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            User user = new User(firstName, lastName, email);
                            saveUserToDatabase(user);

                            Intent intent = new Intent(Register_Screen.this, homepage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register_Screen.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToDatabase(User user) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.child(userId).setValue(user);
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