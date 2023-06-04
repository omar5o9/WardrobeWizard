package com.example.wardrobewizard;

import android.text.TextUtils;

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
    private String userName;

    public User() {}

    public User(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String firstName, String lastName, String email) {
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
            throw new RuntimeException("User is not authenticated.");
        }
    }

    public static User getCurrentUser() {
        String currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            // Implement the logic to retrieve the user object from the Realtime Database
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
            usersRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            user.setProfilePicUrl(dataSnapshot.child("profilePictureUrl").getValue(String.class));
                        } else {
                            throw new RuntimeException("User data is null, GETCURRENT USER CLASS");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error if needed
                    throw databaseError.toException();
                }
            });
        }
        return null;
    }

    public void setProfilePicUrl(String url) {
        if (url != null) {
            this.profilePicUrl = url;
        } else {
            // Handle the case when the profile picture URL is null
            this.profilePicUrl = "https://storage.googleapis.com/wardrobe-wizard-8fe58.appspot.com/profile%20pic.jpg";
        }
    }

    public String getProfilePicUrl() {

        return profilePicUrl;
    }

    public void setUserName(String userName) {
        if (userName != null) {
            this.userName = userName;
        } else {
            this.userName = "";
        }
    }

    public String getUserName() {
        if (TextUtils.isEmpty(userName)) {
            userName = email;
        }
        return userName;
    }
}