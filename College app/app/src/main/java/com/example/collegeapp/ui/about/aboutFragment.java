package com.example.collegeapp.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.collegeapp.R;


public class aboutFragment extends Fragment {

    String prp = "https://moderncoe.edu.in/images/profPrincipal.jpg";
    String chr = "https://moderncoe.edu.in/images/ProfChairman.jpg";



    ImageView img,img2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        img = view.findViewById(R.id.principal);
        Glide.with(this).load(prp).into(img);
        img2 = view.findViewById(R.id.chairman);
        Glide.with(this).load(chr).into(img2);




        return view;
    }
}