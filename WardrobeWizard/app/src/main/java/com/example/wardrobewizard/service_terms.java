package wardrobewizard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.wardrobewizard.R;

public class service_terms extends AppCompatActivity {

    private static final String TERMS_OF_SERVICE = "Terms of Service\n\n" +
            "Welcome to our app! These terms and conditions outline the rules and regulations for the use of our app Wardrobe Wizard.\n\n" +
            "By accessing this app, we assume you accept these terms and conditions. Do not continue to use our app if you do not agree to all of the terms and conditions stated on this page.\n\n" +
            "The following terminology applies to these Terms and Conditions, Privacy Statement, and Disclaimer Notice and all Agreements: 'Client,' 'You,' and 'Your' refers to you, the person log on this app and compliant to the Company’s terms and conditions.\n\n" +
            "Please read these Terms and Conditions carefully before using our app.\n\n" +
            "Intellectual Property\n\n" +
            "All content, trademarks, logos, images, and intellectual property displayed on the app are the property of our app and/or third-party licensors. You must not use or reproduce them without prior written permission.\n\n" +
            "Termination\n\n" +
            "We may terminate or suspend your access immediately, without prior notice or liability, for any reason whatsoever, including without limitation if you breach the Terms.\n\n" +
            "Governing Law\n\n" +
            "These Terms shall be governed and construed in accordance with the laws of your country, without regard to its conflict of law provisions.\n\n" +
            "Changes\n\n" +
            "We reserve the right, at our sole discretion, to modify or replace these Terms at any time. Please review this page periodically for changes.\n\n" +
            "Contact Us\n\n" +
            "If you have any questions about these Terms, please contact us.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_terms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Service and Terms");

        TextView textViewTerms = findViewById(R.id.textViewTerms);
        textViewTerms.setText(TERMS_OF_SERVICE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
