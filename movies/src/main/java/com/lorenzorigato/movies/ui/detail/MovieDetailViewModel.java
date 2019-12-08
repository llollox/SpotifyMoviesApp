package com.lorenzorigato.movies.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.components.architecture.SingleLiveData;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.ui.detail.actors.ActorViewHolder;

import java.util.ArrayList;


public class MovieDetailViewModel extends ViewModel {


    // Private class attributes ********************************************************************
    private IMovieRepository movieRepository;
    private LiveData<MovieWithActors> movie;
    private LiveData<MovieDetailView.State> state;
    private SingleLiveData<MovieDetailView.Status> status = new SingleLiveData<>();


    // Constructor *********************************************************************************
    public MovieDetailViewModel(IMovieRepository movieRepository, int movieId) {
        this.movieRepository = movieRepository;
        this.movie = this.movieRepository.findById(movieId);
        this.state = Transformations.map(this.movie, this::mapToState);
    }


    // Class methods *******************************************************************************
    public LiveData<MovieDetailView.State > getState() { return this.state; }

    public LiveData<MovieDetailView.Status > getStatus() { return this.status; }

    public void onToggleFavorite() {
        MovieWithActors movieWithActors = this.movie.getValue();
        if (movieWithActors != null) {
            Movie movie = movieWithActors.getMovie();
            this.movieRepository.toggleFavorite(movie.getId(), (error, updatedMovie) -> {
                if (error != null) {
                    this.status.setValue(MovieDetailView.Status.FAVORITE_NOT_SET_ERROR);
                }
                else {
                    if (updatedMovie.isFavorite()) {
                        this.status.setValue(MovieDetailView.Status.FAVORITE_ADD_SUCCESS);
                    }
                    else {
                        this.status.setValue(MovieDetailView.Status.FAVORITE_REMOVED_SUCCESS);
                    }
                }
            });
        }
    }

    private MovieDetailView.State mapToState(MovieWithActors movieWithActors) {
        ArrayList<ActorViewHolder.Layout> actors = new ArrayList<>();
        for (Actor actor : movieWithActors.getActors()) {
            actors.add(mapToState(actor));
        }

        Movie movie = movieWithActors.getMovie();

        return new MovieDetailView.State(movie.getPosterFullPath(),
                movie.getTitle(),
                movie.getSubtitle(),
                movie.getDescription(),
                movie.isFavorite(),
                movie.getRating(),
                actors);
    }

    private ActorViewHolder.Layout mapToState(Actor actor) {
        return new ActorViewHolder.Layout(
                actor.getId(),
                actor.getName(),
                actor.getCharacter(),
                actor.getPhotoUrl());
    }
}
