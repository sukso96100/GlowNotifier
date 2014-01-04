package com.hybdms.glownotifier;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;

import java.util.List;
import com.hybdms.glownotifier.BlacklistDBhelper;

/**
 * Created by youngbin on 14. 1. 4.
 */
public class Utilities {
    private static BlacklistDBhelper mHelper = null;
    private static Cursor mCursor = null;
    /*
     * Get all installed application on mobile and return a list
     * @param   c   Context of application
     * @return  list of installed applications
     */
    public static List<ApplicationInfo> getInstalledApplication(Context c) {
        return c.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
    }



}