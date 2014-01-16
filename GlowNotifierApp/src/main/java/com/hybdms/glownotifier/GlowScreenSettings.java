package com.hybdms.glownotifier;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.CheckBox;

public class GlowScreenSettings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glow_screen_settings);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        CheckBox toggle = (CheckBox)findViewById(R.id.toggle);
        boolean toggle_boolean = pref.getBoolean("glowscreen_toggle", false);
        toggle.setChecked(toggle_boolean);

    }

    public void onStop(){
        super.onStop();
        //Save Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        CheckBox toggle = (CheckBox)findViewById(R.id.toggle);
        // Input values
        editor.putBoolean("glowscreen_toggle", toggle.isChecked());
        editor.commit(); // Save values
    }
}
