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
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Spinner colorentry = (Spinner) findViewById(R.id.colorentry);  //colorentry spinner
        Spinner posentry = (Spinner) findViewById(R.id.posentry);  //posentry spinner
        int colorentry_int = pref.getInt("colorentry",0);
        int posentry_int = pref.getInt("posentry",0);
        colorentry.setSelection(colorentry_int);
        posentry.setSelection(posentry_int);

        //Launch Tutorial Activity If user new to this app
        Boolean firstrun = pref.getBoolean("firstrun", true);
        if (firstrun) {
            Intent guide = new Intent(MainActivity.this, Tutorial.class);
            startActivity(guide);
            SharedPreferences.Editor editor = pref.edit(); // Load Editor
            editor.putBoolean("firstrun", false); //put value
            editor.commit(); // Save value
        }
        else{
            //Do Nothing
        }

        TextView accessibility = (TextView)findViewById(R.id.accessibility);
        accessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accessibility = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(accessibility);
            }
        });

        TextView appinfo = (TextView)findViewById(R.id.appinfo);
        appinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appinfo = new Intent(MainActivity.this, Appinfo.class);
                startActivity(appinfo);
            }
        });

    }

    public void onStop(){
        super.onStop();

        //Save Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        Spinner colorentry = (Spinner) findViewById(R.id.colorentry);
        Spinner posentry = (Spinner) findViewById(R.id.posentry);
        // Input values
        int colorentry_selected_value = colorentry.getSelectedItemPosition();
        int posentry_selected_value = posentry.getSelectedItemPosition();
        editor.putInt("colorentry", colorentry_selected_value);
        editor.putInt("posentry", posentry_selected_value);
        editor.commit(); // Save values
    }
}