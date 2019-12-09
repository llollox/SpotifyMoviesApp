package com.lorenzorigato.movies.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.util.IAppInfo;
import com.lorenzorigato.movies.util.MoviesAppInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {

    @ApplicationScope
    @Provides
    public static IAppInfo providesAppInfo() {
        return new MoviesAppInfo();
    }
}
