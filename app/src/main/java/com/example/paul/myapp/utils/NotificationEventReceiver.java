package com.example.paul.myapp.utils;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;

import com.example.paul.myapp.R;
import com.example.paul.myapp.view.MainActivity;


public class NotificationEventReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("text");


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int image = 0;
        if(title.contains("for"))  image=R.drawable.ic_fun;
        else image=R.drawable.ic_study;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(image)
                .setContentText("Click to make an event")
                .setAutoCancel(true);


        notificationManager.notify(100, builder.build());
    }


}