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
