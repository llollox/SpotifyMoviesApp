package com.lorenzorigato.movies.ui.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lorenzorigato.base.model.repository.IGenreRepository;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private IGenreRepository genreRepository;

    public SearchViewModelFactory(IGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchViewModel(this.genreRepository);
    }
}
