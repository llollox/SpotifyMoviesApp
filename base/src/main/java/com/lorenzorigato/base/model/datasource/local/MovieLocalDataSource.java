package com.lorenzorigato.base.model.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.database.dao.GenreMovieJoinDao;
import com.lorenzorigato.base.database.dao.MovieDao;
import com.lorenzorigato.base.database.error.RecordNotFoundError;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieLocalDataSource implements IMovieLocalDataSource {


    // Private class attributes ********************************************************************
    private GenreMovieJoinDao genreMovieJoinDao;
    private MovieDao movieDao;


    // Constructor *********************************************************************************
    public MovieLocalDataSource(GenreMovieJoinDao genreMovieJoinDao, MovieDao movieDao) {
        this.genreMovieJoinDao = genreMovieJoinDao;
        this.movieDao = movieDao;
    }


    // IMovieLocalDataSource methods ***************************************************************
    @Override
    public DataSource.Factory<Integer, Movie> findByGenreIdPaged(int genreId) {
        return this.genreMovieJoinDao.findMoviesByGenreIdPaged(genreId);
    }

    @Override
    public DataSource.Factory<Integer, Movie> findFavorites() {
        return this.movieDao.findFavorites();
    }

    @Override
    public LiveData<MovieWithActors> findById(int id) {
        return movieDao.findByIdWithActors(id);
    }

    @Override
    public void saveMovies(List<Movie> movies, List<GenreMovieJoin> genreMovieJoins, List<Actor> actors, AsyncCallback<List<Movie>> callback) {

        Single.create((SingleOnSubscribe<List<Movie>>) emitter -> {
            try {
                movieDao.insertAll(movies, genreMovieJoins, actors);
                emitter.onSuccess(movies);
            } catch (Throwable t) {
                emitter.onError(t);
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<List<Movie>>() {

            @Override
            public void onSuccess(List<Movie> movies) {
                if (callback != null) {
                    callback.onCompleted(null, movies);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (callback != null) {
                    callback.onCompleted(e, null);
                }
            }
        });
    }

    @Override
    public void toggleFavorite(int movieId, AsyncCallback<Movie> callback) {
        Single.create((SingleOnSubscribe<Movie>) emitter -> {
            try {
                Movie movie = this.movieDao.findById(movieId);
                if (movie == null) {
                    emitter.onError(new RecordNotFoundError());
                    return;
                }
                movie.setFavorite(!movie.isFavorite());
                movieDao.insert(movie);
                emitter.onSuccess(movie);
            } catch (Throwable t) {
                emitter.onError(t);
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<Movie>() {

            @Override
            public void onSuccess(Movie movie) {
                if (callback != null) {
                    callback.onCompleted(null, movie);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (callback != null) {
                    callback.onCompleted(e, null);
                }
            }
        });
    }
}
