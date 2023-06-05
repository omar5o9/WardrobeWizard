package values.wardrobewizard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wardrobewizard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Screen extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        firebaseAuth = FirebaseAuth.getInstance();
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
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
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
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String userId = firebaseUser.getUid();
                                String defaultUsername = email; // Set default username as email

                                // Save default user information to Realtime Database
                                User user = new User(userId, firstName, lastName, email);
                                user.setUserName(defaultUsername);
                                saveUserToDatabase(user);

                                Toast.makeText(Register_Screen.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Register_Screen.this, homepage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Register_Screen.this, "Failed to retrieve user", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register_Screen.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register_Screen.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToDatabase(User user) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.child(user.getUserId()).setValue(user);

        // Update the username in the authentication user profile
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(user.getUserName())
                    .build();

            firebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Username updated successfully in the authentication user profile
                            } else {
                                // Failed to update the username in the authentication user profile
                                Toast.makeText(Register_Screen.this, "Failed to update username in user profile", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register_Screen.this, "Failed to update username: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(Register_Screen.this, "Current user is null", Toast.LENGTH_SHORT).show();
        }
    }
}