package com.hybdms.glownotifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by youngbin on 14. 1. 4.
 */
public class BlacklistDBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "blacklist.db";
    static final String APPNAME = "appname";
    static final String PKGNAME = "pkgname";

    public BlacklistDBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE blacklist (_id INTEGER PRIMARY KEY AUTOINCREMENT, appname TEXT, pkgname TEXT);");

        ContentValues cv = new ContentValues();

       // cv.put(APPNAME, "GlowNotifier");
       // cv.put(PKGNAME, "com.hybdms.glownotifier");
        db.insert("blacklist", PKGNAME, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS blacklist");
        onCreate(db);
    }

}