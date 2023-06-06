package wardrobewizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wardrobewizard.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;



public class Planner extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    private Spinner mondayDropdown, tuesdayDropdown, wednesdayDropdown,
            thursdayDropdown, fridayDropdown, saturdayDropdown, sundayDropdown;
    private EditText mondayNotes, tuesdayNotes, wednesdayNotes,
            thursdayNotes, fridayNotes, saturdayNotes, sundayNotes;
    private Button plannerButton;
    private Button outfitsButton;
    private Button calendarButton;
    private Button saveButton;
    private Button newWeekButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        plannerButton = findViewById(R.id.plannerButton);
        outfitsButton = findViewById(R.id.outfitsButton);
        calendarButton = findViewById(R.id.calendarButton);
        saveButton = findViewById(R.id.saveButton);
        newWeekButton = findViewById(R.id.saveButton2);

        mondayDropdown = findViewById(R.id.mondayDropdown);
        tuesdayDropdown = findViewById(R.id.tuesdayDropdown);
        wednesdayDropdown = findViewById(R.id.wednesdayDropdown);
        thursdayDropdown = findViewById(R.id.thursdayDropdown);
        fridayDropdown = findViewById(R.id.fridayDropdown);
        saturdayDropdown = findViewById(R.id.saturdayDropdown);
        sundayDropdown = findViewById(R.id.sundayDropdown);

        mondayNotes = findViewById(R.id.mondayNotes);
        tuesdayNotes = findViewById(R.id.tuesdayNotes);
        wednesdayNotes = findViewById(R.id.wednesdayNotes);
        thursdayNotes = findViewById(R.id.thursdayNotes);
        fridayNotes = findViewById(R.id.fridayNotes);
        saturdayNotes = findViewById(R.id.saturdayNotes);
        sundayNotes = findViewById(R.id.sundayNotes);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                startActivity(new Intent(Planner.this, outifit_creator.class));
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Calendar activity
                startActivity(new Intent(Planner.this, calendar.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a dialog box to confirm saving
                showSaveConfirmationDialog();
            }
        });

        newWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all previous information and show a blank planner
                clearPlanner();
            }
        });

        // Set up dropdown menus
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.outfit_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mondayDropdown.setAdapter(adapter);
        tuesdayDropdown.setAdapter(adapter);
        wednesdayDropdown.setAdapter(adapter);
        thursdayDropdown.setAdapter(adapter);
        fridayDropdown.setAdapter(adapter);
        saturdayDropdown.setAdapter(adapter);
        sundayDropdown.setAdapter(adapter);

        // Set up dropdown selection listener
        AdapterView.OnItemSelectedListener dropdownListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle dropdown selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        };

        mondayDropdown.setOnItemSelectedListener(dropdownListener);
        tuesdayDropdown.setOnItemSelectedListener(dropdownListener);
        wednesdayDropdown.setOnItemSelectedListener(dropdownListener);
        thursdayDropdown.setOnItemSelectedListener(dropdownListener);
        fridayDropdown.setOnItemSelectedListener(dropdownListener);
        saturdayDropdown.setOnItemSelectedListener(dropdownListener);
        sundayDropdown.setOnItemSelectedListener(dropdownListener);
    }
    private String[] getOutfitNames() {
        // Replace this with your own implementation to retrieve outfit names from Firebase or any other source
        return new String[]{"Outfit 1", "Outfit 2", "Outfit 3"};
    }

    private void showSaveConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Planner");
        builder.setMessage("Are you sure you want to save the planner?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savePlannerData();
                Toast.makeText(Planner.this, "Planner saved", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void savePlannerData() {
        String mondayOutfit = mondayDropdown.getSelectedItem().toString();
        String tuesdayOutfit = tuesdayDropdown.getSelectedItem().toString();
        String wednesdayOutfit = wednesdayDropdown.getSelectedItem().toString();
        String thursdayOutfit = thursdayDropdown.getSelectedItem().toString();
        String fridayOutfit = fridayDropdown.getSelectedItem().toString();
        String saturdayOutfit = saturdayDropdown.getSelectedItem().toString();
        String sundayOutfit = sundayDropdown.getSelectedItem().toString();

        String mondayNote = mondayNotes.getText().toString().trim();
        String tuesdayNote = tuesdayNotes.getText().toString().trim();
        String wednesdayNote = wednesdayNotes.getText().toString().trim();
        String thursdayNote = thursdayNotes.getText().toString().trim();
        String fridayNote = fridayNotes.getText().toString().trim();
        String saturdayNote = saturdayNotes.getText().toString().trim();
        String sundayNote = sundayNotes.getText().toString().trim();

        String userId = currentUser.getUid();
        DatabaseReference userPlannerRef = mDatabase.child("planner").child(userId);

        Map<String, Object> plannerData = new HashMap<>();
        plannerData.put("mondayOutfit", mondayOutfit);
        plannerData.put("tuesdayOutfit", tuesdayOutfit);
        plannerData.put("wednesdayOutfit", wednesdayOutfit);
        plannerData.put("thursdayOutfit", thursdayOutfit);
        plannerData.put("fridayOutfit", fridayOutfit);
        plannerData.put("saturdayOutfit", saturdayOutfit);
        plannerData.put("sundayOutfit", sundayOutfit);

        plannerData.put("mondayNote", mondayNote);
        plannerData.put("tuesdayNote", tuesdayNote);
        plannerData.put("wednesdayNote", wednesdayNote);
        plannerData.put("thursdayNote", thursdayNote);
        plannerData.put("fridayNote", fridayNote);
        plannerData.put("saturdayNote", saturdayNote);
        plannerData.put("sundayNote", sundayNote);

        userPlannerRef.setValue(plannerData);
    }

    private void clearPlanner() {
        mondayDropdown.setSelection(0);
        tuesdayDropdown.setSelection(0);
        wednesdayDropdown.setSelection(0);
        thursdayDropdown.setSelection(0);
        fridayDropdown.setSelection(0);
        saturdayDropdown.setSelection(0);
        sundayDropdown.setSelection(0);

        mondayNotes.setText("");
        tuesdayNotes.setText("");
        wednesdayNotes.setText("");
        thursdayNotes.setText("");
        fridayNotes.setText("");
        saturdayNotes.setText("");
        sundayNotes.setText("");
    }
}
