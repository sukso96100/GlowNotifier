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
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUGTAG, "Service Started");

        //Create Image View
        mGlowOverlay = new ImageView(this);

        //Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        int posentry_int = pref.getInt("posentry",0);
        int ratio_int = pref.getInt("ratiovalue", 50);
        int shape_int = pref.getInt("shapentry", 0);
        int colormethod_int = pref.getInt("colormethodentry", 0);
        int color_int = 0;
        //Load Color Value
        if(colormethod_int == 0){
            color_int = pref.getInt("colorvalue", Color.WHITE);
        }
        else if(colormethod_int == 1){
            color_int = intent.getIntExtra("autocolorvalue", Color.WHITE);
        }
        else{
            SharedPreferences colorpref = getSharedPreferences("colorpref", Context.MODE_PRIVATE);
            String color_key = "color" + intent.getStringExtra("pkgname");
            color_int = colorpref.getInt(color_key, Color.WHITE);
        }

        //Get Device Screen Width Value
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int devicewidth = metrics.widthPixels;

        //change width ratio value by loaded pref value
        double ratiovalue = ratio_int * 0.01;

        GradientDrawable g;
        if(shape_int == 0){
            //Circular Gradient Drawable
            g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]
                    { color_int, Color.argb(0, Color.red(color_int), Color.green(color_int), Color.blue(color_int)) });
            g.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            g.setGradientRadius((float) (devicewidth * ratiovalue));
        }
        else{
            //Linear Gradient Drawable
            GradientDrawable.Orientation linear_orientation;
            if(posentry_int == 0){
                linear_orientation = GradientDrawable.Orientation.TOP_BOTTOM;
            }
            else{
                linear_orientation = GradientDrawable.Orientation.BOTTOM_TOP;
            }
            g = new GradientDrawable(linear_orientation, new int[]
                    { color_int, Color.argb(0, Color.red(color_int), Color.green(color_int), Color.blue(color_int)) });
            g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            g.setSize(devicewidth, (int) (devicewidth * ratiovalue));
        }

        if(posentry_int == 0){
            //If posentry value is 0 (Top)
            g.setGradientCenter(0.5f, 0.0f);
        }
        else{
            //If posentry value is 1 (Bottom)
            g.setGradientCenter(0.5f, 1.0f);
        }

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
        //  mGlowOverlay.setScaleType(ImageView.ScaleType.FIT_XY);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mGlowOverlay, mParams);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        int glowdelay_int = Integer.parseInt(pref.getString("delaytime", "5000"));
        // Stop this Service in a few seconds
        mTask = new TimerTask() {
            @Override
            public void run() {
                stopSelf();
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, glowdelay_int);

        //Remove the View
        if(mWindowManager != null) {
            if(mGlowOverlay != null) mWindowManager.removeView(mGlowOverlay);
        }
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