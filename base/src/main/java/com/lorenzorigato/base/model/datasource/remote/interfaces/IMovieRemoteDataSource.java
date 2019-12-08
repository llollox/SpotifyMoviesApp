package com.lorenzorigato.base.model.datasource.remote.interfaces;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Movie;

import java.util.List;

public interface IMovieRemoteDataSource {

    class FetchMoviesResponse {
        private List<Movie> movies;
        private List<Actor> actors;
        private boolean isLastPage;

        public FetchMoviesResponse(List<Movie> movies, List<Actor> actors, boolean isLastPage) {
            this.movies = movies;
            this.actors = actors;
            this.isLastPage = isLastPage;
        }

        public List<Actor> getActors() { return actors; }

        public List<Movie> getMovies() {
            return movies;
        }

        public boolean isLastPage() { return isLastPage; }
    }

    void fetchMovies(
            String genre,
            int afterMovieId,
            int pageSize,
            AsyncCallback<FetchMoviesResponse> callback);
}
