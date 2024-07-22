package com.example.admin2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView upload_noticebtn, addGimg, addebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload_noticebtn = findViewById(R.id.addNotice);

        upload_noticebtn.setOnClickListener(this);

        addGimg = findViewById(R.id.image);

        addGimg.setOnClickListener(this);

        addebook = findViewById(R.id.ebook);

        addebook.setOnClickListener(this);

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



       }


    }


}