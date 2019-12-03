package com.lorenzorigato.spotifymoviesapp.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.movies.ui.IMoviesNavigator;
import com.lorenzorigato.spotifymoviesapp.ui.navigator.MoviesNavigator;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigatorModule {

    @ApplicationScope
    @Provides
    public static IMoviesNavigator providesIMoviesNavigator() {
        return new MoviesNavigator();
    }
}
