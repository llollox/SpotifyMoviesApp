package com.lorenzorigato.movies.ui.search;

import androidx.lifecycle.ViewModelProviders;

import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    public static SearchViewModel providesSearchViewModel(
            SearchFragment searchFragment, IGenreRepository genreRepository, IMovieRepository movieRepository) {
        SearchViewModelFactory factory = new SearchViewModelFactory(genreRepository, movieRepository);
        return ViewModelProviders.of(searchFragment, factory).get(SearchViewModel.class);
    }
}
