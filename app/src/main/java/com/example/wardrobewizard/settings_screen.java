package com.example.wardrobewizard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    }

    private void initializeViews() {
        // Implement your views initialization here
    }

    private void setBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

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
    }

    private void performSignOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Sign out the user
                FirebaseAuth.getInstance().signOut();

                // Redirect to the login screen
                Intent intent = new Intent(settings_screen.this, Login_Screen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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

    @Override
    public void onBackPressed() {
        // Handle back press to prevent accidental sign out
        // Display the sign-out confirmation dialog
        performSignOut();
    }
}