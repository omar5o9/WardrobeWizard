package com.example.wardrobewizard;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String birthday;
    private String profilePicUrl;

    private String username;

    public User() {
        // Default constructor required for Firebase Realtime Database or Firestore
    }

    public User(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public static String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            // Handle the case when the user is not authenticated
            return null;
        }
    }

    public static User getCurrentUser() {
        String currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            // Implement the logic to retrieve the user object from the Realtime Database
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
            usersRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        // Display the user information or update the UI
                        // Example: updateUI(user);
                        // ...
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error if needed
                }
            });
        }
        return null;
    }

    public void setProfilePicUrl(String url) {
        // Fetch the profile picture from Firebase Storage or Realtime Database
        // Implement the necessary logic here to retrieve and save the profile picture
        this.profilePicUrl = url;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}