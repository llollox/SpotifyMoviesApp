package com.lorenzorigato.base.model.repository;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public interface IGenreRepository {

    void findByName(String name, AsyncCallback<Genre> callback);
    void updateAll(AsyncCallback<List<Genre>> callback);
}
