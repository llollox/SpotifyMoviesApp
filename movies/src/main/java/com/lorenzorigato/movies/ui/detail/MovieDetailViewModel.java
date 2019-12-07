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
    public MovieDetailViewModel(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.layout = Transformations.map(this.movieRepository.findById(682), movie ->
                new MovieDetailView.Layout(movie.getPosterFullPath(),
                        movie.getTitle(),
                        "Witness the beginning of a happy ending",
                        "Deadpool tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.",
                        movie.getRating()));
    }


    // Class methods *******************************************************************************
    public LiveData<MovieDetailView.Layout> getLayout() { return this.layout; }

    public void onToggleFavorite() {

    }
}
