package com.hybdms.glownotifier;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        //Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        int colorentry_int = pref.getInt("colorentry",0);
        int posentry_int = pref.getInt("posentry",0);

        //Create Image View
        mGlowOverlay = new ImageView(this);

        if(posentry_int == 0){
            //If posentry value is 0 (Top)
            if(colorentry_int == 0){
                //Red
                mGlowOverlay.setImageResource(R.drawable.glow_red_top);
            }
            else if(colorentry_int == 1){
                //Orange
                mGlowOverlay.setImageResource(R.drawable.glow_orange_top);
            }
            else if(colorentry_int == 2){
                //Yellow
                mGlowOverlay.setImageResource(R.drawable.glow_yellow_top);
            }
            else if(colorentry_int == 3){
                //Green
                mGlowOverlay.setImageResource(R.drawable.glow_green_top);
            }
            else if(colorentry_int == 4){
                //Blue
                mGlowOverlay.setImageResource(R.drawable.glow_blue_top);
            }
            else if(colorentry_int == 5){
                //Indigo Blue
                mGlowOverlay.setImageResource(R.drawable.glow_indigoblue_top);
            }
            else{
                //Purple
                mGlowOverlay.setImageResource(R.drawable.glow_purple_top);
            }

        }
        else{
            //If posentry value is 1 (Bottom)
            if(colorentry_int == 0){
                //Red
                mGlowOverlay.setImageResource(R.drawable.glow_red_bottom);
            }
            else if(colorentry_int == 1){
                //Orange
                mGlowOverlay.setImageResource(R.drawable.glow_orange_bottom);
            }
            else if(colorentry_int == 2){
                //Yellow
                mGlowOverlay.setImageResource(R.drawable.glow_yellow_bottom);
            }
            else if(colorentry_int == 3){
                //Green
                mGlowOverlay.setImageResource(R.drawable.glow_green_bottom);
            }
            else if(colorentry_int == 4){
                //Blue
                mGlowOverlay.setImageResource(R.drawable.glow_blue_bottom);
            }
            else if(colorentry_int == 5){
                //Indigo Blue
                mGlowOverlay.setImageResource(R.drawable.glow_indigoblue_bottom);
            }
            else{
                //Purple
                mGlowOverlay.setImageResource(R.drawable.glow_purple_bottom);
            }
        }


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

        //Gravity of The Overlay
        if(posentry_int == 0){
            //If posentry value is 0 (Top)
            mParams.gravity = Gravity.TOP | Gravity.CENTER;
        }
        else{
            //If posentry value is 1 (Bottom)
            mParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        }

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