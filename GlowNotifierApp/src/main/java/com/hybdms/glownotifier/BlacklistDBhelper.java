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

       // ContentValues cv = new ContentValues();

       // cv.put(APPNAME, "GlowNotifier");
       // cv.put(PKGNAME, "com.hybdms.glownotifier");
       // db.insert("blacklist", PKGNAME, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS blacklist");
        onCreate(db);
    }

}