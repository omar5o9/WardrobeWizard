package com.example.wardrobewizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class add_clothes extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView imageView;
    private TextView textViewChoosePhoto;
    private TextView textViewTakePhoto;

    public add_clothes() {
        // Required empty public constructor
    }

    public static add_clothes newInstance(String param1, String param2) {
        add_clothes fragment = new add_clothes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_clothes, container, false);

        imageView = view.findViewById(R.id.imageView4);
        textViewChoosePhoto = view.findViewById(R.id.textView25);
        textViewTakePhoto = view.findViewById(R.id.textView26);

        textViewChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle choosing photo from device library
            }
        });

        textViewTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle taking a photo using the device camera
            }
        });

        return view;
    }
}