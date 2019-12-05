package com.lorenzorigato.base.model.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieLocalDataSource implements IMovieLocalDataSource {

    @Override
    public LiveData<List<Movie>> findByIds(List<Integer> ids) {
        ArrayList<Movie> movies = new ArrayList<>();
        return new MutableLiveData<>(movies);
    }

    @Override
    public void findMoviesByGenre(Genre genre, AsyncCallback<List<Movie>> callback) {
        if (callback != null) {
            callback.onCompleted(null, new ArrayList<>());
        }
    }

    @Override
    public void saveMovies(List<Movie> movies, List<GenreMovieJoin> genreMovieJoins, AsyncCallback<List<Movie>> callback) {
        if (callback != null) {
            callback.onCompleted(null, movies);
        }
    }
}
