package com.lorenzorigato.base.network.service;

import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GenreService {
    @GET("genres.json")
    Call<List<Genre>> getAll();
}
