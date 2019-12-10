package com.lorenzorigato.movies;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

public class FakeMovieRepository implements IMovieRepository {


    // Private class attributes ********************************************************************
    private Throwable toggleFavoriteError;
    private Movie toggleFavoriteMovie;


    // Class methods *******************************************************************************
    public void setToggleFavoriteError(Throwable toggleFavoriteError) {
        this.toggleFavoriteError = toggleFavoriteError;
    }

    public void setToggleFavoriteMovie(Movie toggleFavoriteMovie) {
        this.toggleFavoriteMovie = toggleFavoriteMovie;
    }

    // IMovieRepository methods ********************************************************************
    @Override
    public DataSource.Factory<Integer, Movie> findFavorites() {
        return null;
    }

    @Override
    public LiveData<MovieWithActors> findById(int id) {
        return null;
    }

    @Override
    public DataSource.Factory<Integer, Movie> findByGenreIdPaged(int genreId) {
        return null;
    }

    @Override
    public void updateByGenre(Genre genre, Integer afterMovieId, UpdateByGenreCallback callback) {

    }

    @Override
    public void toggleFavorite(int movieId, AsyncCallback<Movie> callback) {
        if (callback != null) {
            callback.onCompleted(this.toggleFavoriteError, this.toggleFavoriteMovie);
        }
    }
}
