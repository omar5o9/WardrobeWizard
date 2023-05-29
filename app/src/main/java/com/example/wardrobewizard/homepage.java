package com.example.wardrobewizard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homepage extends AppCompatActivity {

        private ImageButton profileImageButton;
        private TextView profileNameTextView;
        private ImageButton outfitsImageButton;
        private ImageButton laundryImageButton;
        private ImageButton calendarImageButton;
        private ImageButton statsImageButton;
        private ImageButton closetButton;
        private ImageButton addClothesButton;
        private ImageButton settingsButton;
        private TextView editProfileTextView;

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
                    // Perform the action you want, such as changing the profile picture
                    Toast.makeText(homepage.this, "Change Profile Picture", Toast.LENGTH_SHORT).show();
                }
            });

            profileNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle profile name click
                    // Perform the action you want, such as changing the profile name
                    Toast.makeText(homepage.this, "Change Profile Name", Toast.LENGTH_SHORT).show();
                }
            });

            outfitsImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle outfits image click
                    // Navigate to the outfits page
                    Intent intent = new Intent(homepage.this, outifit_creator.class);
                    startActivity(intent);
                }
            });

            laundryImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle laundry image click
                    // Navigate to the laundry page
                    Intent intent = new Intent(homepage.this, laundry_basket.class);
                    startActivity(intent);
                }
            });

            calendarImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle calendar image click
                    // Navigate to the calendar page
                    Intent intent = new Intent(homepage.this, calendar.class);
                    startActivity(intent);
                }
            });

            statsImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle stats image click
                    // Navigate to the stats page
                    Intent intent = new Intent(homepage.this, stats.class);
                    startActivity(intent);
                }
            });

            editProfileTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle edit profile text click
                    // Navigate to the edit profile page
                    Intent intent = new Intent(homepage.this, edit_profile.class);
                    startActivity(intent);
                }
            });
        }
    }
