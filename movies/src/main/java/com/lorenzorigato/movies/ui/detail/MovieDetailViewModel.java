package com.lorenzorigato.movies.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.components.architecture.SingleLiveData;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;


public class MovieDetailViewModel extends ViewModel {


    // Private class attributes ********************************************************************
    private IMovieRepository movieRepository;
    private LiveData<Movie> movie;
    private LiveData<MovieDetailView.State> state;
    private SingleLiveData<MovieDetailView.Status> status = new SingleLiveData<>();


    // Constructor *********************************************************************************
    public MovieDetailViewModel(IMovieRepository movieRepository, int movieId) {
        this.movieRepository = movieRepository;
        this.movie = this.movieRepository.findById(movieId);
        this.state = Transformations.map(this.movie, movie ->
                new MovieDetailView.State(movie.getPosterFullPath(),
                        movie.getTitle(),
                        movie.getSubtitle(),
                        movie.getDescription(),
                        movie.getRating()));
    }


    // Class methods *******************************************************************************
    public LiveData<MovieDetailView.State > getState() { return this.state; }

    public void onToggleFavorite() {
        Movie movie = this.movie.getValue();
        if (movie != null) {
            movie.setFavorite(!movie.isFavorite());
            this.movieRepository.update(movie, (error, data) -> {
                if (error != null) {
                    this.status.setValue(MovieDetailView.Status.FAVORITE_NOT_SET_ERROR);
                }
            });
        }
    }
}
