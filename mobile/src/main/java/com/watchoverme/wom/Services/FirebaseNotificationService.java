package com.watchoverme.wom.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.watchoverme.wom.Activities.MainActivity;
import com.watchoverme.wom.R;

public class FirebaseNotificationService extends FirebaseMessagingService {

    MainActivity mainActivity = new MainActivity();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder.setContentTitle("WOMNotification");
//        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
//        notificationBuilder.setAutoCancel(true);
//        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        notificationBuilder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,notificationBuilder.build());
//        mainActivity.updateNotificationList(remoteMessage.getNotification().getBody());
        Bundle bundle = new Bundle();
        bundle.putString("text",remoteMessage.getNotification().getBody());

        Intent intent1 = new Intent();
        intent1.setAction("ACTION_STRING_ACTIVITY");
        intent1.putExtra("alertNotices",bundle);

        sendBroadcast(intent1);
    }

}
