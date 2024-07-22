package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.collegeapp.Video.video;
import com.example.collegeapp.ebooks.EbookActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController  navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    //notify
    private static final String channel_id = "Faculty";
    private static final int notify_id = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.frame_layout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);



        //Notify
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.man, null);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;

        Bitmap lic = bitmapDrawable.getBitmap();

        //notify
        /*NotificationManager nm =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             notification = new Notification.Builder(this)
                    .setLargeIcon(lic)
                    .setSmallIcon(R.drawable.abitic)
                    .setContentText("New Faculty Added")
                    .setSubText("see the faculty Now")
                    .setChannelId(channel_id)
                    .build();
             nm.createNotificationChannel(new NotificationChannel(channel_id,"Faculty", NotificationManager.IMPORTANCE_HIGH));
        }
        else{
            notification = new Notification.Builder(this)
                    .setLargeIcon(lic)
                    .setSmallIcon(R.drawable.abitic)
                    .setContentText("New Faculty Added")
                    .setSubText("see the faculty Now")
                    .build();

        }

        nm.notify(notify_id, notification);*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)){

            return true;

        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_devep:
                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_ebook:
                startActivity(new Intent(this, EbookActivity.class));
                break;

            case R.id.nav_video:

                startActivity(new Intent(this, video.class));
                break;

            case R.id.nav_rate:
                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_themes:
                Toast.makeText(this, "Themes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_website:

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://moderncoe.edu.in/"));
                startActivity(intent);

                break;

        }

        return true;
    }
}