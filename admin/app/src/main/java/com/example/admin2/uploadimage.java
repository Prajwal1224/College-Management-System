package com.example.admin2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class uploadimage extends AppCompatActivity {

    private Spinner imagecategory;
    private CardView selectimage;
    private Button Uploadimage;
    private ImageView galleryimageview;
    private final int REQ = 1;
    private String category;
    private Bitmap bitmap;
    private DatabaseReference drf;
    private StorageReference srf;
    String downloadUrl = "";

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimage);

        selectimage = findViewById(R.id.addGalleryImage);
        imagecategory = findViewById(R.id.image_category);
        Uploadimage = findViewById(R.id.uploadimagebtn);
        galleryimageview = findViewById(R.id.galleryimgview);

        pd = new ProgressDialog(this);

        //databaseReferance
        drf = FirebaseDatabase.getInstance().getReference().child("Gallery");

        //StrorageReferance
        srf = FirebaseStorage.getInstance().getReference().child("Gallery");

        String[] items = new String[]{"Select Category", "Events", "Sports", "Other Events"};
        imagecategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        imagecategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = imagecategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();

            }

        });

        Uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bitmap == null)
                {
                    Toast.makeText(uploadimage.this,"Please upload Image",Toast.LENGTH_SHORT).show();

                }
                else if (category.equals("Select Category"))
                {
                    Toast.makeText(uploadimage.this, "Please Select Image Category", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    pd.setMessage("Uploading...");
                    pd.show();
                    UploadImage();
                }

            }


        });


    }

    private void UploadImage() {
        //setting upload image txt
        pd.setMessage("Uploading>>>>");
        pd.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = srf.child(finalimg+"jpg");
        final UploadTask uploadt = filepath.putBytes(finalimg);

        uploadt.addOnCompleteListener(uploadimage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful())
                {
                    uploadt.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //uploading data
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(uploadimage.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }


    //upload data
    private void uploadData() {

        drf = drf.child(category);
        final String uniqueKey = drf.push().getKey();

        drf.child(uniqueKey).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                pd.dismiss();

                Toast.makeText(uploadimage.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pd.dismiss();

                Toast.makeText(uploadimage.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                
            }
        });

    }


    //GPT upload data
    /*private void uploadData() {
        drf = drf.child(category);
        drf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Category already exists, no need to create a new one
                    String uniqueKey = drf.push().getKey();
                    drf.child(uniqueKey).setValue(downloadUrl)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    Toast.makeText(uploadimage.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(uploadimage.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // Category doesn't exist, create a new one
                    drf.setValue(downloadUrl)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    Toast.makeText(uploadimage.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(uploadimage.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(uploadimage.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    private void openGallery() {
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ && resultCode == RESULT_OK)
        {
            Uri uri  = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            galleryimageview.setImageBitmap(bitmap);
        }

    }


}