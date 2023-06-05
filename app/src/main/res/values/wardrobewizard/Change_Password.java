package values.wardrobewizard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wardrobewizard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButtonClicked();
            }
        });
    }

    private void confirmButtonClicked() {
        EditText newPasswordEditText = findViewById(R.id.newPassword);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPassword);

        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (newPassword.equals(confirmPassword)) {
            changePassword(this, newPassword);
        } else {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        }
    }

    public void changePassword(Context context, String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Password updated successfully
                                // Delete the old password (if necessary)
                                // ...

                                Toast.makeText(context, "Password updated successfully.", Toast.LENGTH_SHORT).show();

                                // Redirect the user to the login page
                                startActivity(new Intent(Change_Password.this, Login_Screen.class));
                                finish(); // Optional: Finish the current activity
                            } else {
                                // Password update failed
                                // Show an error message or handle the failure
                                Toast.makeText(context, "Failed to update password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}