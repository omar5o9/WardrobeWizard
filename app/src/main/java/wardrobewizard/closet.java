package wardrobewizard;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wardrobewizard.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class closet extends AppCompatActivity {

    // Views
    private TextView titleTextView;
    private TextView allTextView;
    private TextView shirtsTextView;
    private TextView pantsTextView;
    private TextView shoesTextView;
    private TextView accessoriesTextView;
    private Button sortButton;
    private ImageView imageView;
    private EditText factsEditText;
    private Spinner sizeSpinner;
    private Spinner colorSpinner;
    private Spinner styleSpinner;
    private TextView priceTextView;
    private EditText priceEditText;
    private Button addMoreButton;

    private ImageButton home;

    // Database
    private FirebaseDatabase database;
    private DatabaseReference itemsRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);

        // Initialize views
        home =findViewById(R.id.homeButton);
        titleTextView = findViewById(R.id.titleTextView);
        allTextView = findViewById(R.id.allTextView);
        shirtsTextView = findViewById(R.id.shirtsTextView);
        pantsTextView = findViewById(R.id.pantsTextView);
        shoesTextView = findViewById(R.id.shoesTextView);
        accessoriesTextView = findViewById(R.id.accessoriesTextView);
        sortButton = findViewById(R.id.sortButton);
        imageView = findViewById(R.id.imageView);
        factsEditText = findViewById(R.id.factsEditText);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        colorSpinner = findViewById(R.id.colorSpinner);
        styleSpinner = findViewById(R.id.styleSpinner);
        priceTextView = findViewById(R.id.priceSpinner);
        priceEditText = findViewById(R.id.editTextText2);
        addMoreButton = findViewById(R.id.addMoreButton);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            // User is not authenticated, redirect to login screen or handle as required
        } else {
            // User is authenticated, initialize database reference
            String userId = currentUser.getUid();
            itemsRef = database.getReference().child("users").child(userId).child("items");
        }

        // Set listeners
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortDisabledDialog();
            }
        });

        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddingMoreDisabledDialog();
            }
        });

        // TODO: Implement navigation logic
        allTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle All tab click
                // nothing already here
            }
        });

        shirtsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Shirts tab click
                Intent intent = new Intent(closet.this, shirts.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Shirts tab click
                Intent intent = new Intent(closet.this, homepage.class);
                startActivity(intent);
            }
        });

        pantsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Pants tab click
                Intent intent = new Intent(closet.this, pants.class);
                startActivity(intent);
            }
        });

        shoesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Shoes tab click
                Intent intent = new Intent(closet.this, shoes.class);
                startActivity(intent);
            }
        });

        accessoriesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Accessories tab click
                Intent intent = new Intent(closet.this, other_clothes.class);
                startActivity(intent);
            }
        });
    }

    private void showSortDisabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort")
                .setMessage("Sort is disabled")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showAddingMoreDisabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add More")
                .setMessage("Adding more is disabled")
                .setPositiveButton("OK", null)
                .show();
    }

    private void saveItemToDatabase(ClipData.Item item) {
        if (itemsRef != null) {
            String itemId = itemsRef.push().getKey();
            if (itemId != null) {
                itemsRef.child(itemId).setValue(item);
                Toast.makeText(this, "Item saved successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // TODO: Implement other methods and functionality as required
}
