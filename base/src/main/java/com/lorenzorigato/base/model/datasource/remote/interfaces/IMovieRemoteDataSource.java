package com.lorenzorigato.base.model.datasource.remote.interfaces;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Movie;

import java.util.List;

public interface IMovieRemoteDataSource {

    class FetchMoviesResponse {
        private List<Movie> movies;
        private int numTotalMovies;

        public FetchMoviesResponse(List<Movie> movies, int numTotalMovies) {
            this.movies = movies;
            this.numTotalMovies = numTotalMovies;
        }

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
