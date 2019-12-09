package com.lorenzorigato.movies.di.module;

import com.lorenzorigato.base.config.interfaces.IConfiguration;
import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.movies.config.MoviesAppConfiguration;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigModule {

    @ApplicationScope
    @Provides
    public static IConfiguration providesConfiguration() {
        return new MoviesAppConfiguration();
    }
}
