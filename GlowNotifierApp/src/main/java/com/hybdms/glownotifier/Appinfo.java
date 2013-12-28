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

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

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

        TextView update = (TextView)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(Intent.ACTION_VIEW);
                update.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.hybdms.glownotifier"));
                startActivity(update);
            }
        });

        TextView tutorial = (TextView)findViewById(R.id.tutorial);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorial = new Intent(Appinfo.this, Tutorial.class);
                startActivity(tutorial);
            }
        });

        TextView visit = (TextView)findViewById(R.id.visit);
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visit = new Intent(Intent.ACTION_VIEW);
                visit.setData(Uri.parse("https://sukso96100.github.io/GlowNotifier"));
                startActivity(visit);
            }
        });

        TextView readme = (TextView)findViewById(R.id.readme);
        readme.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readme = new Intent(Appinfo.this, Doc_Readme.class);
                startActivity(readme);
            }
        });

        TextView notices = (TextView)findViewById(R.id.notices);
        notices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notices = new Intent(Appinfo.this, Doc_Notices.class);
                startActivity(notices);
            }
        });

        TextView copying = (TextView)findViewById(R.id.copying);
        copying.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent copying = new Intent(Appinfo.this, Doc_Copying.class);
                startActivity(copying);
            }
        });

        TextView contrubutors = (TextView)findViewById(R.id.contrubutors);
        contrubutors.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contrubutors = new Intent(Appinfo.this, Doc_Contributors.class);
                startActivity(contrubutors);
            }
        });

    }




}
