package com.lorenzorigato.base.model.repository.interfaces;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Movie;

import java.util.List;

public interface IMovieRepository {

    boolean isUpdateByGenreRunning();

    LiveData<Movie> findById(int id);

    LiveData<List<Movie>> findByIds(List<Integer> ids);

    LiveData<List<Movie>> findByGenreId(int genreId);

    interface UpdateByGenreCallback {
        void onCompleted(Throwable error, List<Movie> movies, boolean hasLoadAllMovies);
    }

    void updateByGenre(
            String genreName,
            int offset,
            UpdateByGenreCallback callback);

    void update(Movie movie, AsyncCallback<Movie> callback);
}
