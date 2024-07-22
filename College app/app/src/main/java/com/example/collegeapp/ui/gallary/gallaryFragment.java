package com.example.collegeapp.ui.gallary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class gallaryFragment extends Fragment {


    RecyclerView conv, other, indip, main;
    GalleryAdapter galleryAdapter;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallary, container, false);

        conv = view.findViewById(R.id.convorecycler);
        other = view.findViewById(R.id.otherrecycler);
        indip = view.findViewById(R.id.indirecycler);
        //main = view.findViewById(R.id.mainre);

        ref = FirebaseDatabase.getInstance().getReference().child("Gallery");

        getConvimg();
        getotherimg();
        getindiimg();
        
       return view;
    }

    private void getotherimg() {



        ref.child("Other Events").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String data = dataSnapshot.getValue(String.class);
                    imageList.add(data);

                }

                galleryAdapter = new GalleryAdapter(getContext(), imageList);
                other.setLayoutManager(new GridLayoutManager(getContext(), 3));
                other.setAdapter(galleryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getConvimg() {


        ref.child("Events").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String data = (String) dataSnapshot.getValue();
                    imageList.add(data);

                }

                galleryAdapter = new GalleryAdapter(getContext(), imageList);

                conv.setLayoutManager(new GridLayoutManager(getContext(), 3));
                conv.setAdapter(galleryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getindiimg() {


        ref.child("Sports").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String data = (String) dataSnapshot.getValue();
                    imageList.add(data);

                }

                galleryAdapter = new GalleryAdapter(getContext(), imageList);
                indip.setLayoutManager(new GridLayoutManager(getContext(), 3));
                indip.setAdapter(galleryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getActivity(), "Database error", Toast.LENGTH_SHORT).show();
                
            }
        });

    }
}