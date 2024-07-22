package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Fullimageview extends AppCompatActivity {

    private ImageView ig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimageview);

        ig = findViewById(R.id.img1);

        String image = getIntent().getStringExtra("image");

        Glide.with(this).load(image).into(ig);


    }
}