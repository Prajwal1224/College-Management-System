package com.example.admin2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin2.faculty.uploadfaculty;
import com.example.admin2.notice.DeleteNoticeActivity;
import com.example.admin2.notice.upload_notice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView upload_noticebtn, addGimg, addebook, faculty, deletenotice, mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deletenotice = findViewById(R.id.delnotice);

        deletenotice.setOnClickListener(this);

        faculty = findViewById(R.id.faculty);

        faculty.setOnClickListener(this);

        upload_noticebtn = findViewById(R.id.addNotice);

        upload_noticebtn.setOnClickListener(this);

        addGimg = findViewById(R.id.image);

        addGimg.setOnClickListener(this);

        addebook = findViewById(R.id.ebook);

        addebook.setOnClickListener(this);

        mail = findViewById(R.id.sendmail);

        mail.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

       switch(view.getId())
       {
           case R.id.addNotice:
               Intent inte = new Intent(MainActivity.this, upload_notice.class);
               startActivity(inte);

           break;

           case R.id.image:
               Intent inti = new Intent(MainActivity.this, uploadimage.class);
               startActivity(inti);

               break;

           case R.id.ebook:
               Intent intii = new Intent(MainActivity.this, uploadpdf.class);
               startActivity(intii);

               break;

           case R.id.faculty:
               Intent intiii = new Intent(MainActivity.this, uploadfaculty.class);
               startActivity(intiii);

               break;

           case R.id.delnotice:
               Intent intiiii = new Intent(MainActivity.this, DeleteNoticeActivity.class);
               startActivity(intiiii);

               break;

           case R.id.sendmail:
               Intent intiiiii = new Intent(MainActivity.this, sendmail.class);
               startActivity(intiiiii);

               break;
       }


    }


}