package com.example.wardrobewizard;

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

public class edit_profile extends AppCompatActivity {

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
        setInitialValues();

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
        emailTextView = findViewById(R.id.textView15);
        firstNameTextView = findViewById(R.id.textView16);
        lastNameTextView = findViewById(R.id.textView17);
        phoneEditText = findViewById(R.id.editTextPhone);
        birthdayEditText = findViewById(R.id.editTextDate2);
        saveChangesButton = findViewById(R.id.button3);
    }

    private void setInitialValues() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String firstName = extras.getString("firstName");
            String lastName = extras.getString("lastName");
            String email = extras.getString("email");
            String defaultProfilePic = extras.getString("profilePicUrl"); // Get default profile pic URL

            currentUser = new User(firstName, lastName, email);
            currentUser.setProfilePicUrl(defaultProfilePic); // Set default profile pic
            usernameEditText.setText(firstName);
            emailTextView.setText(email);
            firstNameTextView.setText(firstName);
            lastNameTextView.setText(lastName);
        }
    }

    private void saveChanges() {
        String newUsername = usernameEditText.getText().toString();
        String newPhone = phoneEditText.getText().toString().trim(); // Allow empty values
        String newBirthday = birthdayEditText.getText().toString().trim(); // Allow empty values

        if (currentUser != null) {
            currentUser.setUserName(newUsername); // Update username

            // Assuming you have a method to update the user profile in the database or make an API call
            updateUserProfile(currentUser);

            // Display a success message
            showToast("Changes saved successfully");
        } else {
            // Display an error message or perform any other necessary action
            showToast("Failed to save changes. User not available.");
        }
    }

    private void updateUserProfile(User user) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("wardrobe-wizard-8fe58-default-rtdb").child("users").child(user.getUserId());
        userRef.setValue(user);
    }

    private void showToast(String message) {
        // Display a toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}