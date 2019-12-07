package com.lorenzorigato.movies_sample.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lorenzorigato.base.config.interfaces.IConfiguration;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.ui.detail.MovieDetailActivity;
import com.lorenzorigato.movies.ui.favorites.FavoritesFragment;
import com.lorenzorigato.movies_sample.R;
import com.lorenzorigato.movies_sample.model.Mock;
import com.lorenzorigato.movies_sample.model.repository.FakeMovieRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class TestActivity extends DaggerAppCompatActivity {

    @Inject
    IMovieRepository movieRepository;

    @Inject
    IConfiguration configuration;

    FakeMovieRepository fakeMovieRepository;
    String serverUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.serverUrl = this.configuration.getServerUrl();
        this.fakeMovieRepository = (FakeMovieRepository) this.movieRepository;
//        this.startFavoritesFragment();
        this.startDetailActivity();
    }

    private void startFavoritesFragment() {
        // Init Mocks
        List<Movie> favorites = new ArrayList<>();
        favorites.add(Mock.getBatmanBegins(this.serverUrl));
        favorites.add(Mock.getCasinoRoyale(this.serverUrl));

        this.fakeMovieRepository.setFavorites(favorites);

        // Start View
        this.setContentView(R.layout.test_activity);
        FavoritesFragment fragment = FavoritesFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_layout, fragment)
                .commit();
    }

    private void startDetailActivity() {

        // Init Mocks
        MovieWithActors casinoRoyaleWithActors = Mock.getCasinoRoyaleWithActors(this.serverUrl);
        this.fakeMovieRepository.setFindById(casinoRoyaleWithActors);

        // Start View
        Intent intent = MovieDetailActivity.getCallingIntent(this, casinoRoyaleWithActors.getMovie().getId());
        startActivity(intent);
        finish();
    }
}
