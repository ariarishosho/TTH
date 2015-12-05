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

public class Ranking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rankingimage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


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
        // Intent 縺ｮ菴懈��
        Intent intent = new Intent(Ranking.this, Ranking.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                Ranking.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // LargeIcon 縺ｮ Bitmap 繧堤函謌�
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.unnamed);

        // NotificationBuilder繧剃ｽ懈��
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext());
        builder.setContentIntent(contentIntent);
        // 繧ｹ繝�繝ｼ繧ｿ繧ｹ繝舌�ｼ縺ｫ陦ｨ遉ｺ縺輔ｌ繧九ユ繧ｭ繧ｹ繝�
        builder.setTicker("Ticker");
        // 繧｢繧､繧ｳ繝ｳ
        builder.setSmallIcon(R.drawable.unnamed);
        // Notification繧帝幕縺�縺溘→縺阪↓陦ｨ遉ｺ縺輔ｌ繧九ち繧､繝医Ν
        builder.setContentTitle("隧ｱ縺励°縺代◆縺�縺九ｉ縲�");
        // Notification繧帝幕縺�縺溘→縺阪↓陦ｨ遉ｺ縺輔ｌ繧九し繝悶ち繧､繝医Ν
        builder.setContentText("霑代￥縺ｫ蜿矩＃縺後＞縺ｾ縺呻ｼ�");
        // Notification繧帝幕縺�縺溘→縺阪↓陦ｨ遉ｺ縺輔ｌ繧九い繧､繧ｳ繝ｳ
        builder.setLargeIcon(largeIcon);
        // 騾夂衍縺吶ｋ繧ｿ繧､繝溘Φ繧ｰ
        builder.setWhen(System.currentTimeMillis());
        // 騾夂衍譎ゅ�ｮ髻ｳ繝ｻ繝舌う繝悶�ｻ繝ｩ繧､繝�
        builder.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS);
        // 繧ｿ繝�繝励☆繧九→繧ｭ繝｣繝ｳ繧ｻ繝ｫ(豸医∴繧�)
        builder.setAutoCancel(true);

        // NotificationManager繧貞叙蠕�
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        // Notification繧剃ｽ懈�舌＠縺ｦ騾夂衍
        manager.notify(0, builder.build());
    }

//    GPS gps = new GPS();


}
