package com.example.wardrobewizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settings_screen extends Fragment {

    public settings_screen() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        TextView editProfileText = view.findViewById(R.id.EditText);
        TextView signOutText = view.findViewById(R.id.signout);
        TextView changePasswordText = view.findViewById(R.id.passwordChange);
        TextView deleteAccountText = view.findViewById(R.id.deleteAccount);
        TextView privacyPolicyText = view.findViewById(R.id.Privacy);
        TextView serviceTermsText = view.findViewById(R.id.service_terms);

        editProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), edit_profile.class));
            }
        });

        signOutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform sign out
                performSignOut();
            }
        });

        changePasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Change Password page
                startActivity(new Intent(getActivity(), Change_Password.class));
            }
        });

        deleteAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Delete Account page
                startActivity(new Intent(getActivity(), delete_account.class));
            }
        });

        privacyPolicyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Privacy Policy page
                startActivity(new Intent(getActivity(), privacy_policy.class));
            }
        });

        serviceTermsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Service Terms page
                startActivity(new Intent(getActivity(), service_terms.class));
            }
        });

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.closetButton) {
                    // Handle closet button click
                    // Navigate to the closet page
                    startActivity(new Intent(getActivity(), closet.class));
                    return true;
                } else if (itemId == R.id.addClothesButton) {
                    // Handle add clothes button click
                    // Navigate to the add clothes page
                    startActivity(new Intent(getActivity(), add_clothes.class));
                    return true;
                } else if (itemId == R.id.settingsButton) {
                    // Handle settings button click
                    // Do nothing, already on the settings screen
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void performSignOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the sign out operation (Implement your own sign out logic here)
                // ...

                // Redirect the user to the login screen
                startActivity(new Intent(getActivity(), Login_Screen.class));
                getActivity().finish(); // Optional: Finish the current activity
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, dismiss the dialog
            }
        });
        builder.create().show();
    }
}

