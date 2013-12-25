package com.hybdms.glownotifier;

import android.content.Intent;
import android.content.pm.PackageManager;
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

public class Appinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);

        //Get app version name from Manifest
        String app_ver = null;
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //Set app version name text
        TextView version = (TextView)findViewById(R.id.version);
        version.setText("Version " + app_ver);

        TextView src = (TextView)findViewById(R.id.src);
        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse("http://github.com/sukso96100/GlowNotifier"));
                startActivity(src);
            }
        });

    }




}
