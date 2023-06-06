package wardrobewizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wardrobewizard.R;

public class calendar extends AppCompatActivity {

    private Button plannerButton;
    private Button outfitsButton;
    private Button calendarButton;
    private ImageButton home;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        plannerButton = findViewById(R.id.plannerButton);
        outfitsButton = findViewById(R.id.outfitsButton);
        calendarButton = findViewById(R.id.calendarButton);
        home = findViewById(R.id.homeButton);

        plannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing as we are already on the Planner page
            }
        });

        outfitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Outfit Creator activity
                startActivity(new Intent(calendar.this, outifit_creator.class));
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Calendar activity
                startActivity(new Intent(calendar.this, calendar.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Calendar activity
                startActivity(new Intent(calendar.this, homepage.class));
            }
        });

    }
}

