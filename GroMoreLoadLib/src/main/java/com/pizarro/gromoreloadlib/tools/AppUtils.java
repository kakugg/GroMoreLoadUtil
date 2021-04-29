package com.pizarro.gromoreloadlib.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

/**
 * Created by Irving
 */
public class AppUtils {

    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return String.valueOf(packageManager.getApplicationLabel(context.getApplicationInfo()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "null";
    }

    public static String getAndroidId(Context context) {
        String androidId = null;
        try {
            androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidId;
    }

}
