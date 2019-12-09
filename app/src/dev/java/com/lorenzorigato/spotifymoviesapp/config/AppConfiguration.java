package com.lorenzorigato.spotifymoviesapp.config;

import com.lorenzorigato.base.config.interfaces.IConfiguration;

public class AppConfiguration implements IConfiguration {

    @Override
    public String getServerUrl() {
        return "http://10.0.2.2:3000/";
    }

    @Override
    public String getDbName() { return "spotify_movies_database_dev"; }
}
