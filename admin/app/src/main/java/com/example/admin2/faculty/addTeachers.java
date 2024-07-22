package com.example.admin2.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class addTeachers extends AppCompatActivity {

    private ImageView addteacherimg;
    private EditText addteachername, addteacheremail, addteacherpost;
    private Spinner addteachercategory;
    private Button addteacherbtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String category;
    private String name,post,mail,downloadUrl="";
    private ProgressDialog pd;
    private StorageReference srf;
    private DatabaseReference drf, dbref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        //allocation initialization
        addteacherbtn = findViewById(R.id.addfacultybtn);
        addteachercategory = findViewById(R.id.addteachercategory);
        addteacherpost = findViewById(R.id.addteacherpost);
        addteacheremail = findViewById(R.id.addteacheremail);
        addteachername = findViewById(R.id.addteachername);
        addteacherimg = findViewById(R.id.addteacherimg);
        drf = FirebaseDatabase.getInstance().getReference().child("Teachers");
        srf = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);


        String[] items = new String[]{"Select Category", "Computer Science", "Informational Technology", "Electronics & Telecommunication", "Mechanical", "ITI"};
        addteachercategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        addteachercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addteachercategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        addteacherimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();

            }

        });

        addteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }


    //Validation
    private void checkValidation() {

        name = addteachername.getText().toString();
        mail = addteacheremail.getText().toString();
        post = addteacherpost.getText().toString();

        if (name.isEmpty())
        {
            addteachername.setError("Empty");
            addteachername.requestFocus();
        }

        else if (mail.isEmpty())
        {
            addteacheremail.setError("Empty");
            addteacheremail.requestFocus();
        }

        else if (post.isEmpty())
        {
            addteacherpost.setError("Empty");
            addteacherpost.requestFocus();
        }

        else if (category.equals("Select Category"))
        {
            Toast.makeText(this, "please provide teacher category", Toast.LENGTH_SHORT).show();

        }

        else if(bitmap == null)
        {
            insertData();

        }
        else
        {
            Uploadimage();

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
        filepath = srf.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadt = filepath.putBytes(finalimg);

        uploadt.addOnCompleteListener(addTeachers.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    insertData();
                                    Toast.makeText(addTeachers.this, "Upload image successfully ", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(addTeachers.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertData() {
        dbref = drf.child(category);
        final String uniqukey = dbref.push().getKey();


        teacherdata tdata = new teacherdata(name,post,mail,downloadUrl,uniqukey);

        //adding on successful listener for database reference
        dbref.child(uniqukey).setValue(tdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //adding progress dialog for stop upload txt
                pd.dismiss();

                //adding Toast for successfully uploaded notice
                Toast.makeText(addTeachers.this, "Teacher added Successfully (:", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                //adding progress dialog for stop upload txt
                pd.dismiss();

                //adding toast for failure
                Toast.makeText(addTeachers.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //open gallery
    private void openGallery() {
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);


    }

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
            addteacherimg.setImageBitmap(bitmap);
        }

    }


}