package com.lorenzorigato.movies.config;

import com.lorenzorigato.base.config.interfaces.IConfiguration;

public class MoviesAppConfiguration implements IConfiguration {

    @Override
    public String getServerUrl() {
        return "http://localhost:8080/";
    }

    @Override
    public String getDbName() {
        return "movies_app_db";
    }
}
