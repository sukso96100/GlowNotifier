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
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
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

    @Override
    public IBinder onBind(Intent arg0) { return null; }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(DEBUGTAG, "Service Started");

        //Create Image View
        mGlowOverlay = new ImageView(this);

        //Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        int color_int = pref.getInt("colorvalue", Color.WHITE);
        int posentry_int = pref.getInt("posentry",0);
        int widthentry_int = pref.getInt("widthentry", 5);
        int heightentry_int = pref.getInt("heightentry", 5);
        int glowdelay_int = Integer.parseInt(pref.getString("delaytime", "5000"));

        //Get Device Screen Width Value
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int devicewidth = metrics.widthPixels;

        //Gradient Drawable
        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { color_int, Color.TRANSPARENT });
        g.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        g.setGradientRadius(devicewidth/2);
            if(posentry_int == 0){
                //If posentry value is 0 (Top)
                g.setGradientCenter(0.5f, 0.0f);
            }
            else{
                //If posentry value is 1 (Bottom)
                g.setGradientCenter(0.5f, 1.0f);
            }

        //change width ratio value by loaded pref value
        double widthvalue;
        if(widthentry_int == 0 ){
            widthvalue = 0.3;
        }
        else if(widthentry_int == 1){
            widthvalue = 0.4;
        }
        else if(widthentry_int == 2){
            widthvalue = 0.5;
        }
        else if(widthentry_int == 3){
            widthvalue = 0.6;
        }
        else if(widthentry_int == 4){
            widthvalue = 0.7;
        }
        else if(widthentry_int == 5){
            widthvalue = 1.0;
        }
        else if(widthentry_int == 6){
            widthvalue = 1.25;
        }
        else if(widthentry_int == 7){
            widthvalue = 1.5;
        }
        else if(widthentry_int == 8){
            widthvalue = 1.7;
        }
        else if(widthentry_int == 9){
            widthvalue = 2.0;
        }
        else{
            widthvalue = 2.25;
        }

        //change height ratio value by loaded pref value
        double heightvalue;
        if(heightentry_int == 0 ){
            heightvalue = 0.3;
        }
        else if(heightentry_int == 1){
            heightvalue = 0.4;
        }
        else if(heightentry_int == 2){
            heightvalue = 0.5;
        }
        else if(heightentry_int == 3){
            heightvalue = 0.6;
        }
        else if(heightentry_int == 4){
            heightvalue = 0.7;
        }
        else if(heightentry_int == 5){
            heightvalue = 1.0;
        }
        else if(heightentry_int == 6){
            heightvalue = 1.25;
        }
        else if(heightentry_int == 7){
            heightvalue = 1.5;
        }
        else if(heightentry_int == 8){
            heightvalue = 1.7;
        }
        else if(heightentry_int == 9){
            heightvalue = 2.0;
        }
        else{
            heightvalue = 2.25;
        }

        g.setBounds(0,0, (int) (g.getIntrinsicWidth()*widthvalue), (int) (g.getIntrinsicHeight()*heightvalue));
        mGlowOverlay.setImageDrawable(g);

        //Get width and height from the image
        int WRAP_CONTENT_WIDTH = mGlowOverlay.getDrawable().getIntrinsicWidth();
        int WRAP_CONTENT_HEIGHT = mGlowOverlay.getDrawable().getIntrinsicHeight();

        //Settings for Overlay
        mParams = new WindowManager.LayoutParams(
                WRAP_CONTENT_WIDTH,
                WRAP_CONTENT_HEIGHT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE //Not Focusable
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, //GlowOverlay Never Receives Touch Input
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

        // Stop this Service in a few seconds
        mTask = new TimerTask() {
            @Override
            public void run() {
                stopSelf();
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, glowdelay_int);
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