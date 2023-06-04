package com.example.wardrobewizard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class open_page extends AppCompatActivity {

    private static final int LOADING_DURATION = 3500; // 5 seconds
    private ProgressBar loadingProgressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openpage);

        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        // Start the progress bar animation
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 10;

                    // Update the progress bar on the main thread
                    handler.post(new Runnable() {
                        public void run() {
                            loadingProgressBar.setProgress(progressStatus);
                        }
                    });

                    try {
                        // Sleep for 1 second to simulate progress
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Start the next activity after the loading duration
                Intent intent = new Intent(open_page.this, Login_Screen.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}