package com.lorenzorigato.base.model.datasource.local.interfaces;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public interface IGenreLocalDataSource {

    LiveData<Genre> findByName(String name);
    void saveGenres(List<Genre> genres, AsyncCallback<List<Genre>> callback);
}
