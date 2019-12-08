package com.lorenzorigato.base.model.datasource.local.interfaces;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import java.util.List;

public interface IMovieLocalDataSource {

    DataSource.Factory<Integer, Movie> findFavorites();
    DataSource.Factory<Integer, Movie> findByGenreIdPaged(int genreId);
    LiveData<MovieWithActors> findById(int id);
    void saveMovies(List<Movie> movies, List<GenreMovieJoin> genreMovieJoins, List<Actor> actors, AsyncCallback<List<Movie>> callback);
    void toggleFavorite(int movieId, AsyncCallback<Movie> callback);
}
