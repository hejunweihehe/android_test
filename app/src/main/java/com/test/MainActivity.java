package com.test;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.com.test.R;

public class MainActivity extends FragmentActivity {
    @BindView(R.id.btn_click)
    public Button button;
    @BindView(R.id.btn_cancel)
    public Button cancelBtn;
    NotificationManager mNotificationManager;

    private String CHANNEL_ID = "test.com.test.notification.channel";
    private static final String TAG = "MainActivity";
    boolean is1 = false;
    Button btn_click;
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getNotification();
                if (isAndroidOOrHigher()) {
                    createChannel();
                    //createChannelGroup();
                }
                Intent resultIntent = new Intent(MainActivity.this, SecondActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);

                Intent secondIntent = new Intent();
                ComponentName componentName = new ComponentName("com.example.android.mediasession", "com.example.android.mediasession.ui.MainActivity");
                secondIntent.setComponent(componentName);

                /**
                 * addParentStack的参数的意思是，参数中的所有父Activity都会被添加到后退栈中。
                 *
                 * 1.如果没有为SecondActivity设置parentActivity，那么不管addParentStack设置为哪个，都不会再回到MainActivity了
                 *
                 * 2.如果SecondActivity设置parentActivity为MainActivity时，addParentStack设置为SecondActivity，最后会返回到MainActivity
                 *
                 * 3.如果SecondActivity设置parentActivity为MainActivity时，addParentStack设置为MainActivity，最后不会返回MainActivity，直接回到桌面
                 *
                 */
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                stackBuilder.addNextIntent(resultIntent);
                stackBuilder.addNextIntent(secondIntent);

                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                final Notification.Builder nb = new Notification.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("content text")
                        .setContentIntent(resultPendingIntent);
                mNotificationManager.notify(1, nb.build());
            }
        });
    }

    private void getNotification() {
        if (isAndroidOOrHigher()) {
            Toast.makeText(this, "isAndroidOOrHigher", Toast.LENGTH_LONG).show();
            createChannel();
            //createChannelGroup();
        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, SecondActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SecondActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);//一定要加一个intent，不然会报异常IllegalStateException: No intents added to TaskStackBuilder;
        stackBuilder.addNextIntent(new Intent(this, Activity3.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    private boolean isAndroidOOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    // Does nothing on versions of Android earlier than O.
    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            // The user-visible name of the channel.
            CharSequence name = "MediaSession2222";
            // The user-visible description of the channel.
            String description = "MediaSession and MediaPlayer2222222";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mNotificationManager.createNotificationChannel(mChannel);
            Log.d(TAG, "createChannel: New channel created");
        } else {
            Log.d(TAG, "createChannel: Existing channel reused");
        }
    }

}
