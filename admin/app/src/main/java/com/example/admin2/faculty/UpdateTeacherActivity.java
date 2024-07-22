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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {

    private ImageView updateteacherimage;
    private EditText updateteachername,updateteacherpost,updateteacheremail;
    private Button updateteacherbtn, deleteteacherbtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    String name, email, image, post;
    private ProgressDialog pd;
    private StorageReference srf;
    private DatabaseReference drf, dbref;

    private String  downloadUrl, uniquekey, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        //allocating

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        post = getIntent().getStringExtra("post");
        image = getIntent().getStringExtra("image");

         uniquekey = getIntent().getStringExtra("key");
         category = getIntent().getStringExtra("category");

        updateteacherimage = findViewById(R.id.updateteacherimg);
        updateteachername = findViewById(R.id.updateteachername);
        updateteacheremail = findViewById(R.id.updateteacheremail);
        updateteacherpost = findViewById(R.id.updateteacherpost);
        updateteacherbtn = findViewById(R.id.updateteacherbtn);
        deleteteacherbtn = findViewById(R.id.deleteteacherbtn);
        drf = FirebaseDatabase.getInstance().getReference().child("Teachers");
        srf = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);
        //allocating

        try {
            Picasso.get().load(image).into(updateteacherimage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateteachername.setText(name);
        updateteacheremail.setText(email);
        updateteacherpost.setText(post);


        updateteacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();
            }
        });

        updateteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = updateteachername.getText().toString();
                email = updateteacheremail.getText().toString();
                post = updateteacherpost.getText().toString();

                checkValidation();
            }
        });

        deleteteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

    }


    //deleting data
    private void deleteData() {

        drf.child(category).child(uniquekey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(UpdateTeacherActivity.this, "Teacher Delete Successfully :)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateTeacherActivity.this, uploadfaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void checkValidation() {

        name = updateteachername.getText().toString();
        email =updateteacheremail.getText().toString();
        post = updateteacherpost.getText().toString();

        if (name.isEmpty())
        {
          updateteachername.setError("Empty");
          updateteachername.requestFocus();
        }

        else if (email.isEmpty())
        {
            updateteacheremail.setError("Empty");
            updateteacheremail.requestFocus();
        }

        else if (post.isEmpty())
        {
            updateteacherpost.setError("Empty");
            updateteacherpost.requestFocus();
        }


        else if(bitmap == null)
        {
            updateData(image);

        }
        else
        {
            Uploadimage();

        }

    }


    //uploading image
    private void updateData(String s) {

        HashMap hp = new HashMap();

        hp.put("name",name);
        hp.put("email",email);
        hp.put("post",post);
        hp.put("image",s);



        drf.child(category).child(uniquekey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(UpdateTeacherActivity.this, "Teacher Update Successfully :)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateTeacherActivity.this, uploadfaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                
            }
        });

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

        uploadt.addOnCompleteListener(UpdateTeacherActivity.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);
                                    Toast.makeText(UpdateTeacherActivity.this, "Upload image successfully ", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
                else
                {
                   // pd.dismiss();
                    Toast.makeText(UpdateTeacherActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //pick Image
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
            updateteacherimage.setImageBitmap(bitmap);
        }

    }

}