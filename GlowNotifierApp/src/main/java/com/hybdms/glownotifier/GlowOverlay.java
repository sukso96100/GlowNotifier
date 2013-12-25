package com.hybdms.glownotifier;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.graphics.PixelFormat;

import java.util.Timer;
import java.util.TimerTask;

public class GlowOverlay extends Service {
    private ImageView mGlowOverlay;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private TimerTask mTask;
    private Timer mTimer;
    private String DEBUGTAG = "GlowOverlay";


    private OnTouchListener mViewTouchListener = new OnTouchListener() {
        @Override public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                //GlowOverlay Touch Event



            }
            return true;
        }
    };

    @Override
    public IBinder onBind(Intent arg0) { return null; }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(DEBUGTAG, "Service Started");

        //Create Image View
        mGlowOverlay = new ImageView(this);
        mGlowOverlay.setImageResource(R.drawable.glow_red_top);

        //Get width and height from the image
        int WRAP_CONTENT_WIDTH = mGlowOverlay.getDrawable().getIntrinsicWidth();
        int WRAP_CONTENT_HEIGHT = mGlowOverlay.getDrawable().getIntrinsicHeight();

        mGlowOverlay.setOnTouchListener(mViewTouchListener);
        //Settings for Overlay
        mParams = new WindowManager.LayoutParams(
                WRAP_CONTENT_WIDTH,
                WRAP_CONTENT_HEIGHT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //Not Focusable
                PixelFormat.TRANSLUCENT);     //Transparent
        mParams.gravity = Gravity.TOP | Gravity.CENTER; //Gravity of The Overlay
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mGlowOverlay, mParams);

        // Stop this Service in 5 seconds
        mTask = new TimerTask() {
            @Override
            public void run() {
                stopSelf();
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 5000);
    }
    @Override
    public void onDestroy() {
        //Remove the View
        if(mWindowManager != null) {
            if(mGlowOverlay != null) mWindowManager.removeView(mGlowOverlay);
        }
        super.onDestroy();

    }
}