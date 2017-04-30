package com.coverevi.coverevi.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

// import wseemann.media.FFmpegMediaPlayer;

public class StreamService extends Service {
    private final String TAG = "StreamService";

    // Broadcast actions
    public final static String CONTROL_STATUS = "ControlStatus";

    MediaPlayer mediaPlayer;
    Context context;
    Notification n;

    @Override
    public void onCreate() {
        super.onCreate();

        // mediaPlayer = new FFmpegMediaPlayer();
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource("http://95.173.161.133:9858/");
            mediaPlayer.prepareAsync();
        } catch (java.io.IOException e) {
            Log.i(TAG, e.getMessage());
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                sendBroadcast(CONTROL_STATUS, "Çalıyor..");
            }
        });

        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        sendBroadcast(CONTROL_STATUS, "Ara belleğe alınıyor..");
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        sendBroadcast(CONTROL_STATUS, "Çalıyor..");
                        break;
                }

                return false;
            }
        });

        /*
        context = getApplicationContext();

        Intent nIntent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, nIntent, 0);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Cover Evi")
                .setContentText("Çalıyor");


        n = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(5315, n);
        */
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendBroadcast(String action, String message) {
        Intent intent1 = new Intent();
        intent1.setAction(action);
        intent1.putExtra("message", message);
        sendBroadcast(intent1);
    }
}
