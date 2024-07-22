package com.example.collegeapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.collegeapp.R;


import java.util.ArrayList;



public class homeFragment extends Fragment {

    private ImageView location, abitimg, kanitkar;

    String loc = "https://media.discordapp.net/attachments/1101186979233812573/1232076260864163910/clgmcoe.png?ex=662823ef&is=6626d26f&hm=3ac566ae044f857090bf28f0cd5b9495d1d85c4aae656fab9a57df18a18000a3&=&format=webp&quality=lossless&width=906&height=434";
    String abit = "https://moderncoe.edu.in/images/new_bannernew.jpg";
    String kanitkarimg = "https://moderncoe.edu.in/images/logo.png";
    String ig1 = "https://moderncoe.edu.in/images/Infrastructure%20&%20Facilities/Main%20Building/2.1.Main-Building-Entrance_sm.jpg";
    String ig2 = "https://moderncoe.edu.in/images/Infrastructure%20&%20Facilities/Main%20Building/7.1-Students-Facility-Center_sm.jpg";
    String ig3 = "https://moderncoe.edu.in/images/Infrastructure%20&%20Facilities/Auditorium/2.jpg";
    String ig4 = "https://moderncoe.edu.in/images/Infrastructure%20&%20Facilities/ICT%20Enabled%20Classroom%20Laboratories/Smart%20Classroom%20Photos/IMG_20160129_112312095_HDR.jpg";
    String ig5 = "https://moderncoe.edu.in/images/facilities/image021_sm.jpg";
    String igg1 = "https://moderncoe.edu.in/images/new_bannernew.jpg";
    String igg2 = "https://moderncoe.edu.in/images/new_banner3.jpg";
    String igg3 = "https://moderncoe.edu.in/images/new_banner4.jpg";
    String igg4 = "https://moderncoe.edu.in/images/new_banner5.jpg";
    String igg5 = "https://moderncoe.edu.in/images/new_banner6.jpg";
    String igg6 = "https://moderncoe.edu.in/images/new_banner7.jpg";
    String igg7 = "https://moderncoe.edu.in/images/new_banner8.jpg";
    String igg8 = "https://moderncoe.edu.in/images/new_banner1.jpg";

    ImageView i1,i2,i3,i4,i5;
    ImageView ii1,ii2,ii3,ii4,ii5,ii6,ii7,ii8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);


        location = view.findViewById(R.id.location);
        //abitimg = view.findViewById(R.id.abitimg);
        kanitkar = view.findViewById(R.id.kanitkar);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });


        Glide.with(this).load(loc).into(location);
        //Glide.with(this).load(abit).into(abitimg);
        Glide.with(this).load(kanitkarimg).into(kanitkar);

        i1 = view.findViewById(R.id.v1);
        Glide.with(this).load(ig1).into(i1);
        i2 = view.findViewById(R.id.v2);
        Glide.with(this).load(ig2).into(i2);
        i3 = view.findViewById(R.id.v3);
        Glide.with(this).load(ig3).into(i3);
        i4 = view.findViewById(R.id.v4);
        Glide.with(this).load(ig4).into(i4);
        i5 = view.findViewById(R.id.v5);
        Glide.with(this).load(ig5).into(i5);
        ii1 = view.findViewById(R.id.vv1);
        Glide.with(this).load(igg1).into(ii1);
        ii2 = view.findViewById(R.id.vv2);
        Glide.with(this).load(igg2).into(ii2);
        ii3 = view.findViewById(R.id.vv3);
        Glide.with(this).load(igg3).into(ii3);
        ii4 = view.findViewById(R.id.vv4);
        Glide.with(this).load(igg4).into(ii4);
        ii5 = view.findViewById(R.id.vv5);
        Glide.with(this).load(igg5).into(ii5);
        ii6 = view.findViewById(R.id.vv6);
        Glide.with(this).load(igg6).into(ii6);
        ii7 = view.findViewById(R.id.vv7);
        Glide.with(this).load(igg7).into(ii7);
        ii8 = view.findViewById(R.id.vv8);
        Glide.with(this).load(igg8).into(ii8);



        // Inflate the layout for this fragment
        return view;
    }

    private void openMap() {

        Uri uri = Uri.parse("geo:0, 0?q=P.E.S. Modern College of Engineering");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}