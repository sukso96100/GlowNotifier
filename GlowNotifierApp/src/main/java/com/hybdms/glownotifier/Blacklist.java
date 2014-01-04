package com.hybdms.glownotifier;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Blacklist extends ActionBarActivity implements AdapterView.OnItemLongClickListener {
    private BlacklistDBhelper mHelper = null;
    private static Cursor mCursor = null;
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);

        mHelper = new BlacklistDBhelper(this);
        mCursor = mHelper.getWritableDatabase().rawQuery("SELECT _ID, appname, pkgname FROM blacklist ORDER BY appname", null);
        @SuppressWarnings("deprecation")
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.custom,
                mCursor,
                new String[] {BlacklistDBhelper.APPNAME, BlacklistDBhelper.PKGNAME},
                new int[] {R.id.bigtext, R.id.smalltext});
        myList = (ListView)findViewById(R.id.list);
        myList.setAdapter(adapter);
        registerForContextMenu(myList);

        myList.setOnItemLongClickListener(this);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String[] args = {String.valueOf(id)};
        mHelper.getWritableDatabase().delete("blacklist", "_ID=?", args);
        mCursor.requery();
        Toast.makeText(getApplicationContext(), getString(R.string.db_removed), Toast.LENGTH_SHORT).show();
        return false;
    }




    @SuppressWarnings("deprecation")
    static void refreshView() {

        mCursor.requery();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
        mHelper.close();
    }

    //ActionBar Action Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blacklist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_new:
                startActivity(new Intent(this, Blacklist_Addnew.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
