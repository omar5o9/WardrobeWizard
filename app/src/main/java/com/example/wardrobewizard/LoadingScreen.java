package com.example.wardrobewizard;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingScreen extends AppCompatActivity {

    private static final int LOADING_DURATION = 3000; // 3 seconds
    private LoadUserDataTask loadUserDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        // Simulate loading process in the background
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(LOADING_DURATION);

                    // Start the AsyncTask to load user data
                    loadUserDataTask = new LoadUserDataTask();
                    loadUserDataTask.execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void loadHomepage(User user) {
        if (user != null) {
            // User information is available, transition to the homepage
            Intent intent = new Intent(LoadingScreen.this, homepage.class);
            startActivity(intent);
            finish();
        } else {
            // User information is not available, handle the case accordingly
            // For example, redirect to the login or registration screen
            Intent intent = new Intent(LoadingScreen.this, Login_Screen.class);
            startActivity(intent);
            finish();
        }
    }

    private class LoadUserDataTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... voids) {
            try {

                // Get the currently authenticated user
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    // User is authenticated, fetch user information
                    String displayName = firebaseUser.getDisplayName();
                    String email = firebaseUser.getEmail();
                    Uri photoUrl = firebaseUser.getPhotoUrl();

                    // Create a User object with the fetched information
                    User user = new User();
                    user.setUserName(displayName);
                    user.setEmail(email);
                    user.setProfilePicUrl(photoUrl != null ? photoUrl.toString() : null);

                    return user;
                } else {
                    // User is not authenticated, return null or handle the case accordingly
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            loadHomepage(user);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the LoadUserDataTask if it is still running
        if (loadUserDataTask != null && loadUserDataTask.getStatus() == AsyncTask.Status.RUNNING) {
            loadUserDataTask.cancel(true);
        }
    }
}

