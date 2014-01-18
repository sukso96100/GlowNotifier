package com.hybdms.glownotifier;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Spinner;

public class GlowScreenSettings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glow_screen_settings);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        CheckBox toggle = (CheckBox)findViewById(R.id.toggle);
        Spinner clockkinds = (Spinner)findViewById(R.id.clockkindsentry);
        boolean toggle_boolean = pref.getBoolean("glowscreen_toggle", false);
        int clockkinds_int = pref.getInt("clockkinds", 0);
        toggle.setChecked(toggle_boolean);
        clockkinds.setSelection(clockkinds_int);

    }

    public void onStop(){
        super.onStop();
        //Save Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        CheckBox toggle = (CheckBox)findViewById(R.id.toggle);
        Spinner clockkinds = (Spinner)findViewById(R.id.clockkindsentry);
        // Input values
        editor.putBoolean("glowscreen_toggle", toggle.isChecked());
        editor.putInt("clockkinds", clockkinds.getSelectedItemPosition());
        editor.commit(); // Save values
    }
}
