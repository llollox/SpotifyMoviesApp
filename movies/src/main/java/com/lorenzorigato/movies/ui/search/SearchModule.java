package com.lorenzorigato.movies.ui.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {


    // Static **************************************************************************************
    public static class SearchViewModelFactory implements ViewModelProvider.Factory {
        private IGenreRepository genreRepository;
        private IMovieRepository movieRepository;

        public SearchViewModelFactory(IGenreRepository genreRepository, IMovieRepository movieRepository) {
            this.genreRepository = genreRepository;
            this.movieRepository = movieRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SearchViewModel(this.genreRepository, this.movieRepository);
        }
    }


    @Provides
    public static SearchViewModel providesSearchViewModel(
            SearchFragment searchFragment, IGenreRepository genreRepository, IMovieRepository movieRepository) {
        SearchViewModelFactory factory = new SearchViewModelFactory(genreRepository, movieRepository);
        return ViewModelProviders.of(searchFragment, factory).get(SearchViewModel.class);
    }
}
