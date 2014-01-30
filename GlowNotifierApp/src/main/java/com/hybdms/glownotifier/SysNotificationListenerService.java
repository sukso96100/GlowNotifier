/*
 * GlowNotifier Application for Android
 * Copyright (C) 2013 Youngbin Han<sukso96100@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hybdms.glownotifier;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PowerManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

//This is for Android 4.3 Jelly-Bean or any later versions
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SysNotificationListenerService extends NotificationListenerService {
    private String DEBUGTAG = "SysNotificationListenerService";
    private BlacklistDBhelper mHelper = null;
    private Cursor mCursor = null;

    //When new notification posted on status ber
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        int colormethod_int = pref.getInt("colormethodentry", 0);
        boolean glowscreen_toggle = pref.getBoolean("glowscreen_toggle", true);
        int glowblink_int = Integer.parseInt(pref.getString("blinktime", "1"));
        final int glowdelay_int = Integer.parseInt(pref.getString("delaytime", "5000"));

        // Load BlackList
        mHelper = new BlacklistDBhelper(this);
        mCursor = mHelper.getWritableDatabase().rawQuery(
                "SELECT _ID, pkgname FROM blacklist ORDER BY pkgname", null);

        List<String> array = new ArrayList<String>();
        while (mCursor.moveToNext()) {
            String uname = mCursor.getString(mCursor.getColumnIndex("pkgname"));
            array.add(uname);
        }
        mCursor.close();
        mHelper.close();

        if (sbn.getNotification()!=null){
            String pkgnameforfilter = sbn.getPackageName().toString();
            //Filter Blacklisted Apps' Notifications out
            if (array.toString().contains(pkgnameforfilter)){
                //Do Nothing
            }
            else{
                //Get Device Screen Status
                PowerManager pwm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = pwm.isScreenOn();

                // Get App Icon
                final PackageManager pm = getApplicationContext().getPackageManager();
                ApplicationInfo ai;
                try {
                    ai = pm.getApplicationInfo((String) sbn.getPackageName(), 0);
                } catch (final PackageManager.NameNotFoundException e) {
                    ai = null;
                }
                Drawable appicon = pm.getApplicationIcon(ai);
                //Get Average Color
                int autocolor = BitmapAverageColor.getAverageColorCodeRGB(appicon);

                if(isScreenOn){
                    //If the Screen is On
                    //Blink Glow 't' times

                    for(int t=1; t<glowblink_int; t++){
                        //Stop GlowOverlay first
                        stopService(new Intent(this, GlowOverlay.class));
                        //Show GlowOverlay
                        Log.d(DEBUGTAG, "Starting GlowOverlay");
                        Intent i = new Intent(SysNotificationListenerService.this, GlowOverlay.class);
                        if(colormethod_int == 1){
                            i.putExtra("autocolorvalue", autocolor);
                        }
                        else if(colormethod_int == 2){
                            i.putExtra("pkgname", sbn.getPackageName());
                        }
                        else{
                            //Do Nothing
                        }
                        startService(i);
                        try {
                            Thread.sleep(glowdelay_int + glowdelay_int/4);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    if(glowscreen_toggle){
                    //If the Screen is Off
                    //Wake the Screen Up
                    PowerManager.WakeLock wakeLock = pwm.newWakeLock
                            ((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
                    wakeLock.acquire();
                    //Show GlowActivity
                    Log.d(DEBUGTAG, "Starting GlowActivity");
                    Intent a = new Intent(SysNotificationListenerService.this, GlowActivity.class);
                    if(colormethod_int == 1){
                        a.putExtra("autocolorvalue", autocolor);
                    }
                    else{
                        //Do Nothing
                    }
                    a.putExtra("ParcelableData", sbn.getNotification());
                    a.putExtra("pkgname", sbn.getPackageName());
                    a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(a);
                    }
                    else{
                        //Do Nothing
                    }
                }
            }
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
