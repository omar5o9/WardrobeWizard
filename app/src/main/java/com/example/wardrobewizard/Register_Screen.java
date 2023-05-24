package com.example.wardrobewizard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

    public class Register_Screen extends AppCompatActivity {

        private EditText FirstName;
        private EditText LastName;
        private EditText emailEdit;
        private EditText passwordEdit;
        private EditText confirm_password;
        private Button button;

        private FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.registration_screen);

            firebaseAuth = FirebaseAuth.getInstance();

            FirstName = findViewById(R.id.FirstName);
            LastName = findViewById(R.id.LastName);
            emailEdit = findViewById(R.id.email_Enter);
            passwordEdit = findViewById(R.id.password_Enter);
            confirm_password = findViewById(R.id.confirm_password_enter);
            button = findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String firstName = FirstName.getText().toString().trim();
                    String lastName = LastName.getText().toString().trim();
                    String email = emailEdit.getText().toString().trim();
                    String password = passwordEdit.getText().toString();
                    String confirmPassword = confirm_password.getText().toString();

                    if (validateRegistrationDetails(firstName, lastName, email, password, confirmPassword)) {
                        registerUser(firstName, lastName, email, password);
                    }
                }
            });
        }

        private boolean validateRegistrationDetails(String firstName, String lastName, String email, String password, String confirmPassword) {
            if (TextUtils.isEmpty(firstName)) {
                FirstName.setError("Please enter your first name");
                return false;
            }
            if (TextUtils.isEmpty(lastName)) {
                LastName.setError("Please enter your last name");
                return false;
            }
            if (TextUtils.isEmpty(email)) {
                emailEdit.setError("Please enter your email");
                return false;
            }
            if (TextUtils.isEmpty(password)) {
                passwordEdit.setError("Please enter your password");
                return false;
            }
            if (!password.equals(confirmPassword)) {
                confirm_password.setError("Passwords do not match");
                return false;
            }
            return true;
        }

        private void registerUser(final String firstName, final String lastName, final String email, String password) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Registration successful
                                Toast.makeText(Register_Screen.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                // Save additional user information to database if needed
                                // You can create a user profile document in Firestore or update the user node in the Realtime Database with the first name, last name, etc.

                                // Move to the user's custom homepage or the main homepage
                                //todo: update homePage class
                                Intent intent = new Intent(Register_Screen.this, homepage.class);
                                startActivity(intent);
                                finish(); // Optional: Finish the registration activity to prevent the user from going back
                            } else {
                                // Registration failed
                                Toast.makeText(Register_Screen.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
