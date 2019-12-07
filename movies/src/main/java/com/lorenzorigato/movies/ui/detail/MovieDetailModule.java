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
        int movieId = movieDetailActivity.getMovieId();
        MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(movieRepository, movieId);
        return ViewModelProviders.of(movieDetailActivity, factory).get(MovieDetailViewModel.class);
    }

    public static class MovieDetailViewModelFactory implements ViewModelProvider.Factory {
        private IMovieRepository movieRepository;
        private int movieId;

        public MovieDetailViewModelFactory(IMovieRepository movieRepository, int movieId) {
            this.movieRepository = movieRepository;
            this.movieId = movieId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MovieDetailViewModel(this.movieRepository, this.movieId);
        }
    }
}
