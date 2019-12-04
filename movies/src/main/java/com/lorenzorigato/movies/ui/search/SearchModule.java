package com.lorenzorigato.movies.ui.search;

import androidx.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    public static SearchViewModel providesSearchViewModel(SearchFragment searchFragment) {
        return ViewModelProviders.of(searchFragment).get(SearchViewModel.class);
    }
}
