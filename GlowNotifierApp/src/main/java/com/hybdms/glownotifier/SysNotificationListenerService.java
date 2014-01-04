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
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
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
        //Show GlowOverlay
        Log.d(DEBUGTAG, "+++++++++++++++++++++++++++++++++++++++++++++++");
        Log.d(DEBUGTAG, "Starting GlowOverlay");
        startService(new Intent(this, GlowOverlay.class));
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
