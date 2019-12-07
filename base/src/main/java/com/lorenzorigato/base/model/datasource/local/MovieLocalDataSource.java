package com.lorenzorigato.base.model.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.database.dao.GenreMovieJoinDao;
import com.lorenzorigato.base.database.dao.MovieDao;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import java.util.ArrayList;
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
    public LiveData<List<Movie>> findByGenreId(int genreId) {
        return this.genreMovieJoinDao.findMoviesByGenreId(genreId);
    }

    @Override
    public LiveData<List<Movie>> findFavorites() {
        return this.movieDao.findFavorites();
    }

    @Override
    public LiveData<MovieWithActors> findById(int id) {
        return movieDao.findById(id);
    }

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
    public void updateMovie(Movie movie, AsyncCallback<Movie> callback) {
        Single.create((SingleOnSubscribe<Movie>) emitter -> {
            try {
                movieDao.insertAll(movie);
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
