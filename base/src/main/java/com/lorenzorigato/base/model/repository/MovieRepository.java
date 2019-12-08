package com.lorenzorigato.base.model.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;
import com.lorenzorigato.base.model.error.TaskAlreadyRunningError;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements IMovieRepository {

    // Static **************************************************************************************
    private static final int NETWORK_PAGE_SIZE = 30;


    // Private class attributes ********************************************************************
    private IMovieLocalDataSource localDataSource;
    private IMovieRemoteDataSource remoteDataSource;
    private boolean isUpdateByGenreRunning = false;


    // Constructor *********************************************************************************
    public MovieRepository(IMovieLocalDataSource localDataSource, IMovieRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }


    // IMovieRepository methods ********************************************************************
    @Override
    public LiveData<MovieWithActors> findById(int id) {
        return this.localDataSource.findById(id);
    }

    @Override
    public DataSource.Factory<Integer, Movie> findFavorites() {
        return this.localDataSource.findFavorites();
    }

    @Override
    public DataSource.Factory<Integer, Movie> findByGenreIdPaged(int genreId) {
        return this.localDataSource.findByGenreIdPaged(genreId);
    }

    @Override
    public void updateByGenre(Genre genre, Integer afterMovieId, UpdateByGenreCallback callback) {

        if (this.isUpdateByGenreRunning) {
            if (callback != null) {
                callback.onCompleted(new TaskAlreadyRunningError(), null, false);
            }

            return;
        }

        this.isUpdateByGenreRunning = true;
        this.remoteDataSource.fetchMovies(genre.getName(), afterMovieId, NETWORK_PAGE_SIZE, (networkError, response) -> {
            if (networkError != null) {
                this.isUpdateByGenreRunning = false;

                if (callback != null) {
                    callback.onCompleted(networkError, null, false);
                }

                return;
            }

            int genreId = genre.getId();
            List<Movie> movies = response.getMovies();
            List<GenreMovieJoin> genreMovieJoins = this.mapToGenreMovieJoin(genreId, movies);
            List<Actor> actors = response.getActors();
            boolean isLastPage = response.isLastPage();

            this.localDataSource.saveMovies(movies, genreMovieJoins, actors, (dbError, savedMovies) -> {
                this.isUpdateByGenreRunning = false;

                if (callback == null) {
                    return;
                }

                if (dbError == null) {
                    callback.onCompleted(null, savedMovies, isLastPage);
                } else {
                    callback.onCompleted(dbError, null, false);
                }

            });
        });
    }


    @Override
    public void toggleFavorite(int movieId, AsyncCallback<Movie> callback) {
        this.localDataSource.toggleFavorite(movieId, callback);
    }

    // Private class methods ***********************************************************************
    private List<GenreMovieJoin> mapToGenreMovieJoin(int genreId, List<Movie> movies) {
        ArrayList<GenreMovieJoin> genreMovieJoins = new ArrayList<>();
        for (Movie movie : movies) {
            GenreMovieJoin genreMovieJoin = new GenreMovieJoin(genreId, movie.getId());
            genreMovieJoins.add(genreMovieJoin);
        }
        return genreMovieJoins;
    }
}
