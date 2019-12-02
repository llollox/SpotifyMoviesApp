package com.lorenzorigato.movies.di;

import com.lorenzorigato.movies.ui.MoviesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoviesModule {
    @ContributesAndroidInjector(modules = {})
    abstract MoviesActivity bindMoviesActivity();
}
