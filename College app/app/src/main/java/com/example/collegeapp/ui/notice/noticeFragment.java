package com.example.collegeapp.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class noticeFragment extends Fragment {
    private ProgressBar pb;
    private RecyclerView rv;
    private ArrayList<noticedata> list;
    private NoticeAdapter adapter;
    private DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        rv = view.findViewById(R.id.delenoticerecycler);
        pb = view.findViewById(R.id.pb);
        ref = FirebaseDatabase.getInstance().getReference().child("Notice");

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);


        getNotice();

        return view;

    }

    private void getNotice() {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                list = new ArrayList<>();

                for(DataSnapshot snapshot : datasnapshot.getChildren())
                {
                    noticedata data = snapshot.getValue(noticedata.class);
                    list.add(data);

                }

                adapter = new NoticeAdapter(getContext(), list);

                adapter.notifyDataSetChanged();

                pb.setVisibility(View.GONE);

                rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                pb.setVisibility(View.GONE);

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

}