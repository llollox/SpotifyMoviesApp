package com.lorenzorigato.movies_sample.model.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import java.util.List;

public class FakeMovieRepository implements IMovieRepository {


    // Private class attributes ********************************************************************
    private List<Movie> favorites;
    private MovieWithActors findById;


    // Class methods *******************************************************************************
    public void setFavorites(List<Movie> favorites) {
        this.favorites = favorites;
    }

    public void setFindById(MovieWithActors findById) {
        this.findById = findById;
    }


    // IMovieRepository methods ********************************************************************
    @Override
    public boolean isUpdateByGenreRunning() {
        return false;
    }

    @Override
    public LiveData<List<Movie>> findFavorites() {
        return new MutableLiveData<>(this.favorites);
    }

    @Override
    public LiveData<MovieWithActors> findById(int id) {
        return new MutableLiveData<>(this.findById);
    }

    @Override
    public LiveData<List<Movie>> findByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public LiveData<List<Movie>> findByGenreId(int genreId) {
        return null;
    }

    @Override
    public void updateByGenre(String genreName, int offset, UpdateByGenreCallback callback) {

    }

    @Override
    public void update(Movie movie, AsyncCallback<Movie> callback) {

    }
}
