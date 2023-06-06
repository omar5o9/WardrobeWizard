package wardrobewizard;

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
        if(this.phone != null)
        return phone;
        else
            this.phone = "";
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        if(this.birthday != null)
        return birthday;
        else {
            this.birthday = "";
        }
        return this.birthday;
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

    public static void getCurrentUser(final UserCallback callback) {
        String currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
            usersRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            user.setProfilePicUrl(dataSnapshot.child("profilePicUrl").getValue(String.class));
                            callback.onUserReceived(user); // Pass the user object to the callback
                        } else {
                            throw new RuntimeException("User data is null, getCurrentUser");
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
    }

    public void setProfilePicUrl(String url) {
        if (url != null) {
            this.profilePicUrl = url;
        } else {
            // Handle the case when the profile picture URL is null
            this.profilePicUrl = "https://firebasestorage.googleapis.com/v0/b/wardrobe-wizard-8fe58.appspot.com/o/funnypic.jpg?alt=media&token=5e592042-bd91-42d0-9ff3-c2046a115ca4";
        }
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setUserName(String userName) {
        if (userName != null) {
            // Remove domain part from email
            int atIndex = userName.indexOf('@');
            if (atIndex != -1) {
                this.userName = userName.substring(0, atIndex);
            } else {
                this.userName = userName;
            }
        } else {
            this.userName = "Set Username is bad";
        }
    }

    public String getUserName() {
        if (TextUtils.isEmpty(userName)) {
            this.userName = getEmail();
        }
        return this.userName;
    }
}