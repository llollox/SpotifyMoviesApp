package com.lorenzorigato.base.model.datasource.local.interfaces;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public interface IGenreLocalDataSource {

    void findByName(String name, AsyncCallback<Genre> callback);
    void saveGenres(List<Genre> genres, AsyncCallback<List<Genre>> callback);
}
