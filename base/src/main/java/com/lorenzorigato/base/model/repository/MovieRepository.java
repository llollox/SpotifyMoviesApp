package com.lorenzorigato.base.model.repository;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.database.error.RecordNotFoundError;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.error.TaskAlreadyRunningError;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements IMovieRepository {

    // Static **************************************************************************************
    private static final int NETWORK_PAGE_SIZE = 20;


    // Private class attributes ********************************************************************
    private IGenreRepository genreRepository;
    private IMovieLocalDataSource localDataSource;
    private IMovieRemoteDataSource remoteDataSource;
    private boolean isUpdateByGenreRunning = false;


    // Constructor *********************************************************************************
    public MovieRepository(IGenreRepository genreRepository, IMovieLocalDataSource localDataSource, IMovieRemoteDataSource remoteDataSource) {
        this.genreRepository = genreRepository;
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }


    // IMovieRepository methods ********************************************************************
    @Override
    public boolean isUpdateByGenreRunning() {
        return isUpdateByGenreRunning;
    }

    @Override
    public LiveData<List<Movie>> findByIds(List<Integer> ids) {
        return this.localDataSource.findByIds(ids);
    }

    @Override
    public LiveData<List<Movie>> findByGenreId(int genreId) {
        return this.localDataSource.findByGenreId(genreId);
    }

    @Override
    public void updateByGenre(String genreName, final int offset, UpdateByGenreCallback callback) {

        if (this.isUpdateByGenreRunning) {
            if (callback != null) {
                callback.onCompleted(new TaskAlreadyRunningError(), null, false);
            }

            return;
        }

        this.isUpdateByGenreRunning = true;
        this.genreRepository.findByName(genreName, (genreError, genre) -> {

            if (genre == null) {
                // If the genre is not found into the db, then there is no possibility
                // to find movies with that genre.
                // For this reason simply return with an error.
                if (callback != null) {
                    callback.onCompleted(new RecordNotFoundError(), null, false);
                }

                this.isUpdateByGenreRunning = false;
                return;
            }

            this.localDataSource.findMoviesByGenre(genre, (findMoviesError, moviesFromDb) -> {

                if (findMoviesError != null) {
                    this.isUpdateByGenreRunning = false;

                    if (callback != null) {
                        callback.onCompleted(findMoviesError, null, false);
                    }
                    return;
                }

                if (!moviesFromDb.isEmpty() && offset < moviesFromDb.size()) {
                    // If movies are already present in db and
                    // the offset is within the number of movies into the db, than reply immediately
                    // HasLoadedAllMovies is false because since the request hasn't performed,
                    // maybe the db doesn't have all movies because others are still in the server.
                    // (Or they has been loaded later)
                    this.isUpdateByGenreRunning = false;

                    if (callback != null) {
                        callback.onCompleted(null, moviesFromDb, false);
                    }
                    return;
                }

                // Otherwise perform the network request to get the first page of movies
                // and once the movies are retrieved, then save them into the database.
                // Finally notify the caller.
                this.remoteDataSource.fetchMovies(genreName, offset, NETWORK_PAGE_SIZE, (networkError, response) -> {
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
                    boolean hasLoadedAllMovies = offset + movies.size() >= response.getNumTotalMovies();

                    this.localDataSource.saveMovies(movies, genreMovieJoins, (dbError, savedMovies) -> {
                        this.isUpdateByGenreRunning = false;

                        if (callback == null) {
                            return;
                        }

                        if (dbError == null) {
                            callback.onCompleted(null, savedMovies, hasLoadedAllMovies);
                        } else {
                            callback.onCompleted(dbError, null, false);
                        }

                    });
                });
            });
        });
    }

    @Override
    public void update(Movie movie, AsyncCallback<Movie> callback) {
        this.localDataSource.updateMovie(movie, callback);
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
