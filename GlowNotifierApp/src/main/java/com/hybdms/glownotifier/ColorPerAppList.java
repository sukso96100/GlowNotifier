package com.hybdms.glownotifier;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ColorPerAppList extends ActionBarActivity {
    private ListView mListAppInfo;
    String color_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_per_app_list);

        // load application list
        mListAppInfo = (ListView)findViewById(R.id.listView1);
        // create new adapter
        AppInfoAdapter adapter = new AppInfoAdapter(this, Utilities.getInstalledApplication(this), getPackageManager());
        // set adapter to list view
        mListAppInfo.setAdapter(adapter);

        // implement event when an item on list view is selected
        mListAppInfo.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id){

                // get the list adapter
                AppInfoAdapter appInfoAdapter = (AppInfoAdapter)parent.getAdapter();
                // get selected item on the list
                ApplicationInfo appInfo = (ApplicationInfo)appInfoAdapter.getItem(pos);
                // launch the selected application

                color_key = "color" + appInfo.packageName.toString();
                showColorPickerDialog();
            }
        });

    }

    private void showColorPickerDialog() {
        SharedPreferences pref = getSharedPreferences("colorpref", Activity.MODE_PRIVATE);  //Load Preference
        int initialColor = pref.getInt(color_key, Color.WHITE);  //Load Color Value from Preference

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                SharedPreferences pref = getSharedPreferences("colorpref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit(); //Load Editor
                editor.putInt(color_key, color); //Save Selected Color
                editor.commit();
            }

        });
        colorPickerDialog.show();

    }

}
