package values.wardrobewizard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.wardrobewizard.R;

public class privacy_policy extends AppCompatActivity {

    private static final String PRIVACY_POLICY = "Privacy Policy\n\n" +
            "Effective date: May 20223\n\n" +
            "Our app Wardrobe Wizard is committed to protecting the privacy of its users. This Privacy Policy describes how we collect, use, store, and disclose your personal information when you use our app.\n\n" +
            "Information Collection and Use\n\n" +
            "We collect personal information that you provide to us when using our app. This may include your name, email address, and other information you choose to provide.\n\n" +
            "We use this information to provide and improve our services, personalize your experience, communicate with you, and respond to your inquiries.\n\n" +
            "Data Security\n\n" +
            "We implement industry-standard security measures to protect your personal information from unauthorized access, alteration, disclosure, or destruction.\n\n" +
            "Third-Party Services\n\n" +
            "Our app may contain links to third-party websites or services that are not owned or controlled by us. We have no control over and assume no responsibility for the privacy practices of these third-party sites or services.\n\n" +
            "Changes to This Privacy Policy\n\n" +
            "We reserve the right to update or change our Privacy Policy at any time. Any changes will be effective immediately upon posting the updated Privacy Policy.\n\n" +
            "Contact Us\n\n" +
            "If you have any questions or suggestions about our Privacy Policy, please contact us.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Privacy Policy");

        TextView textViewPolicy = findViewById(R.id.textViewPolicy);
        textViewPolicy.setText(PRIVACY_POLICY);
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
