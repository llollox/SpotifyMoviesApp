package com.lorenzorigato.base.model.datasource.remote.interfaces;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public interface IGenreRemoteDataSource {

    void fetchAll(AsyncCallback<List<Genre>> callback);
}
