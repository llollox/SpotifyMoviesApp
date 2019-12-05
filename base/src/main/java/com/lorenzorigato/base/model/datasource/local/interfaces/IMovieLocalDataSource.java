package com.lorenzorigato.base.model.datasource.local.interfaces;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;

import java.util.List;

public interface IMovieLocalDataSource {

    LiveData<List<Movie>> findByIds(List<Integer> ids);
    void findMoviesByGenre(Genre genre, AsyncCallback<List<Movie>> callback);
    void saveMovies(List<Movie> movies, List<GenreMovieJoin> genreMovieJoins, AsyncCallback<List<Movie>> callback);
}
