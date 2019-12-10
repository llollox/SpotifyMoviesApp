package com.lorenzorigato.base.fake;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import java.util.List;

public class FakeMovieLocalDataSource implements IMovieLocalDataSource {


    // Private class attributes ********************************************************************
    private List<Movie> saveMoviesList;
    private Throwable saveMoviesError;


    // Class methods *******************************************************************************
    public void setSaveMoviesList(List<Movie> saveMoviesList) {
        this.saveMoviesList = saveMoviesList;
    }

    public void setSaveMoviesError(Throwable saveMoviesError) {
        this.saveMoviesError = saveMoviesError;
    }


    // IMovieLocalDataSource methods ***************************************************************
    @Override
    public DataSource.Factory<Integer, Movie> findFavorites() {
        return null;
    }

    @Override
    public DataSource.Factory<Integer, Movie> findByGenreIdPaged(int genreId) {
        return null;
    }

    @Override
    public LiveData<MovieWithActors> findById(int id) {
        return null;
    }

    @Override
    public void saveMovies(List<Movie> movies, List<GenreMovieJoin> genreMovieJoins, List<Actor> actors, AsyncCallback<List<Movie>> callback) {
        if (callback != null) {
            callback.onCompleted(this.saveMoviesError, this.saveMoviesList);
        }
    }

    @Override
    public void toggleFavorite(int movieId, AsyncCallback<Movie> callback) {

    }
}
