package values.wardrobewizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wardrobewizard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class delete_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account);

        Button deleteButton = findViewById(R.id.button2);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete your account?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform account deletion and send email
                deleteAccount();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteAccount() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Delete user data from the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(user.getUid());
            userRef.removeValue();

            // Delete user account from Firebase Authentication
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Account deletion successful
                                sendAccountDeletionEmail();
                            } else {
                                // Account deletion failed
                                // Handle the failure or show an error message
                            }
                        }
                    });
        }
    }

    private void sendAccountDeletionEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Email sent successfully
                                // Notify the user about the account deletion via email
                                sendNotificationEmail();
                                navigateToLoginScreen();
                            } else {
                                // Email sending failed
                                // Handle the failure or show an error message
                            }
                        }
                    });
        }
    }

    private void sendNotificationEmail() {
        // Code to send account deletion notification email
        // ...
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(delete_account.this, Login_Screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}