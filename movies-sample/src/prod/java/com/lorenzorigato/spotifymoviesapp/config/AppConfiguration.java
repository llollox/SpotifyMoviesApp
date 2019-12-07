package com.lorenzorigato.spotifymoviesapp.config;

import com.lorenzorigato.base.config.interfaces.IConfiguration;

public class AppConfiguration implements IConfiguration {

    @Override
    public String getServerUrl() {
        return "https://www.spotifymovies.com/";
    }
}
