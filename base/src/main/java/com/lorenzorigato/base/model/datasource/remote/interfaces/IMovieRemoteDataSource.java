package com.lorenzorigato.base.model.datasource.remote.interfaces;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Movie;

import java.util.List;

public interface IMovieRemoteDataSource {

    class FetchMoviesResponse {
        private List<Movie> movies;
        private List<Actor> actors;
        private int numTotalMovies;

        public FetchMoviesResponse(List<Movie> movies, List<Actor> actors, int numTotalMovies) {
            this.movies = movies;
            this.actors = actors;
            this.numTotalMovies = numTotalMovies;
        }

        public List<Actor> getActors() { return actors; }

        public List<Movie> getMovies() {
            return movies;
        }

        public int getNumTotalMovies() {
            return numTotalMovies;
        }
    }

    void fetchMovies(
            String genre,
            int offset,
            int pageSize,
            AsyncCallback<FetchMoviesResponse> callback);
}
