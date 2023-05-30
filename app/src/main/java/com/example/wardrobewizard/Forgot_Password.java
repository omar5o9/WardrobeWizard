package com.example.wardrobewizard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                            Intent intent = new Intent(Forgot_Password.this, Change_Password.class);
                            startActivity(intent);
                        } else {
                            // Reset password email sending failed
                            // Show an error message or handle the failure
                            Toast.makeText(Forgot_Password.this, "Failed to send reset password email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}