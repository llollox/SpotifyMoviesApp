package com.lorenzorigato.movies.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

public class MovieDetailViewModel extends ViewModel {


    // Private class attributes ********************************************************************
    private IMovieRepository movieRepository;
    private LiveData<MovieDetailView.Layout> layout;


    // Constructor *********************************************************************************
    public MovieDetailViewModel(IMovieRepository movieRepository, int movieId) {
        this.movieRepository = movieRepository;
        this.layout = Transformations.map(this.movieRepository.findById(movieId), movie ->
                new MovieDetailView.Layout(movie.getPosterFullPath(),
                        movie.getTitle(),
                        movie.getSubtitle(),
                        movie.getDescription(),
                        movie.getRating()));
    }


    // Class methods *******************************************************************************
    public LiveData<MovieDetailView.Layout> getLayout() { return this.layout; }

    public void onToggleFavorite() {

    }
}
