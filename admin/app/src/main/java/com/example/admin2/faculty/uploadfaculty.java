package com.example.admin2.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class    uploadfaculty extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView csdept,itdept,entcdept,itidept,mechdept;
    LinearLayout csnodata,itnodata,entcnodata,itinodata,mechnodata;
    private List<teacherdata> list1,list2, list3,list4, list5;
    private TeacherAdapter adapter;

    private DatabaseReference dref, dbref;
    private StorageReference srf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfaculty);

        //Finding Ids
        csnodata = findViewById(R.id.csnodata);
        itinodata = findViewById(R.id.itinodata);
        itnodata = findViewById(R.id.itnodata);
        entcnodata = findViewById(R.id.entcnodata);
        mechnodata = findViewById(R.id.mechnodata);

        csdept = findViewById(R.id.csdept);
        itdept = findViewById(R.id.itdept);
        itidept = findViewById(R.id.itidept);
        mechdept = findViewById(R.id.mechdept);
        entcdept = findViewById(R.id.entcdept);

        //Finding Ids

        dref = FirebaseDatabase.getInstance().getReference().child("Teachers");


        csdept();
        itdept();
        entcdept();
        mechdept();
        itidept();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(uploadfaculty.this, addTeachers.class));
            }
        });
    }

    //CS dept L1
    private void csdept() {

        dbref = dref.child("Computer Science");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list1 = new ArrayList<>();
                if (!snapshot.exists())
                {
                    csnodata.setVisibility(View.VISIBLE);
                    csdept.setVisibility(View.GONE);
                }
                else
                {
                    csnodata.setVisibility(View.GONE);
                    csdept.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        teacherdata data = snapshot1.getValue(teacherdata.class);
                        list1.add(data);

                    }
                    csdept.setHasFixedSize(true);
                    csdept.setLayoutManager(new LinearLayoutManager(uploadfaculty.this));
                    adapter = new TeacherAdapter(list1, uploadfaculty.this, "Computer Science");
                    csdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(uploadfaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                
            }
        });

    }


    //IT dept L2
    private void itdept() {

        dbref = dref.child("Informational Technology");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list2 = new ArrayList<>();
                if (!snapshot.exists())
                {
                    itnodata.setVisibility(View.VISIBLE);
                    itdept.setVisibility(View.GONE);
                }
                else
                {
                    itnodata.setVisibility(View.GONE);
                    itdept.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        teacherdata data = snapshot1.getValue(teacherdata.class);
                        list2.add(data);

                    }
                    itdept.setHasFixedSize(true);
                    itdept.setLayoutManager(new LinearLayoutManager(uploadfaculty.this));
                    adapter = new TeacherAdapter(list2, uploadfaculty.this, "Informational Technology");
                    itdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(uploadfaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    //MECH dept L4
    private void mechdept() {

        dbref = dref.child("Mechanical");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list4 = new ArrayList<>();
                if (!snapshot.exists())
                {
                    mechnodata.setVisibility(View.VISIBLE);
                    mechdept.setVisibility(View.GONE);
                }
                else
                {
                    mechnodata.setVisibility(View.GONE);
                    mechdept.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        teacherdata data = snapshot1.getValue(teacherdata.class);
                        list4.add(data);

                    }
                    mechdept.setHasFixedSize(true);
                    mechdept.setLayoutManager(new LinearLayoutManager(uploadfaculty.this));
                    adapter = new TeacherAdapter(list4, uploadfaculty.this, "Mechanical");
                    mechdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(uploadfaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    //ENTC dept L3
    private void entcdept() {

        dbref = dref.child("Electronics & Telecommunication");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list3 = new ArrayList<>();
                if (!snapshot.exists())
                {
                    entcdept.setVisibility(View.VISIBLE);
                    entcdept.setVisibility(View.GONE);
                }
                else
                {
                    entcnodata.setVisibility(View.GONE);
                    entcdept.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        teacherdata data = snapshot1.getValue(teacherdata.class);
                        list3.add(data);

                    }
                    entcdept.setHasFixedSize(true);
                    entcdept.setLayoutManager(new LinearLayoutManager(uploadfaculty.this));
                    adapter = new TeacherAdapter(list3, uploadfaculty.this, "Electronics & Telecommunication");
                    entcdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(uploadfaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //ITI dept L5
    private void itidept() {

        dbref = dref.child("ITI");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list5 = new ArrayList<>();
                if (!snapshot.exists())
                {
                    itinodata.setVisibility(View.VISIBLE);
                    itidept.setVisibility(View.GONE);
                }
                else
                {
                    itinodata.setVisibility(View.GONE);
                    itidept.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        teacherdata data = snapshot1.getValue(teacherdata.class);
                        list5.add(data);

                    }
                    itidept.setHasFixedSize(true);
                    itidept.setLayoutManager(new LinearLayoutManager(uploadfaculty.this));
                    adapter = new TeacherAdapter(list5, uploadfaculty.this, "ITI");
                    itidept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(uploadfaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}