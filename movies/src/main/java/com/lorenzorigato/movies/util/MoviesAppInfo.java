package com.lorenzorigato.movies.util;

import com.lorenzorigato.base.util.IAppInfo;

public class MoviesAppInfo implements IAppInfo {

    @Override
    public String getApplicationId() {
        return "movies_app_application_id";
    }

    @Override
    public boolean isProduction() {
        return false;
    }
}
