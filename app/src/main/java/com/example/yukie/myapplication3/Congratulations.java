package com.example.yukie.myapplication3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Congratulations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulationimage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendNotification() {
        // Intent
        Intent intent = new Intent(Congratulations.this, Announcement.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                Congratulations.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // NotificationBuilder
        // LargeIcon
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.unnamed);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext());
        builder.setContentIntent(contentIntent);

        builder.setTicker("Ticker");

        builder.setSmallIcon(R.drawable.unnamed);

        builder.setContentTitle("tt");

        builder.setContentText("yy");

        builder.setLargeIcon(largeIcon);

        builder.setWhen(System.currentTimeMillis());

        builder.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS);

        builder.setAutoCancel(true);

        // NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        // Notification
        manager.notify(0, builder.build());
    }

//    GPS gps = new GPS();


}
