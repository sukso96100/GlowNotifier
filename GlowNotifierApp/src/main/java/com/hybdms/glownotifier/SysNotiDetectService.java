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

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.List;

public class SysNotiDetectService extends AccessibilityService {
private String DEBUGTAG = "SysNotiDetectService";
    private BlacklistDBhelper mHelper = null;
    private Cursor mCursor = null;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

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

        Log.d("DBVALUES", array.toString());

        Log.d(DEBUGTAG, "onAccessibilityEvent");
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            System.out.println("notification: " + event.getText());

            String pkgnameforfilter = event.getPackageName().toString();

            //Filter Toast Out
            Parcelable parcelable = event.getParcelableData();
            if (parcelable instanceof Notification) {
                //Filter Blacklisted Apps' Notifications out
                if (array.toString().contains(pkgnameforfilter)){
                    //Do Nothing
                }
                else{
                //Show GlowOverlay
                Log.d(DEBUGTAG, "Starting GlowOverlay");
                startService(new Intent(SysNotiDetectService.this, GlowOverlay.class));
                }
            }
            else{
                //Do Nothing
            }
        }
    }

    @Override
    protected void onServiceConnected() {
        System.out.println("onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.notificationTimeout = 100;
        info.feedbackType = AccessibilityEvent.TYPES_ALL_MASK;
        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {
        System.out.println("onInterrupt");
    }
}