package values.wardrobewizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wardrobewizard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    private EditText emailEditText;
    private Button sendEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password); // Replace with your layout file

        emailEditText = findViewById(R.id.emailEnter);
        sendEmailButton = findViewById(R.id.sendEmail);

        sendEmailButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (!email.isEmpty()) {
                sendResetPasswordEmail(email);
            } else {
                Toast.makeText(Forgot_Password.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendResetPasswordEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Reset password email sent successfully
                            // Redirect the user to the change password page
                            showResetEmailSentDialog();
                        } else {
                            // Reset password email sending failed
                            // Show an error message or handle the failure
                            Toast.makeText(Forgot_Password.this, "Failed to send reset password email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void showResetEmailSentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Email Sent");
        builder.setMessage("An email with instructions to reset your password has been sent.");

        builder.setPositiveButton("Resend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Clear the email field
                emailEditText.setText("");
                // Allow the user to enter the email again for resending the reset password email
            }
        });

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Go back to the login screen
                Intent intent = new Intent(Forgot_Password.this, Login_Screen.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}