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