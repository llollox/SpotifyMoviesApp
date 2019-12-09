package com.lorenzorigato.movies.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.movies.navigator.FakeMovieNavigator;
import com.lorenzorigato.movies.ui.IMoviesNavigator;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigatorModule {

    @ApplicationScope
    @Provides
    public static IMoviesNavigator providesIMoviesNavigator() {
        return new FakeMovieNavigator();
    }
}
