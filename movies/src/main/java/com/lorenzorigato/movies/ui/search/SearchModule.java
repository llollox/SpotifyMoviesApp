package com.lorenzorigato.movies.ui.search;

import androidx.lifecycle.ViewModelProviders;

import com.lorenzorigato.base.model.repository.IGenreRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    public static SearchViewModel providesSearchViewModel(SearchFragment searchFragment, IGenreRepository genreRepository) {
        SearchViewModelFactory factory = new SearchViewModelFactory(genreRepository);
        return ViewModelProviders.of(searchFragment, factory).get(SearchViewModel.class);
    }
}
