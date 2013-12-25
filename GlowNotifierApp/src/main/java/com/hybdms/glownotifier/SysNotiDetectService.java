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
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class SysNotiDetectService extends AccessibilityService {
private String DEBUGTAG = "SysNotiDetectService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.d(DEBUGTAG, "onAccessibilityEvent");
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            System.out.println("notification: " + event.getText());
            //Show GlowOverlay
            Log.d(DEBUGTAG, "Starting GlowOverlay");
            startService(new Intent(SysNotiDetectService.this, GlowOverlay.class));
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