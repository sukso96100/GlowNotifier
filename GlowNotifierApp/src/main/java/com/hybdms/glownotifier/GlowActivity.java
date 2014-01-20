package com.hybdms.glownotifier;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class GlowActivity extends Activity {
    private ImageView mGlowOverlay;
    String DEBUGTAG = "GlowActivity";
    private TimerTask mTask;
    private Timer mTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Flags for showing GlowActivity over lock screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED  | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        //Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        int posentry_int = pref.getInt("posentry",0);
        int ratio_int = pref.getInt("ratiovalue", 50);
        int shape_int = pref.getInt("shapentry", 0);
        int colormethod_int = pref.getInt("colormethodentry", 0);
        int color_int ;
        int clockkinds_int = pref.getInt("clockkinds", 0);
        final boolean closetoggle_boolean = pref.getBoolean("closeglowscreen_toggle", true);
        int glowdelay_int = Integer.parseInt(pref.getString("delaytime", "30000"));
        //Load Color Value
        if(colormethod_int == 0){
            color_int = pref.getInt("colorvalue", Color.WHITE);
        }
        else{
            color_int = getIntent().getIntExtra("autocolorvalue", Color.WHITE);
        }
        //Clock Kinds
        if(clockkinds_int == 0){
            setContentView(R.layout.activity_glow);
        }else{
            setContentView(R.layout.activity_glow_analog);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){

        }
        else{
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }
        //Device Policy Manager
        final DevicePolicyManager mDPM =
                (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);

        //Create Image View
        mGlowOverlay = new ImageView(this);

        //Get Device Screen Width Value
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int devicewidth = metrics.widthPixels;

        //change width ratio value by loaded pref value
        double ratiovalue = ratio_int * 0.01;

getIntent().getIntExtra("autocolorvalue", Color.WHITE);
        GradientDrawable g;
        if(shape_int == 0){
            //Circular Gradient Drawable
            Log.d(DEBUGTAG, "Circular Gradient Drawable");
            g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]
                    { color_int, Color.BLACK });
            g.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            g.setGradientRadius((float) (devicewidth * ratiovalue));
        }
        else{
            //Linear Gradient Drawable
            Log.d(DEBUGTAG, "Linear Gradient Drawable");
            GradientDrawable.Orientation linear_orientation;
            if(posentry_int == 0){
                linear_orientation = GradientDrawable.Orientation.TOP_BOTTOM;
            }
            else{
                linear_orientation = GradientDrawable.Orientation.BOTTOM_TOP;
            }
            g = new GradientDrawable(linear_orientation, new int[]
                    { color_int, Color.BLACK });
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
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.GlowScreenLayout);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            rl.setBackgroundDrawable(g);
        }else{
            rl.setBackground(g);
        }

        final Notification n = (Notification) getIntent().getParcelableExtra("ParcelableData");

        // Get App Icon
        final PackageManager pm = getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo((String) getIntent().getStringExtra("pkgname"), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        Drawable appicon = pm.getApplicationIcon(ai);
        ImageView appiconfield = (ImageView)findViewById(R.id.appicon);
        appiconfield.setImageDrawable(appicon);
        appiconfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disable keyguard
                KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keylock = km.newKeyguardLock(KEYGUARD_SERVICE);
                keylock.disableKeyguard();
                try {
                    //launch notification event
                    n.contentIntent.send();
                    //finish activity
                    finish();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                    finish();
                }
            }
        });
        ImageView close = (ImageView)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(glowdelay_int == 0){
            //Do Nothing
        }
        else{
            // Stop this Activity in a few seconds
            mTask = new TimerTask() {
                @Override
                public void run() {
                    //Turn the Screen Off
                    /*
                    PowerManager.WakeLock wakeLock = pwm.newWakeLock
                            (PowerManager.PARTIAL_WAKE_LOCK, "TAG");
                    wakeLock.acquire();
                    wakeLock.release();
                    */
                    mDPM.lockNow();
                    if(closetoggle_boolean){
                        finish();
                    }else{
                        //Do Nothing
                    }
                }
            };
            mTimer = new Timer();
            mTimer.schedule(mTask, glowdelay_int);
        }
    }
}