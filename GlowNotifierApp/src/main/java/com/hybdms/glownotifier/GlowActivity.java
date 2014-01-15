package com.hybdms.glownotifier;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GlowActivity extends Activity {
    private ImageView mGlowOverlay;
    String DEBUGTAG = "GlowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glow);

        //Disable Keyguard
        KeyguardManager.KeyguardLock k1;
        KeyguardManager km =(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        k1= km.newKeyguardLock("IN");
        k1.disableKeyguard();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){

        }
        else{
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }

        //Create Image View
        mGlowOverlay = new ImageView(this);

        //Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        int posentry_int = pref.getInt("posentry",0);
        int ratio_int = pref.getInt("ratiovalue", 5);
     // int glowdelay_int = Integer.parseInt(pref.getString("delaytime", "5000"));
        int shape_int = pref.getInt("shapentry", 0);
        int colormethod_int = pref.getInt("colormethodentry", 0);
        int color_int ;
        //Load Color Value
        if(colormethod_int == 0){
            color_int = pref.getInt("colorvalue", Color.WHITE);
        }
        else{
            color_int = getIntent().getIntExtra("autocolorvalue", Color.WHITE);
        }

        //Get Device Screen Width Value
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int devicewidth = metrics.widthPixels;
        int defaultdistance = devicewidth / 2;

        //change width ratio value by loaded pref value
        double ratiovalue;
        if(ratio_int == 0 ){
            ratiovalue = 0.3;
        }
        else if(ratio_int == 1){
            ratiovalue = 0.4;
        }
        else if(ratio_int == 2){
            ratiovalue = 0.5;
        }
        else if(ratio_int == 3){
            ratiovalue = 0.6;
        }
        else if(ratio_int == 4){
            ratiovalue = 0.7;
        }
        else if(ratio_int == 5){
            ratiovalue = 1.0;
        }
        else if(ratio_int == 6){
            ratiovalue = 1.25;
        }
        else if(ratio_int == 7){
            ratiovalue = 1.5;
        }
        else if(ratio_int == 8){
            ratiovalue = 1.7;
        }
        else if(ratio_int == 9){
            ratiovalue = 2.0;
        }
        else{
            ratiovalue = 2.25;
        }
getIntent().getIntExtra("autocolorvalue", Color.WHITE);
        GradientDrawable g;
        if(shape_int == 0){
            //Circular Gradient Drawable
            Log.d(DEBUGTAG, "Circular Gradient Drawable");
            g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]
                    { color_int, Color.argb(0, Color.red(color_int), Color.green(color_int), Color.blue(color_int)) });
            g.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            g.setGradientRadius((float) (defaultdistance * ratiovalue));
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
                    { color_int, Color.argb(0, Color.red(color_int), Color.green(color_int), Color.blue(color_int)) });
            g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            g.setSize(devicewidth, (int) (defaultdistance * ratiovalue));
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
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //Gravity of The Overlay
        if(posentry_int == 0){
            //If posentry value is 0 (Top)
            lp.addRule(RelativeLayout.ALIGN_TOP);
        }
        else{
            //If posentry value is 1 (Bottom)
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        rl.addView(mGlowOverlay, lp);

        //Set Notification Text
        TextView notitext = (TextView)findViewById(R.id.notitext);
        String NotiString = getIntent().getStringExtra("notistring");
        notitext.setText(NotiString);

        //Long Click Event
        //Long Click Anywhere to launch event
        final Notification n = (Notification) getIntent().getParcelableExtra("ParcelableData");
        rl.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                try {
                    n.contentIntent.send();
                    finish();
                } catch (Exception e) {
                    finish();
                }
                return false;
            }
        });
    }
}