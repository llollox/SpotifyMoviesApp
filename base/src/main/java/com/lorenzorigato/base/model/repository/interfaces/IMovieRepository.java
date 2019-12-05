package com.lorenzorigato.base.model.repository.interfaces;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.model.entity.Movie;

import java.util.List;

public interface IMovieRepository {

    boolean isUpdateByGenreRunning();

    LiveData<List<Movie>> findByIds(List<Integer> ids);

    interface UpdateByGenreCallback {
        void onCompleted(Throwable error, List<Movie> movies, boolean hasLoadAllMovies);
    }

    void updateByGenre(
            String genreName,
            int offset,
            UpdateByGenreCallback callback);
}
