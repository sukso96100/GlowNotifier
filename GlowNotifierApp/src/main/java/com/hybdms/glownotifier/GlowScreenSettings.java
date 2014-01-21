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

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class GlowScreenSettings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glow_screen_settings);
        // Load Preference Value
        final SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        CheckBox toggle = (CheckBox)findViewById(R.id.toggle);
        Spinner clockkinds = (Spinner)findViewById(R.id.clockkindsentry);
        EditText glowdelay = (EditText) findViewById(R.id.delaytime);
        CheckBox closetoggle = (CheckBox)findViewById(R.id.closetoggle);
        CheckBox autoff = (CheckBox)findViewById(R.id.screenoff);

        boolean toggle_boolean = pref.getBoolean("glowscreen_toggle", false);
        int clockkinds_int = pref.getInt("clockkinds", 0);
        boolean closetoggle_boolean = pref.getBoolean("closeglowscreen_toggle", false);
        String delaytime_String = pref.getString("glowscreendelay", "30000");
        boolean screenoff_boolean = pref.getBoolean("autoscreenoff", false);
        final boolean admin = pref.getBoolean("deviceadmin", false);

        toggle.setChecked(toggle_boolean);
        clockkinds.setSelection(clockkinds_int);
        closetoggle.setChecked(closetoggle_boolean);
        glowdelay.setText(delaytime_String);
        autoff.setChecked(screenoff_boolean);


        //Device Policy Manager
        final DevicePolicyManager mDPM =
                (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);

        final ComponentName mAdminName = new ComponentName(this, DevicePolicyReceiver.class);

        autoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            getString(R.string.deviceadmin_desc));
                    startActivity(intent);
            }
        });

    }

    public void onStop(){
        super.onStop();
        //Save Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        CheckBox toggle = (CheckBox)findViewById(R.id.toggle);
        Spinner clockkinds = (Spinner)findViewById(R.id.clockkindsentry);
        EditText glowdelay = (EditText) findViewById(R.id.delaytime);
        CheckBox closetoggle = (CheckBox)findViewById(R.id.closetoggle);
        CheckBox autoff = (CheckBox)findViewById(R.id.screenoff);
        // Input values
        editor.putBoolean("glowscreen_toggle", toggle.isChecked());
        editor.putInt("clockkinds", clockkinds.getSelectedItemPosition());
        editor.putString("glowscreendelay", glowdelay.getText().toString());
        editor.putBoolean("closeglowscreen_toggle", closetoggle.isChecked());
        editor.putBoolean("autoscreenoff", autoff.isChecked());
        editor.commit(); // Save values
    }
}
