package com.lorenzorigato.base.model.repository.interfaces;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public interface IGenreRepository {

    LiveData<Genre> findByName(String name);
    void findByName(String name, AsyncCallback<Genre> callback);
    void updateAll(AsyncCallback<List<Genre>> callback);
}
