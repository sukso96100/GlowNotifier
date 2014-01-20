package com.hybdms.glownotifier;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class DevicePolicyReceiver extends DeviceAdminReceiver {


    @Override
    public void onEnabled(Context context, Intent intent) {
        //Load Preference Value
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        editor.putBoolean("deviceadmin", true);
        editor.commit(); // Save values
        Toast.makeText(context, context.getString(R.string.deviceadmin_on), Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onDisabled(Context context, Intent intent) {
        //Load Preference Value
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        editor.putBoolean("deviceadmin", false);
        editor.commit(); // Save values
        Toast.makeText(context, context.getString(R.string.deviceadmin_off), Toast.LENGTH_SHORT).show();
    }
}
