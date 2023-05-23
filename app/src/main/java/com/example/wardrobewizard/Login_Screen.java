package com.example.wardrobewizard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class Login_Screen extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;


    @Override

    //initialize Firebase and set the content view to the login_screen layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.login_screen);

        //views are initialized using their respective IDs from the XML layout using findViewById
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        //click listener is set, which triggers the login process when the button is clicked.
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login button click
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                // Validate the login credentials
                if (validateCredentials(email, password)) {
                    // Perform the login process
                    loginUser(email, password);
                }
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for the "No Account yet? Create One" TextView
                // Perform the action you want, such as navigating to the registration page
                Intent intent = new Intent(Login_Screen.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    //perform basic validation
    // checks on the email and password.
    // If the validation fails, appropriate error messages are displayed
    private boolean validateCredentials(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            return false;
        }
        return true;
    }

    //If the validation succeeds, the loginUser method
    // is called to authenticate the user using Firebase Authentication.
    private void loginUser(String email, String password) {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    //If the login is successful, the user is redirected to the HomepageActivity.
                    // Otherwise, an error message is displayed using a Toast
                    /*The Toast class in Android is used to display short-duration messages or notifications to the user.
                    It provides a simple way to show pop-up messages at the bottom of the screen for a specified duration.
                     The Toast.makeText() method is used to createa new Toast object with a specified message and duration.
                    The makeText() method requires three parameters: the context (usually the current activity), the message to be displayed, and the duration of the toast.*/
                    //ToDo:Need to update homepagePageActivity to new page name
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Authentication successful
                            Intent intent = new Intent(Login_Screen.this, HomepageActivity.class);
                            startActivity(intent);
                            finish(); // Optional: Finish the login activity to prevent the user from going back
                        } else {
                            // Authentication failed, show an error message or handle the failure case
                            Toast.makeText(Login_Screen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}