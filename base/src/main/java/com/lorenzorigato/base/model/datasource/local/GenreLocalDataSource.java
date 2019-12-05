package com.lorenzorigato.base.model.datasource.local;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public class GenreLocalDataSource implements IGenreLocalDataSource {

    @Override
    public void findByName(String name, AsyncCallback<Genre> callback) {
        if (callback != null) {
            Genre genre = new Genre(1, "Action");
            callback.onCompleted(null, genre);
        }
    }

    @Override
    public void saveGenres(List<Genre> genres, AsyncCallback<List<Genre>> listener) {
        if (listener != null) {
            listener.onCompleted(null, genres);
        }
    }
}
