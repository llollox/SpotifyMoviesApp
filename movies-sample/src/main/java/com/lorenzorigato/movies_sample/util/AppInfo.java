package com.lorenzorigato.movies_sample.util;

import com.lorenzorigato.base.util.IAppInfo;
import com.lorenzorigato.movies_sample.BuildConfig;

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
