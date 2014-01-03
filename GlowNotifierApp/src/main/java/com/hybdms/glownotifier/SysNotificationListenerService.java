package com.hybdms.glownotifier;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

//This is for Android 4.3 Jelly-Bean or any later versions
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SysNotificationListenerService extends NotificationListenerService {
    private String DEBUGTAG = "SysNotificationListenerService";


    //When new notification posted on status ber
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getNotification()!=null){
        //Show GlowOverlay
        Log.d(DEBUGTAG, "+++++++++++++++++++++++++++++++++++++++++++++++");
        Log.d(DEBUGTAG, "Starting GlowOverlay");
        startService(new Intent(this, GlowOverlay.class));
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(DEBUGTAG, "+++++++++++++++++++++++++++++++++++++++++++++++");
        Log.d(DEBUGTAG, "onNotificationRemoved");
    }
/*
    @Override
    public IBinder onBind(Intent intent) {
      //  super.onBind(intent);
        Log.d(DEBUGTAG, "onBind");
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return super.onBind(intent);
    }
*/

}
