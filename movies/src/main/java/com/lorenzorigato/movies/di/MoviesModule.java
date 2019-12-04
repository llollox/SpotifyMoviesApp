package com.lorenzorigato.movies.di;

import com.lorenzorigato.movies.ui.MoviesActivity;
import com.lorenzorigato.movies.ui.search.SearchFragment;
import com.lorenzorigato.movies.ui.search.SearchModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoviesModule {
    @ContributesAndroidInjector(modules = {})
    abstract MoviesActivity bindMoviesActivity();

    @ContributesAndroidInjector(modules = {SearchModule.class})
    abstract SearchFragment bindSearchFragment();
}
