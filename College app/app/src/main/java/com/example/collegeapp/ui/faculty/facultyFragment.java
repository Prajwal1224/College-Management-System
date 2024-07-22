package com.example.collegeapp.ui.faculty;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class facultyFragment extends Fragment {

    RecyclerView csdept,itdept,entcdept,itidept,mechdept;
    LinearLayout csnodata,itnodata,entcnodata,itinodata,mechnodata;
    private List<teacherdata> list1,list2, list3,list4, list5;
    private TeacherAdapter adapter;
    private DatabaseReference dref, dbref;
    //notify
    private static final String channel_id = "Faculty";
    private static final int notify_id = 100;
    private AtomicInteger msgId = new AtomicInteger();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);


        //Finding Ids
        csnodata = view.findViewById(R.id.csnodata);
        itinodata =view.findViewById(R.id.itinodata);
        itnodata = view.findViewById(R.id.itnodata);
        entcnodata = view.findViewById(R.id.entcnodata);
        mechnodata = view.findViewById(R.id.mechnodata);

        csdept = view.findViewById(R.id.csdept);
        itdept = view.findViewById(R.id.itdept);
        itidept = view.findViewById(R.id.itidept);
        mechdept = view.findViewById(R.id.mechdept);
        entcdept = view.findViewById(R.id.entcdept);

        //Finding Ids

        dref = FirebaseDatabase.getInstance().getReference().child("Teachers");


        csdept();
        itdept();
        entcdept();
        mechdept();
        itidept();

        //NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        //Notify
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.man, null);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;

        Bitmap lic = bitmapDrawable.getBitmap();

        /*

        NotificationManager nm =(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(getActivity())
                    .setLargeIcon(lic)
                    .setSmallIcon(R.drawable.abitic)
                    .setContentText("This is Fragment")
                    .setSubText("see the faculty Now")
                    .setChannelId(channel_id)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(channel_id,"Faculty", NotificationManager.IMPORTANCE_HIGH));
        }
        else{
            notification = new Notification.Builder(getActivity())
                    .setLargeIcon(lic)
                    .setSmallIcon(R.drawable.abitic)
                    .setContentText("This is Fragment")
                    .setSubText("see the faculty Now")
                    .build();

        }

        nm.notify(notify_id, notification);
*/



        return view;
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
                    csdept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list1, getContext(), "Computer Science");
                    csdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    itdept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list2, getContext(), "Informational Technology");
                    itdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    mechdept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list4, getContext(), "Mechanical");
                    mechdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    entcdept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list3, getContext(), "Electronics & Telecommunication");
                    entcdept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    itidept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list5, getContext(), "ITI");
                    itidept.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //notification

    public class MyFirebaseMessagingService extends FirebaseMessagingService {

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);

            // Handle incoming messages and trigger notifications
            // Check if the message contains data payload
            if (remoteMessage.getData().size() > 0) {
                // Trigger notification
                sendNotification(remoteMessage.getData().get("message"));
            }
        }

        private void sendNotification(String messageBody) {
            // Implement code to display the notification
            // You can use NotificationCompat to create and display notifications
        }
    }

    private void addFacultyToFirebase(teacherdata facultyData) {
        // Add faculty data to Firebase Realtime Database
        DatabaseReference facultyRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("Computer Science");
        String key = facultyRef.push().getKey();
        facultyRef.child(key).setValue(facultyData);

        // Send notification using Firebase Cloud Messaging
        sendNotificationToTopic("New faculty added in " + "Computer Science");
    }

    private void sendNotificationToTopic(String message) {
        // Send notification to a specific topic (e.g., "Faculty")
        FirebaseMessaging.getInstance().send(new RemoteMessage.Builder("your-sender-id@fcm.googleapis.com")
                .setMessageId(Integer.toString(msgId.incrementAndGet()))
                .addData("message", message)
                .build());
    }




}
