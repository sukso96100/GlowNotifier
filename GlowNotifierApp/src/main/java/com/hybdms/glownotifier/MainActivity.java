package com.hybdms.glownotifier;

import android.content.Intent;
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
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
