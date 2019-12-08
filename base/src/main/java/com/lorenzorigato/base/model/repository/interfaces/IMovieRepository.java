package com.lorenzorigato.base.model.repository.interfaces;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import java.util.List;

public interface IMovieRepository {

    DataSource.Factory<Integer, Movie> findFavorites();

    LiveData<MovieWithActors> findById(int id);

    DataSource.Factory<Integer, Movie> findByGenreIdPaged(int genreId);

    interface UpdateByGenreCallback {
        void onCompleted(Throwable error, List<Movie> movies, boolean isLastPage);
    }

    void updateByGenre(
            Genre genre,
            Integer afterMovieId,
            UpdateByGenreCallback callback);

    void toggleFavorite(int movieId, AsyncCallback<Movie> callback);
}
