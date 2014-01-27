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
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class ColorSettings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_settings);

        // Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Spinner colormethodentry = (Spinner) findViewById(R.id.color_method_entry);  //posentry spinner
        int colormethodentry_int = pref.getInt("colormethodentry", 0);
        colormethodentry.setSelection(colormethodentry_int);

        TextView pick_fixed = (TextView)findViewById(R.id.pick_fixed);
        pick_fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog();
            }
        });

        TextView colorperapp = (TextView)findViewById(R.id.color_per_app);
        colorperapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ColorSettings.this, ColorPerAppList.class));
            }
        });
    }

    public void onStop(){
        super.onStop();

        //Save Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        Spinner colormethodentry = (Spinner) findViewById(R.id.color_method_entry);
        // Input values
        int colormethodentry_selected_value = colormethodentry.getSelectedItemPosition();

        editor.putInt("colormethodentry", colormethodentry_selected_value);
        editor.commit(); // Save values
    }

    private void showColorPickerDialog() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);  //Load Preference
        int initialColor = pref.getInt("colorvalue", Color.WHITE);  //Load Color Value from Preference

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit(); //Load Editor
                editor.putInt("colorvalue", color); //Save Selected Color
                editor.commit();
            }

        });
        colorPickerDialog.show();

    }

}
