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
        int colorentry_int = pref.getInt("colorentry", 0);
        int posentry_int = pref.getInt("posentry",0);
        int widthentry_int = pref.getInt("widthentry", 5);
        int heightentry_int = pref.getInt("heightentry", 5);

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



        //change WRAP_CONTENT_WIDTH value by loaded pref value
        int widthvalue;
        if(widthentry_int == 0 ){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 0.3);
        }
        else if(widthentry_int == 1){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 0.4);
        }
        else if(widthentry_int == 2){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 0.5);
        }
        else if(widthentry_int == 3){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 0.6);
        }
        else if(widthentry_int == 4){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 0.7);
        }
        else if(widthentry_int == 5){
            widthvalue = WRAP_CONTENT_WIDTH;
        }
        else if(widthentry_int == 6){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 1.25);
        }
        else if(widthentry_int == 7){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 1.5);
        }
        else if(widthentry_int == 8){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 1.7);
        }
        else if(widthentry_int == 9){
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 2.0);
        }
        else{
            widthvalue = (int) (WRAP_CONTENT_WIDTH * 2.25);
        }

        //change WRAP_CONTENT_height value by loaded pref value
        int heightvalue;
        if(heightentry_int == 0 ){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 0.3);
        }
        else if(heightentry_int == 1){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 0.4);
        }
        else if(heightentry_int == 2){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 0.5);
        }
        else if(heightentry_int == 3){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 0.6);
        }
        else if(heightentry_int == 4){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 0.7);
        }
        else if(heightentry_int == 5){
            heightvalue = WRAP_CONTENT_HEIGHT;
        }
        else if(heightentry_int == 6){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 1.25);
        }
        else if(heightentry_int == 7){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 1.5);
        }
        else if(heightentry_int == 8){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 1.7);
        }
        else if(heightentry_int == 9){
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 2.0);
        }
        else{
            heightvalue = (int) (WRAP_CONTENT_HEIGHT * 2.25);
        }


        mGlowOverlay.setOnTouchListener(mViewTouchListener);
        //Settings for Overlay
        mParams = new WindowManager.LayoutParams(
                widthvalue,
                heightvalue,
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
        mGlowOverlay.setScaleType(ImageView.ScaleType.FIT_XY);
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