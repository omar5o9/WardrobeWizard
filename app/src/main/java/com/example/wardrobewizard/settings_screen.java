package com.example.wardrobewizard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class settings_screen extends AppCompatActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        initializeViews();
        setBottomNavigationView();

        // Get the current user information from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String firstName = extras.getString("firstName");
            String lastName = extras.getString("lastName");
            String email = extras.getString("email");

            currentUser = new User(firstName, lastName, email);
        }

        // Set click listener for the sign out text
        TextView signOutTextView = findViewById(R.id.signout);
        signOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignOut();
            }
        });
    }

    private void initializeViews() {
        // Implement your views initialization here
    }

    private void setBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomMenu);

        bottomNavigationView.setSelectedItemId(R.id.settingsButton);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.homepage) {
                    // Handle homepage button click
                    // Navigate to the homepage
                    Intent intent = new Intent(settings_screen.this, homepage.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.closetButton) {
                    // Handle closet button click
                    // Navigate to the closet page
                    Intent intent = new Intent(settings_screen.this, closet.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.addClothesButton) {
                    // Handle add clothes button click
                    // Navigate to the add clothes page
                    Intent intent = new Intent(settings_screen.this, add_clothes.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.settingsButton) {
                    // Handle settings button click (current page)
                    // Do nothing
                    return true;
                }
                return false;
            }
        });
        TextView editProfile = findViewById(R.id.editProfileSettings);
        TextView deleteAccount = findViewById(R.id.deleteAccount);
        TextView serviceTerms = findViewById(R.id.service_terms);
        TextView passwordChange = findViewById(R.id.passwordChange);
        TextView privacy = findViewById(R.id.Privacy);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Edit Profile page
                Intent intent = new Intent(settings_screen.this, edit_profile.class);

                // Pass any necessary extras
                if (currentUser != null) {
                    intent.putExtra("firstName", currentUser.getFirstName());
                    intent.putExtra("lastName", currentUser.getLastName());
                    intent.putExtra("email", currentUser.getEmail());
                    startActivity(intent);
                } else {
                    showToast("User data not available. Cannot edit profile.");
                }
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Delete Account page
                Intent intent = new Intent(settings_screen.this, delete_account.class);
                startActivity(intent);
            }
        });

        serviceTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Service Terms page
                Intent intent = new Intent(settings_screen.this, service_terms.class);
                startActivity(intent);
            }
        });

        passwordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Password Change page
                Intent intent = new Intent(settings_screen.this, Change_Password.class);
                startActivity(intent);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Privacy Policy page
                Intent intent = new Intent(settings_screen.this, privacy_policy.class);
                startActivity(intent);
            }
        });
    }

    private void performSignOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    // Sign out the user
                    FirebaseAuth.getInstance().signOut();

                    // Redirect to the login screen
                    Intent intent = new Intent(settings_screen.this, Login_Screen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    // Handle the exception or display an error message
                    showToast("Failed to sign out. Error: " + e.getMessage());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showToast(String message) {
        // Display a toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
