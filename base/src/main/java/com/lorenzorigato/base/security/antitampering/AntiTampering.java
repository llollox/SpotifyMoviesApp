package com.lorenzorigato.base.security.antitampering;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import com.lorenzorigato.base.util.IAppInfo;

public class AntiTampering implements IAntiTampering {


    // Static **************************************************************************************
    private static String VALID_APPLICATION_ID = "com.lorenzorigato.spotifymoviesapp";


    // Instance Variables **************************************************************************
    private Context context;
    private IAppInfo appInfo;


    // Constructor *********************************************************************************
    public AntiTampering(Context context, IAppInfo appInfo) {
        this.context = context;
        this.appInfo = appInfo;
    }


    // IAntiTampering methods **********************************************************************
    public boolean isAppTampered() {
        return this.isAppDebuggable()
                || !this.isAppInstalledFromGooglePlayStore()
                || !this.isApplicationIdValid()
                || this.isAppRunningOnEmulator();
    }


    // Private class methods ***********************************************************************
    boolean isApplicationIdValid() {
        return this.appInfo.getApplicationId().equals(VALID_APPLICATION_ID);
    }

    boolean isAppRunningOnEmulator() {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk".equals(Build.PRODUCT));
    }

    boolean isAppInstalledFromGooglePlayStore() {
        String playStoreAppId = "com.android.vending";
        String packageName = this.context.getPackageName();
        String installer = this.context.getPackageManager().getInstallerPackageName(packageName);
        return installer != null && installer.startsWith(playStoreAppId);
    }

    boolean isAppDebuggable() {
        int flags = this.context.getApplicationInfo().flags;
        return 0 != (flags & ApplicationInfo.FLAG_DEBUGGABLE);
    }
}
