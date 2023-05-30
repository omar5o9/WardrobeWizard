package com.example.wardrobewizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settings_screen extends AppCompatActivity {

    private User currentUser; // Placeholder for the current user, you need to replace it with your own implementation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        TextView editProfileText = findViewById(R.id.EditText);
        TextView signOutText = findViewById(R.id.signout);
        TextView changePasswordText = findViewById(R.id.passwordChange);
        TextView deleteAccountText = findViewById(R.id.deleteAccount);
        TextView privacyPolicyText = findViewById(R.id.Privacy);
        TextView serviceTermsText = findViewById(R.id.service_terms);

        editProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the current user's information to the edit profile activity
                Intent intent = new Intent(settings_screen.this, edit_profile.class);
                intent.putExtra("firstName", currentUser.getFirstName());
                intent.putExtra("lastName", currentUser.getLastName());
                intent.putExtra("email", currentUser.getEmail());
                startActivity(intent);
            }
        });

        signOutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform sign out
                performSignOut();
            }
        });

        changePasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Change Password page
                startActivity(new Intent(settings_screen.this, Change_Password.class));
            }
        });

        deleteAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Delete Account page
                startActivity(new Intent(settings_screen.this, delete_account.class));
            }
        });

        privacyPolicyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Privacy Policy page
                startActivity(new Intent(settings_screen.this, privacy_policy.class));
            }
        });

        serviceTermsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Service Terms page
                startActivity(new Intent(settings_screen.this, service_terms.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.closetButton) {
                    // Handle closet button click
                    // Navigate to the closet page
                    startActivity(new Intent(settings_screen.this, closet.class));
                    return true;
                } else if (itemId == R.id.addClothesButton) {
                    // Handle add clothes button click
                    // Navigate to the add clothes page
                    startActivity(new Intent(settings_screen.this, add_clothes.class));
                    return true;
                } else if (itemId == R.id.settingsButton) {
                    // Handle settings button click
                    // Do nothing, already on the settings screen
                    return true;
                }
                return false;
            }
        });
    }

    private void performSignOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(settings_screen.this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the sign out operation (Implement your own sign out logic here)
                // ...

                // Redirect the user to the login screen
                startActivity(new Intent(settings_screen.this, Login_Screen.class));
                finish(); // Optional: Finish the current activity
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, dismiss the dialog
            }
        });
        builder.create().show();
    }
}
