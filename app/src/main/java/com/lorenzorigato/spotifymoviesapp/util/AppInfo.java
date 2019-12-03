package com.lorenzorigato.spotifymoviesapp.util;

import com.lorenzorigato.base.util.IAppInfo;
import com.lorenzorigato.spotifymoviesapp.BuildConfig;

public class AppInfo implements IAppInfo {

    @Override
    public String getApplicationId() {
        return BuildConfig.APPLICATION_ID;
    }

    @Override
    public boolean isProduction() {
        return BuildConfig.FLAVOR.equals("prod");
    }
}
