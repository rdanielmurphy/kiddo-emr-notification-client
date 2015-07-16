package com.example.danielmurphy.kiddoemrclient;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

public class GcmMessageHandler extends IntentService {

    private TipsDbAdpater dbHelper;
    private String mes;
    private Handler handler;
	public GcmMessageHandler() {
		super("GcmMessageHandler");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        mes = extras.getString("title");
        showNotification();
        Log.i("GCM", "Received : (" +messageType+")  "+extras.getString("title"));

        GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	public void showNotification(){
		handler.post(new Runnable() {
		    public void run() {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.newicon)
                                .setContentTitle("Health Tips")
                                .setContentText(mes);

                Intent resultIntent = new Intent(getApplicationContext(), HealthTipViewer.class);
                resultIntent.putExtra("message", mes);

                dbHelper = new TipsDbAdpater(getApplicationContext());
                dbHelper.open();
                dbHelper.insertMessage(mes, new Date().getTime());

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                stackBuilder.addParentStack(AndroidListViewCursorAdaptorActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
		    }
		 });
	}
}
