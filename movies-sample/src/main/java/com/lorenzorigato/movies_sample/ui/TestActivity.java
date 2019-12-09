package com.lorenzorigato.movies_sample.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lorenzorigato.base.config.interfaces.IConfiguration;
import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.ui.detail.MovieDetailActivity;
import com.lorenzorigato.movies.ui.favorites.FavoritesFragment;
import com.lorenzorigato.movies_sample.R;
import com.lorenzorigato.movies_sample.model.Mock;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends DaggerAppCompatActivity {

    @Inject
    IMovieRepository movieRepository;

    @Inject
    IConfiguration configuration;

    @Inject
    SpotifyMoviesDatabase db;

    String serverUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.serverUrl = this.configuration.getServerUrl();
        this.startFavoritesFragment();
//        this.startDetailActivity();
    }

    @SuppressLint("CheckResult")
    private void startFavoritesFragment() {
        // Init Mocks
        Movie batman = Mock.getBatmanBegins(this.serverUrl);
        batman.setFavorite(true);
        Movie casinoRoyale = Mock.getCasinoRoyale(this.serverUrl);
        casinoRoyale.setFavorite(true);

        Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            try {
                this.db.clearAllTables();
                this.db.getMovieDao().insertAll(batman, casinoRoyale);
                emitter.onSuccess(true);
            } catch (Throwable t) {
                emitter.onError(t);
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<Boolean>() {

            @Override
            public void onSuccess(Boolean result) {
                // Start View
                TestActivity.this.setContentView(R.layout.test_activity);
                FavoritesFragment fragment = FavoritesFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame_layout, fragment)
                        .commit();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void startDetailActivity() {

        // Init Mocks
        MovieWithActors casinoRoyaleWithActors = Mock.getCasinoRoyaleWithActors(this.serverUrl);
        Movie movie = casinoRoyaleWithActors.getMovie();
        Genre genre = new Genre(1, "Action");
        ArrayList<Genre> genres = new ArrayList<>();
        genres.add(genre);
        GenreMovieJoin genreMovieJoin = new GenreMovieJoin(1, movie.getId());
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(movie);
        ArrayList<GenreMovieJoin> genreMovieJoins = new ArrayList<>();
        genreMovieJoins.add(genreMovieJoin);

        Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            try {

                this.db.clearAllTables();
                this.db.getGenreDao().insertAll(genres);
                this.db.getMovieDao().insertAll(movies, genreMovieJoins, casinoRoyaleWithActors.getActors());
                emitter.onSuccess(true);
            } catch (Throwable t) {
                emitter.onError(t);
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<Boolean>() {

            @Override
            public void onSuccess(Boolean result) {

                // Start View
                Intent intent = MovieDetailActivity.getCallingIntent(TestActivity.this, movie.getId());
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
