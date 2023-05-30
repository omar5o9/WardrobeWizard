package com.example.wardrobewizard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class edit_profile extends AppCompatActivity {

    private EditText usernameEditText;
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private EditText phoneEditText;
    private EditText birthdayEditText;
    private Button saveChangesButton;

    private String currentUsername; // Stores the current username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Initialize views
        usernameEditText = findViewById(R.id.editTextText);
        emailTextView = findViewById(R.id.textView15);
        firstNameTextView = findViewById(R.id.textView16);
        lastNameTextView = findViewById(R.id.textView17);
        phoneEditText = findViewById(R.id.editTextPhone);
        birthdayEditText = findViewById(R.id.editTextDate2);
        saveChangesButton = findViewById(R.id.button3);

        // Set initial values
        currentUsername = getCurrentUserEmail(); // Get the current user's email address
        usernameEditText.setText(currentUsername);
        emailTextView.setText(currentUsername);
        firstNameTextView.setText(getCurrentUserFirstName()); // Get the current user's first name
        lastNameTextView.setText(getCurrentUserLastName()); // Get the current user's last name

        // TextWatcher for usernameEditText to update currentUsername when changed
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentUsername = s.toString();
                updateHomepageUsername(currentUsername);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        // Set OnClickListener for saveChangesButton
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    // Method to save the changes made to the profile
    private void saveChanges() {
        String newUsername = usernameEditText.getText().toString();
        String newPhone = phoneEditText.getText().toString();
        String newBirthday = birthdayEditText.getText().toString();

        // Save the changes to the user profile (e.g., update in the database)

        // Save newUsername to SharedPreferences or update the user's profile document in the database

        // Update the currentUsername with the new username
        currentUsername = newUsername;

        // Update the username displayed on the homepage
        updateHomepageUsername(newUsername);

        // Display a message or perform any other required actions
    }

    // Example methods to retrieve current user information
    private String getCurrentUserEmail() {
        // Retrieve the current user's email address from SharedPreferences or database
        return "example@example.com";
    }

    private String getCurrentUserFirstName() {
        // Retrieve the current user's first name from SharedPreferences or database
        return "John";
    }

    private String getCurrentUserLastName() {
        // Retrieve the current user's last name from SharedPreferences or database
        return "Doe";
    }

    private void updateHomepageUsername(String newUsername) {
        // Update the username displayed on the homepage with the newUsername
        TextView profileNameTextView = findViewById(R.id.profileName);
        profileNameTextView.setText(newUsername);
    }
}