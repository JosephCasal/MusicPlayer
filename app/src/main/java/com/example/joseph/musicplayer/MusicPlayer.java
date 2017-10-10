package com.example.joseph.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

/**
 * Created by joseph on 10/10/17.
 */

public class MusicPlayer extends Service {

    public static boolean IS_SERVICE_RUNNING = false;
    MediaPlayer player;
    Notification notification;
    NotificationCompat.Builder notificationBuilder;
    private static final int Id = 1;

    public MusicPlayer() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.toxicity);
        player.setLooping(true);
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals("car_action")) {
            carNotification(intent.getStringExtra("car"));
        }else if (intent.getAction().equals("foreground_action")) {
            startInForeground();
            player.start();
        }else if (intent.getAction().equals("play_action")) {
            player.start();
        }
        else if (intent.getAction().equals("pause_action")) {
            if (player.isPlaying()) {
                player.pause();
            }
        }
        else {
            player.stop();
            stopSelf();
        }

        return START_STICKY;
    }

    public void carNotification(String car) {
        Notification carNotification = null;
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction("main_action");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT );

        NotificationCompat.Builder notificationBuilder= new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Car")
                .setContentTitle("Car")
                .setContentText(car)
                .setContentIntent(pendingIntent);

        carNotification = notificationBuilder.build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(5678, carNotification);
    }

    public void startInForeground() {

        //pending intent to launch notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction("main_action");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT );

        //pending intent to play song
        Intent playIntent = new Intent(this,MusicPlayer.class);
        playIntent.setAction("play_action");
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent,PendingIntent.FLAG_CANCEL_CURRENT );

        //pending intent to pause song
        Intent pauseIntent = new Intent(this, MusicPlayer.class);
        pauseIntent.setAction("pause_action");
        PendingIntent ppauseIntent =  PendingIntent.getService(this, 0,
                pauseIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //pending intent to stop song
        Intent stopIntent = new Intent(this, MusicPlayer.class);
        stopIntent.setAction("stop_action");
        PendingIntent pstopIntent =  PendingIntent.getService(this, 0,
                stopIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        //build the notification
        notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Music Player")
                .setContentTitle("Music Player")
                .setContentText("Song Playing")
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_media_pause, "Pause", ppauseIntent)
                .addAction(R.drawable.ic_media_play, "Play", pplayIntent)
                .addAction(R.drawable.ic_media_stop, "Stop", pstopIntent);

        notification = notificationBuilder.build();

        startForeground(Id, notification);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

}
