package com.example.wardrobewizard;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {

    private static final int LOADING_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        // Show a progress indicator or animation to indicate loading

        // Simulate loading process in the background
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Perform any initialization tasks or data loading operations here

                // Transition to the homepage
                Intent intent = new Intent(LoadingScreen.this, homepage.class);
                startActivity(intent);
                finish();
            }
        }, LOADING_DURATION);
    }

    @Override
    public void onBackPressed() {
        // Disable back button during loading
        // Uncomment the line below if you want to prevent going back from the loading screen
        // super.onBackPressed();
    }
}