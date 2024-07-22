package com.example.admin2.notice;

import androidx.activity.result.ActivityResultLauncher;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class upload_notice extends AppCompatActivity {

    private CardView addimage;
    private ImageView noticeview;
    private EditText noticetitle;
    private Button uploadnotice;

    private DatabaseReference drf, dbref;
    private StorageReference srf;

    private final int REQ = 1;

    private Bitmap bitmap;
    String downloadUrl = "";
    private ProgressDialog pd;

    //for Activity
    ActivityResultLauncher<String> pickimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

            drf = FirebaseDatabase.getInstance().getReference();
            srf = FirebaseStorage.getInstance().getReference();
            pd = new ProgressDialog(this);
            uploadnotice = findViewById(R.id.noticebtn);
            noticetitle = findViewById(R.id.noticetitle);

            addimage = findViewById(R.id.addPhoto);
            noticeview = findViewById(R.id.noticeimgview);


            addimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    openGallery();

                }
            });

            uploadnotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    


                    if(noticetitle.getText().toString().isEmpty())
                    {
                        noticetitle.setError("Empty");
                        noticetitle.requestFocus();
                    }
                    //if notice in fill then this will execute
                    else if (bitmap == null)
                    {
                        uploadData();
                    }
                    else
                    {
                        Uploadimage();
                    }
                }
            });
    }

    //open gallery method
    private void openGallery() {

       Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(pickimage,REQ);

       //pickimage.launch(new Intent());


    }

    //new way activity



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
            noticeview.setImageBitmap(bitmap);
        }

        else if(bitmap == null)
        {
            Uploadimage();

            System.out.println("Upload Successfully");
        }

        else {
            System.out.println("Something went wrong");
        }
    }

    //method for upload image
    private void Uploadimage() {

        //setting upload image txt
        pd.setMessage("Uploading>>>>");
        pd.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = srf.child("Notice").child(finalimg+"jpg");
        final UploadTask uploadt = filepath.putBytes(finalimg);

        uploadt.addOnCompleteListener(upload_notice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    Toast.makeText(upload_notice.this, "Upload image successfully ", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(upload_notice.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Upload Data

    private void uploadData()
    {
        dbref = drf.child("Notice");
        final String uniqukey = dbref.push().getKey();
        String title = noticetitle.getText().toString();

        Calendar caldate = Calendar.getInstance();
        SimpleDateFormat curdate = new SimpleDateFormat("dd--MM--yy");
        String date = curdate.format(caldate.getTime());
        Calendar caltime = Calendar.getInstance();
        SimpleDateFormat curtime = new SimpleDateFormat("hh:mm a");
        String time = curtime.format(caltime.getTime());

        noticedata ndata = new noticedata(title,downloadUrl,date,time,uniqukey);

        //adding on successful listener for database reference
        dbref.child(uniqukey).setValue(ndata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //adding progress dialog for stop upload txt
                pd.dismiss();

                //adding Toast for successfully uploaded notice
                Toast.makeText(upload_notice.this, "Notice Uploaded Successfully (:", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                //adding progress dialog for stop upload txt
                pd.dismiss();

                //adding toast for failure
                Toast.makeText(upload_notice.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }
}