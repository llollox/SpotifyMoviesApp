package com.lorenzorigato.movies.ui.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailModule {

    @Provides
    public static MovieDetailViewModel providesMovieDetailViewModel(
            MovieDetailActivity movieDetailActivity, IMovieRepository movieRepository) {
        MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(movieRepository);
        return ViewModelProviders.of(movieDetailActivity, factory).get(MovieDetailViewModel.class);
    }

    public static class MovieDetailViewModelFactory implements ViewModelProvider.Factory {
        private IMovieRepository movieRepository;

        public MovieDetailViewModelFactory(IMovieRepository movieRepository) {
            this.movieRepository = movieRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MovieDetailViewModel(this.movieRepository);
        }
    }
}
