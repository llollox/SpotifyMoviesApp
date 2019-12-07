package com.lorenzorigato.movies.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.components.architecture.SingleLiveData;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.ui.component.movielist.MovieLayoutMapper;
import com.lorenzorigato.movies.ui.component.movielist.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FavoritesViewModel extends ViewModel {


    // Private class attributes ********************************************************************
    private IMovieRepository movieRepository;
    private MovieLayoutMapper movieLayoutMapper = new MovieLayoutMapper();
    private LiveData<List<Movie>> movies;
    private SingleLiveData<FavoritesView.Status> status = new SingleLiveData<>();
    private LiveData<FavoritesView.State> state;


    // Constructor *********************************************************************************
    public FavoritesViewModel(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.movies = this.movieRepository.findFavorites();
        this.state = Transformations.map(this.movies, movies -> {
            ArrayList<MovieViewHolder.Layout> layouts = new ArrayList<>();
            for (Movie movie : movies) {
                boolean isTop = movie.getRating() > 7.0;
                MovieViewHolder.Layout layout = this.movieLayoutMapper.mapToLayout(movie, isTop);
                layouts.add(layout);
            }

            return new FavoritesView.State(layouts, movies.isEmpty(), !movies.isEmpty());
        });
    }

    public LiveData<FavoritesView.Status> getStatus() { return status; }

    public LiveData<FavoritesView.State> getState() { return state; }

    public void onToggleFavorite(MovieViewHolder.Layout layout) {
        Movie movie = this.findMovieById(this.movies.getValue(), layout.getId());
        if (movie != null) {
            movie.setFavorite(!movie.isFavorite());
            this.movieRepository.update(movie, (error, data) -> {
                if (error != null) {
                    this.status.setValue(FavoritesView.Status.FAVORITE_NOT_SET_ERROR);
                }
            });
        }
    }


    // Private class attributes ********************************************************************
    private Movie findMovieById(List<Movie> movies, int movieId) {
        if (movies == null || movies.isEmpty()) {
            return null;
        }

        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                return movie;
            }
        }

        return null;
    }
}
