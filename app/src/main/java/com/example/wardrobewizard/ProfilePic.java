package com.example.wardrobewizard;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ProfilePic extends Activity {
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 3;
    private static final int REQUEST_CODE_CROP_IMAGE = 4;

    private ImageView imageView;
    private ImageButton choosePhotoButton;
    private ImageButton takePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile_pic);

        imageView = findViewById(R.id.image_view);
        choosePhotoButton = findViewById(R.id.choose_photo_button);
        takePhotoButton = findViewById(R.id.take_photo_button);

        choosePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            captureImage();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_IMAGE:
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        cropImage(selectedImageUri);
                    }
                    break;
                case REQUEST_CODE_CAPTURE_IMAGE:
                    if (data != null && data.getExtras() != null) {
                        Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                        if (capturedImage != null) {
                            cropCapturedImage(capturedImage);
                        }
                    }
                    break;
                case REQUEST_CODE_CROP_IMAGE:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap croppedImage = extras.getParcelable("data");
                            imageView.setImageBitmap(croppedImage);
                        }
                    }
                    break;
            }
        }
    }

    private void cropImage(Uri imageUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    private void cropCapturedImage(Bitmap capturedImage) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("data", capturedImage);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }
    }
}