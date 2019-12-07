package com.lorenzorigato.movies_sample.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.movies.ui.IMoviesNavigator;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigatorModule {

    @ApplicationScope
    @Provides
    public static IMoviesNavigator providesIMoviesNavigator() {
        return null;
    }
}
