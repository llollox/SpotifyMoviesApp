package com.lorenzorigato.movies.di;

import com.lorenzorigato.movies.ui.MoviesActivity;
import com.lorenzorigato.movies.ui.detail.MovieDetailActivity;
import com.lorenzorigato.movies.ui.detail.MovieDetailModule;
import com.lorenzorigato.movies.ui.favorites.FavoritesFragment;
import com.lorenzorigato.movies.ui.favorites.FavoritesModule;
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

    @ContributesAndroidInjector(modules = {MovieDetailModule.class})
    abstract MovieDetailActivity bindMovieDetailActivity();

    @ContributesAndroidInjector(modules = {FavoritesModule.class})
    abstract FavoritesFragment bindFavoritesFragment();
}
