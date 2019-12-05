package com.lorenzorigato.base.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.model.datasource.local.GenreLocalDataSource;
import com.lorenzorigato.base.model.datasource.local.IGenreLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.GenreRemoteDataSource;
import com.lorenzorigato.base.model.datasource.remote.IGenreRemoteDataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

    @ApplicationScope
    @Provides
    public static IGenreLocalDataSource providesGenreLocalDataSource() {
        return new GenreLocalDataSource();
    }

    @ApplicationScope
    @Provides
    public static IGenreRemoteDataSource providesGenreRemoteDataSource() {
        return new GenreRemoteDataSource();
    }
}
