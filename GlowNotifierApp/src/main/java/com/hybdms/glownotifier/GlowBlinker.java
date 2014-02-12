package com.hybdms.glownotifier;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class GlowBlinker extends Service {
    private String DEBUGTAG = "GlowBlinker";

    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        int glowblink_int = Integer.parseInt(pref.getString("blinktime", "1"));
        final int glowdelay_int = Integer.parseInt(pref.getString("delaytime", "5000"));
        int colormethod_int = pref.getInt("colormethodentry", 0);

        int autocolor = intent.getIntExtra("autocolorvalue", Color.WHITE);
        String pkgname = intent.getStringExtra("pkgname");

        //Blink Glow "t" times
        int t=0;
        while(t<glowblink_int){
            final Intent i = new Intent(GlowBlinker.this, GlowOverlay.class);
            if(colormethod_int == 1){
                i.putExtra("autocolorvalue", autocolor);
            }
            else if(colormethod_int == 2){
                i.putExtra("pkgname", pkgname);
            }
            else{
                //Do Nothing
            }

                Log.d(DEBUGTAG, "STOP");
            stopService(new Intent(GlowBlinker.this, GlowOverlay.class));
                Log.d(DEBUGTAG, "START");
            startService(i);
            try {
                Thread.sleep(glowdelay_int + glowdelay_int / 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(DEBUGTAG, "Delayed");
                    stopService(new Intent(GlowBlinker.this, GlowOverlay.class));

            t++;
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
