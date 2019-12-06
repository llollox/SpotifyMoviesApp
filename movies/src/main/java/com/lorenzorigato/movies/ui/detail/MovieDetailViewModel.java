package com.lorenzorigato.movies.ui.detail;

import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

public class MovieDetailViewModel extends ViewModel {


    // Private class attributes ********************************************************************
    private IMovieRepository movieRepository;


    // Constructor *********************************************************************************
    public MovieDetailViewModel(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    // Class methods *******************************************************************************
    public MovieDetailView.Layout getLayout() {
        String coverUrl = "http://10.0.2.2:3000/system/pictures/photos/000/000/323/big/open-uri20191202-4543-1si1cvh?1575302497";
        String subtitle = "Witness the beginning of a happy ending";
        String description = "Deadpool tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.";
        double rating = 7.5;
        return new MovieDetailView.Layout(coverUrl, subtitle, description, rating);
    }
}
