package com.example.wardrobewizard;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_profile extends AppCompatActivity implements UserCallback {

    private EditText usernameEditText;
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private EditText phoneEditText;
    private EditText birthdayEditText;
    private Button saveChangesButton;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        initializeViews();
        User.getCurrentUser(this);

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (currentUser != null) {
                    currentUser.setUserName(s.toString()); // Update username
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void initializeViews() {
        usernameEditText = findViewById(R.id.editTextText);
        emailTextView = findViewById(R.id.editTextTextEmailAddress2);
        firstNameTextView = findViewById(R.id.editTextText3);
        lastNameTextView = findViewById(R.id.editTextText4);
        phoneEditText = findViewById(R.id.editTextPhone);
        birthdayEditText = findViewById(R.id.editTextDate2);
        saveChangesButton = findViewById(R.id.button3);
    }

    private void saveChanges() {
        String newUsername = usernameEditText.getText().toString();
        String newPhone = phoneEditText.getText().toString().trim(); // Allow empty values
        String newBirthday = birthdayEditText.getText().toString().trim(); // Allow empty values

        if (currentUser != null) {
            currentUser.setUserName(newUsername); // Update username
            currentUser.setPhone(newPhone); // Update phone
            currentUser.setBirthday(newBirthday); // Update birthday

            updateUserProfile(currentUser);
            // Display a success message
            showToast("Changes saved successfully");
            Intent intent = new Intent(edit_profile.this, homepage.class);
            startActivity(intent);
            finish();
        } else {
            // Display an error message or perform any other necessary action
            showToast("Failed to save changes. User not available.");
        }
    }

    private void updateUserProfile(User user) {
        try {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("wardrobe-wizard-8fe58-default-rtdb").child("users").child(user.getUserId());
            userRef.setValue(user);
        } catch (Exception e) {
            // Handle the exception or display an error message
            showToast("Failed to update user profile: " + e.getMessage());
        }
    }

    private void showToast(String message) {
        // Display a toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserReceived(User user) {
        currentUser = user;
       populateInitialValues();
    }
    private void populateInitialValues() {
        if (currentUser != null) {
            usernameEditText.setText(currentUser.getUserName());
            emailTextView.setText(currentUser.getEmail());
            firstNameTextView.setText(currentUser.getFirstName());
            lastNameTextView.setText(currentUser.getLastName());
            phoneEditText.setText(currentUser.getPhone());
            birthdayEditText.setText(currentUser.getBirthday());
        }
    }
}