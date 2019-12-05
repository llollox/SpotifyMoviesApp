package com.lorenzorigato.base.model.datasource.remote;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreRemoteDataSource implements IGenreRemoteDataSource {

    @Override
    public void fetchAll(AsyncCallback<List<Genre>> callback) {
        if (callback != null) {

            List<Genre> genres = new ArrayList<>();
            genres.add(new Genre(1, "Action"));
            genres.add(new Genre(2, "Adventure"));
            genres.add(new Genre(3, "Animation"));

            callback.onCompleted(null, genres);
        }
    }
}
