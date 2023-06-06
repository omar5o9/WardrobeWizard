package wardrobewizard;

import android.graphics.Picture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class closet extends AppCompatActivity {

    /*private GridView gridView;
    private PictureAdapter pictureAdapter;
    private List<Picture> pictureList;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public closet() {
        // Required empty public constructor
    }

    public static closet newInstance() {
        return new closet();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase components
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("users").child(currentUser.getUid()).child("pictures");

        // Initialize picture list
        pictureList = new ArrayList<>();

        // Create the picture adapter
        pictureAdapter = new PictureAdapter(requireContext(), pictureList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.closet, container, false);

        gridView = view.findViewById(R.id.gridView);
        gridView.setAdapter(pictureAdapter);

        // Retrieve and display the pictures from Firebase Storage
        retrievePictures();

        return view;
    }

    private void retrievePictures() {
        // TODO: Retrieve pictures from Firebase Storage for the authorized user
        // You can use the storageReference to access the pictures in the storage
        // Add the retrieved pictures to the pictureList and notify the adapter

        // Example code to add a sample picture
        Picture picture = new Picture("picture_url", "description", "color", "size", "style", "price");
        pictureList.add(picture);
        pictureAdapter.notifyDataSetChanged();
    }*/
}