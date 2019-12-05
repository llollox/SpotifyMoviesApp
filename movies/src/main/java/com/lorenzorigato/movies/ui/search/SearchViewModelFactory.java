package com.lorenzorigato.movies.ui.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
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
