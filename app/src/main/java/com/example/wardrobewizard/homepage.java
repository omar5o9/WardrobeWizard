package com.example.wardrobewizard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class homepage extends AppCompatActivity {

    private ImageButton profileImageButton;
    private TextView profileNameTextView;
    private ImageButton outfitsImageButton;
    private ImageButton laundryImageButton;
    private ImageButton calendarImageButton;
    private ImageButton statsImageButton;
    private TextView editProfileTextView;
    private String currentUsername;  // Stores the current username
    private static final int REQUEST_CODE_PROFILE_PIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        profileImageButton = findViewById(R.id.profilePic);
        profileNameTextView = findViewById(R.id.profileName);
        outfitsImageButton = findViewById(R.id.outfitsPic);
        laundryImageButton = findViewById(R.id.laundryBasket);
        calendarImageButton = findViewById(R.id.calendarPic);
        statsImageButton = findViewById(R.id.Stats);
        editProfileTextView = findViewById(R.id.editProfile);

        // Set initial username
        try {
            currentUsername = getCurrentUsername();
            profileNameTextView.setText(currentUsername);
            loadProfilePicture();
        } catch (Exception e) {
            Toast.makeText(this, "Error loading username: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.closetButton) {
                    // Handle closet button click
                    // Navigate to the closet page
                    Intent intent = new Intent(homepage.this, closet.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.addClothesButton) {
                    // Handle add clothes button click
                    // Navigate to the add clothes page
                    Intent intent = new Intent(homepage.this, add_clothes.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.settingsButton) {
                    // Handle settings button click
                    // Navigate to the settings page
                    Intent intent = new Intent(homepage.this, settings_screen.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle profile image click
                // Navigate to the ProfilePicActivity to choose or capture a profile picture
                Intent intent = new Intent(homepage.this, ProfilePic.class);
                startActivityForResult(intent, REQUEST_CODE_PROFILE_PIC);
            }
        });

        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage.this, edit_profile.class);
                startActivity(intent);
            }
        });
    }

    private String getCurrentUsername() {
        try {
            User user = User.getCurrentUser();
            if (user != null) {
                String username = user.getUsername();
                profileNameTextView.setText(username);
                return username;
            } else {
                throw new Exception("User object is null.");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error getting current username: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return "";
        }
    }


    private void saveUserToDatabase(User user) {
        try {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (TextUtils.isEmpty(userId)) {
                throw new Exception("Current user ID is null or empty.");
            }
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
            usersRef.child(userId).setValue(user);

            // Store the user's profile picture in Firebase Storage
            // Implement the necessary logic here
        } catch (Exception e) {
            Toast.makeText(this, "Error saving user to database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProfilePicture() {
        try {
            // Assuming you have a reference to the user object in the homepage activity
            User user = User.getCurrentUser();

            // Check if the user object is not null
            if (user != null) {
                // Get the user's profile picture URL
                String profilePictureUrl = user.getProfilePicUrl();

                // Check if the user has a profile picture URL
                if (!TextUtils.isEmpty(profilePictureUrl)) {
                    // Load the user's profile picture using Glide or any other image loading library
                    Glide.with(this)
                            .load(profilePictureUrl)
                            .into(profileImageButton);
                } else {
                    // If the user does not have a profile picture, load the default profile picture
                    profileImageButton.setImageResource(R.drawable.profile_pic);
                }
            } else {
                throw new Exception("User object is null.");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading profile picture: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PROFILE_PIC && resultCode == RESULT_OK) {
            // Check if the result is from the ProfilePicActivity
            // Update the profile picture based on the result
            if (data != null) {
                // Get the image URI from the intent
                String profilePicUri = data.getStringExtra("profilePicUri");
                if (!TextUtils.isEmpty(profilePicUri)) {
                    Glide.with(homepage.this)
                            .load(profilePicUri)
                            .into(profileImageButton);
                } else {
                    Toast.makeText(homepage.this, "Failed to load profile picture", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}