package com.hybdms.glownotifier;

import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Blacklist_Addnew extends ActionBarActivity {
    private BlacklistDBhelper mHelper = null;
    private Cursor mCursor = null;
    private ListView mListAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist__addnew);
        // load list application
        mListAppInfo = (ListView)findViewById(R.id.listView1);
        mHelper = new BlacklistDBhelper(this);
        mCursor = mHelper.getWritableDatabase().rawQuery("SELECT _ID, appname, pkgname FROM blacklist ORDER BY pkgname", null);

        // create new adapter
        AppInfoAdapter adapter = new AppInfoAdapter(this, Utilities.getInstalledApplication(this), getPackageManager());
        // set adapter to list view
        mListAppInfo.setAdapter(adapter);
        // implement event when an item on list view is selected
        mListAppInfo.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id){
                    /*
                     * Note : When the application is added, you must verify whether the app is duplicated.
                     * This may not be an important thing, but this is a bug...
                     */
                boolean passable = true;
                String refString;

                mCursor.moveToLast();
                int count = mCursor.getPosition();
                mCursor.moveToFirst();

                // get the list adapter
                AppInfoAdapter appInfoAdapter = (AppInfoAdapter)parent.getAdapter();
                // get selected item on the list
                ApplicationInfo appInfo = (ApplicationInfo)appInfoAdapter.getItem(pos);
                // launch the selected application

                ApplicationInfo ai;
                try{
                    ai = getPackageManager().getApplicationInfo(appInfo.packageName, 0);
                }catch(final PackageManager.NameNotFoundException e){
                    ai = null;
                }
                final String applicationName = (String) (ai != null ? getPackageManager().getApplicationLabel(ai) : "(unknown)");
                // Logic
                for(int k = 0; k < count; k++) {
                    mCursor.moveToPosition(k);
                    refString = mCursor.getString(1);
                    if (refString.equals(applicationName)) passable = false;
                }

                if(passable){
                    //put values to db
                    ContentValues values = new ContentValues();
                    values.put(BlacklistDBhelper.APPNAME, applicationName.toString());
                    values.put(BlacklistDBhelper.PKGNAME, appInfo.packageName.toString());
                    mHelper.getWritableDatabase().insert("blacklist", BlacklistDBhelper.APPNAME, values);
                    // TODO Let's refresh view. If this logic has errors, then modify this code.
                    // ADD : if passable is false, this is not needed!
                    Blacklist.refreshView();
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.db_alreayexist), Toast.LENGTH_SHORT).show();
                }
                mHelper.close();
                mCursor.close();
                finish();
                // Toast.makeText(c, "appname:" + applicationName + "packagename:" + appInfo.packageName + "added", Toast.LENGTH_LONG);
            }
        });
    }
}