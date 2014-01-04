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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Build;
import android.widget.EditText;
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
        Spinner widthentry = (Spinner) findViewById(R.id.widthentry); //widthentry spinner
        Spinner heightentry = (Spinner) findViewById(R.id.heightentry); //heightentry spinner
        EditText glowdleay = (EditText) findViewById(R.id.delaytime); //delaytime Edittext
        int colorentry_int = pref.getInt("colorentry",0);
        int posentry_int = pref.getInt("posentry",0);
        int widthentry_int = pref.getInt("widthentry", 5);
        int heightentry_int = pref.getInt("heightentry", 5);
        String delaytime_String = pref.getString("delaytime", "5000");
        colorentry.setSelection(colorentry_int);
        posentry.setSelection(posentry_int);
        widthentry.setSelection(widthentry_int);
        heightentry.setSelection(heightentry_int);
        glowdleay.setText(delaytime_String);

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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
                    Intent accessibility = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(accessibility);
                }
                else{
                    Intent notiintent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    startActivity(notiintent);
                }
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

        TextView blacklist = (TextView)findViewById(R.id.blacklist);
        blacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent blacklist = new Intent(MainActivity.this, Blacklist.class);
                startActivity(blacklist);
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
        Spinner widthentry = (Spinner) findViewById(R.id.widthentry);
        Spinner heightentry = (Spinner) findViewById(R.id.heightentry);
        EditText glowdelay = (EditText) findViewById(R.id.delaytime);
        // Input values
        int colorentry_selected_value = colorentry.getSelectedItemPosition();
        int posentry_selected_value = posentry.getSelectedItemPosition();
        int widthentry_selected_value = widthentry.getSelectedItemPosition();
        int hedightentry_selected_value = heightentry.getSelectedItemPosition();
        String delaytime_edited_value = glowdelay.getText().toString();

        editor.putInt("colorentry", colorentry_selected_value);
        editor.putInt("posentry", posentry_selected_value);
        editor.putInt("widthentry", widthentry_selected_value);
        editor.putInt("heightentry", hedightentry_selected_value);
        editor.putString("delaytime", delaytime_edited_value);
        editor.commit(); // Save values
    }

    //ActionBar Action Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_preview:
                startService(new Intent(this, GlowOverlay.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}