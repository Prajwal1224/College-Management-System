package com.example.admin2;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import papaya.in.sendmail.SendMail;

public class sendmail extends AppCompatActivity {

    private EditText sub;
    private EditText msg;
    private EditText email;
    private DatabaseReference mDatabase;
    CardView openfiles;
    private final int REQ = 1;

    ListView lst;
    EditText addemail;
    Button addbtn,delbtn,updatebtn;

    ArrayList<String> Emails = new ArrayList<String>();
    ArrayAdapter myadapter;

    Integer indexval;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmail);

       sub = findViewById(R.id.subject);
       msg = findViewById(R.id.msg);


       lst = findViewById(R.id.list);
       addemail = findViewById(R.id.addemail);
       addbtn = findViewById(R.id.addb);
       delbtn = findViewById(R.id.delb);
       updatebtn = findViewById(R.id.updateE);
       openfiles = findViewById(R.id.addPdf);

        FirebaseApp.initializeApp(this);

        // Get a reference to the Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

       //set up list view
       // Emails.add("hackurdad087@gmail.com");
       // Emails.add("baburao2614@gmail.com");

        // Load data from Firebase when activity starts
        loadListFromFirebase();


        myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Emails);

        lst.setAdapter(myadapter);


        //add
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strngval = addemail.getText().toString();
                Emails.add(strngval);
                myadapter.notifyDataSetChanged();

                // Save the updated list to Firebase Realtime Database
                saveListToFirebase(Emails);

                addemail.setText("");
            }
        });

        //delete

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strngval = addemail.getText().toString();
                Emails.remove(strngval);
                myadapter.notifyDataSetChanged();

                deleteEmailFromFirebase(strngval);


            }
        });


        //selete Item

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                item = parent.getItemAtPosition(i).toString()+"has been Selected";
                indexval = i;
                Toast.makeText(sendmail.this,item,Toast.LENGTH_SHORT).show();

            }
        });

        //Update

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strngval = addemail.getText().toString();
                Emails.set(indexval, strngval);
                myadapter.notifyDataSetChanged();

                deleteEmailFromFirebase(strngval);

            }
        });

        //delete by click

        lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

                item = parent.getItemAtPosition(i).toString()+" has been deleted";
                Toast.makeText(sendmail.this,item,Toast.LENGTH_SHORT).show();

                Emails.remove(i);
                myadapter.notifyDataSetChanged();

                return true;
            }
        });



        //sending mail process

        findViewById(R.id.sendmail).setOnClickListener(v -> {
            // Retrieve subject text when send button is clicked
            String s = sub.getText().toString();
            // Retrieve message text when send button is clicked
            String b = msg.getText().toString();

            // Iterate through each email address in the list and send email
            for (String emailAddress : Emails) {
                SendMail mail = new SendMail("projectmcoe69@gmail.com", "cydx qjzw xaix zqqs",
                        emailAddress,
                        s,
                        b);
                mail.execute();
            }

            // Notify user that emails have been sent
            Toast.makeText(this, "Emails Sent Successfully...:)", Toast.LENGTH_SHORT).show();
        });


        openfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();

            }

        });

    }


    private void openGallery() {
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);


    }

    //All List linking code for firebase

    // Method to load the list from Firebase Realtime Database
    private void loadListFromFirebase() {
        // Assuming you have a "emails" node in your database
        // Add a ValueEventListener to sync the list with Firebase
        mDatabase.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the existing list
                Emails.clear();
                // Iterate through each child node and add it to the list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String email = snapshot.getValue(String.class);
                    Emails.add(email);
                }
                // Notify the adapter that the data set has changed
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }

    // Your existing code...

    // Method to save the list to Firebase Realtime Database
    private void saveListToFirebase(ArrayList<String> list) {
        // Assuming you have a "emails" node in your database
        // Set the value of the "emails" node to the updated list
        mDatabase.child("emails").setValue(list);
    }

    // Method to delete an email from Firebase Realtime Database
    private void deleteEmailFromFirebase(String emailToDelete) {
        // Find the index of the email to delete in the list
        int index = Emails.indexOf(emailToDelete);
        if (index != -1) {
            // Remove the email from the list
            Emails.remove(index);
            // Save the updated list to Firebase
            saveListToFirebase(Emails);
        } else {
            Toast.makeText(this, "Email not found in list", Toast.LENGTH_SHORT).show();
        }
    }






}