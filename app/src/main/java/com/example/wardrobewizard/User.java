package com.example.wardrobewizard;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String birthday;

    public User() {
        // Default constructor required for Firebase Realtime Database or Firestore
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
            return null;
        }
    }

    public static User getCurrentUser() {
        String currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            // Implement the logic to retrieve the user object from your data source
            // For example, you can query the Firebase Realtime Database or Firestore
            // using the currentUserId to get the corresponding User object
            // Return the User object or null if the user is not found
            // Example: return MyFirebaseDatabase.getUserById(currentUserId);
            // ...
        }
        return null;
    }
}
}