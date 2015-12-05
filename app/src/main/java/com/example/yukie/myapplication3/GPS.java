package com.example.yukie.myapplication3;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Set;

/**
 * Created by yukie on 2015/12/05.
 */
public class GPS extends FragmentActivity implements LocationListener {

    private TextView tv_la;
    private TextView tv_lng;
    private TextView distance;

    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothChatService mChatService = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topimage);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }


        findViewById(R.id.friendlistbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "----------");
                Intent intent = new Intent(GPS.this, friendlist.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.historybutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "----------");
                Intent intent = new Intent(GPS.this, History.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.rankingbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "----------");
                Intent intent = new Intent(GPS.this, Ranking.class);
                startActivity(intent);
            }
        });


        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 0);
            // Otherwise, setup the chat session
        }



        // LocationManagerを取得
        LocationManager mLocationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Criteriaオブジェクトを生成
        Criteria criteria = new Criteria();

        // Accuracyを指定(低精度)
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        // PowerRequirementを指定(低消費電力)
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        // ロケーションプロバイダの取得
        String provider = mLocationManager.getBestProvider(criteria, true);

        // 取得したロケーションプロバイダを表示
        //TextView tv_provider = (TextView) findViewById(R.id.Provider);
        //tv_provider.setText("Provider: " + provider);

        // LocationListenerを登録
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        mLocationManager.requestLocationUpdates(provider, 0, 0, this);


        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, 0);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    mChatService = new BluetoothChatService(this, mHandler);

                } else {
                    // User did not enable Bluetooth or an error occured
//                    Log.d("BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /*
            FragmentActivity activity = getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connect);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            */
        }
    };




    @Override
    public void onLocationChanged(Location location) {
        // 緯度の表示
        //tv_la = (TextView) findViewById(R.id.Latitude);
        //tv_la.setText("Latitude:" + location.getLatitude());

        // 経度の表示
        //tv_lng = (TextView) findViewById(R.id.Longitude);
        //tv_lng.setText("Longitude:" + location.getLongitude());

        double startLatitude = location.getLatitude();
        double startLongitude = location.getLongitude();
        double endLatitude = 35.664;
        double endLongitude = 139.745;

        // 結果を格納するための配列を生成
        float[] results = new float[3];

        // 距離計算
        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);

        // 結果を表示
        Log.v("TEST", "results[0]: " + results[0]); // 距離（メートル）

        //  distance = (TextView) findViewById(R.id.distance);
        //  distance.setText("distance:" + results[0]);
    }



    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private void sendNotification() {
        Intent intent = new Intent(this, GPS.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                GPS.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.unnamed);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext());
        builder.setContentIntent(contentIntent);
        builder.setTicker("Ticker");
        builder.setSmallIcon(R.drawable.unnamed);
        builder.setContentTitle("");
        builder.setContentText("");
        builder.setLargeIcon(largeIcon);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}