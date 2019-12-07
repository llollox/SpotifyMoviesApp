package com.lorenzorigato.movies.ui.favorites;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesModule {

    @Provides
    public static FavoritesViewModel providesFavoritesViewModel(
            FavoritesFragment favoritesFragment, IMovieRepository movieRepository) {
        FavoritesViewModelFactory factory = new FavoritesViewModelFactory(movieRepository);
        return ViewModelProviders.of(favoritesFragment, factory).get(FavoritesViewModel.class);
    }

    public static class FavoritesViewModelFactory implements ViewModelProvider.Factory {
        private IMovieRepository movieRepository;

        public FavoritesViewModelFactory(IMovieRepository movieRepository) {
            this.movieRepository = movieRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavoritesViewModel(this.movieRepository);
        }
    }
}
