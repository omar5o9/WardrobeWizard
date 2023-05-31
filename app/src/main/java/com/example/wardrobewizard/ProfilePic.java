package com.example.wardrobewizard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfilePic extends Activity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView profileImageView;
    private ImageButton takePhotoButton;
    private ImageButton choosePhotoButton;
    private Button saveButton;

    private StorageReference storageRef;
    private DatabaseReference userRef;
    private User currentUser;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile_pic);

        profileImageView = findViewById(R.id.profileImageView);
        takePhotoButton = findViewById(R.id.takePhotoButton);
        choosePhotoButton = findViewById(R.id.choosePhotoButton);
        saveButton = findViewById(R.id.saveButton);

        // Initialize Firebase Storage
        storageRef = FirebaseStorage.getInstance().getReference();

        // Assuming you have a userRef instance in your edit_profile activity
        userRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(currentUser.getUserId());

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        choosePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageBitmap != null) {
                    uploadImageToDatabase(selectedImageBitmap);
                } else {
                    Toast.makeText(ProfilePic.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                if (extras != null && extras.containsKey("data")) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    profileImageView.setImageBitmap(imageBitmap);
                    selectedImageBitmap = imageBitmap;
                }
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    profileImageView.setImageBitmap(imageBitmap);
                    selectedImageBitmap = imageBitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfilePic.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadImageToDatabase(Bitmap imageBitmap) {
        if (userRef != null) {
            // Create a unique file name for the image
            String fileName = "profile_pic_" + currentUser.getUserId() + ".png";
            StorageReference imageRef = storageRef.child(fileName);

            // Convert the image to bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageData = baos.toByteArray();

            // Upload the image to Firebase Storage
            UploadTask uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the image download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            // Save the image URL to the database
                            userRef.child("profilePictureUrl").setValue(downloadUri.toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ProfilePic.this, "Profile picture saved", Toast.LENGTH_SHORT).show();
                                            // Go back to the home screen or perform any necessary action
                                            finish();
                                            startActivity(new Intent(ProfilePic.this, homepage.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfilePic.this, "Failed to save profile picture", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfilePic.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfilePic.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ProfilePic.this, "User reference is null", Toast.LENGTH_SHORT).show();
        }
    }
}
