package com.example.admin2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class uploadpdf extends AppCompatActivity {

    private CardView addpdf;

    private EditText pdftitle;
    private Button uploadpdfbtn;
    private TextView ntv;
    private String pdfname, title;
    

    private DatabaseReference drf;
    private StorageReference srf;

    private final int REQ = 1;

    private Uri pdfdata;
    String downloadUrl = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpdf);

        drf = FirebaseDatabase.getInstance().getReference();
        srf = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        //CardView
        addpdf = findViewById(R.id.addPdf);
        ntv = findViewById(R.id.nosele);
        uploadpdfbtn = findViewById(R.id.Pdfbtn);
        pdftitle = findViewById(R.id.pdftitle);
        
        addpdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {

                openGallery();

            }
        });
        
        uploadpdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                title = pdftitle.getText().toString();
                if (title.isEmpty())
                {
                    pdftitle.setError("Empty");
                    pdftitle.requestFocus();
                    
                }
                else if (pdfdata == null)
                {

                    Toast.makeText(uploadpdf.this, "Please Uploadpdf", Toast.LENGTH_SHORT).show();
                    
                }
                else{
                    
                    uploadPdf();
                }
                
            }
        });


    }


    //Upload Pdf
    private void uploadPdf() {

        pd.setTitle("Please wait...");
        pd.setMessage("Uploading pdf...");
        pd.show();

        StorageReference reference = srf.child("pdf/"+pdfname+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfdata)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uritask = taskSnapshot.getStorage().getDownloadUrl();

                        while (!uritask.isComplete());
                        Uri uri = uritask.getResult();
                        uploadData(String.valueOf(uri));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();

                        Toast.makeText(uploadpdf.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    //Upload Data
    private void uploadData(String downloadUrl) {

        String uniquekey = drf.child(".pdf").push().getKey();

        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);

        drf.child("pdf").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                pd.dismiss();

                Toast.makeText(uploadpdf.this, "Pdf Upload Successfully", Toast.LENGTH_SHORT).show();

                pdftitle.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(uploadpdf.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void openGallery() {

        Intent inte = new Intent();
        inte.setType("*/*");//if this will not work then put *
        inte.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(inte,"Select PDF file."), REQ);


    }

    @SuppressLint("Range")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ && resultCode == RESULT_OK) {

            pdfdata = data.getData();

            //Toast.makeText(this, ""+pdfdata, Toast.LENGTH_SHORT).show();

            if(pdfdata.toString().startsWith("content://"))
            {
                Cursor cursor = null;

                try {
                    cursor = uploadpdf.this.getContentResolver().query(pdfdata,null,null,null,null);

                    if (cursor != null && cursor.moveToFirst())
                    {
                        pdfname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            else if(pdfdata.toString().startsWith("file://"))
            {
                pdfname = new File(pdfdata.toString()).getName();
                
            }
            
            ntv.setText(pdfname);
            
        }
    }

}