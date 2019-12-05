package com.lorenzorigato.base.network.service;

import com.lorenzorigato.base.network.service.envelope.MovieEnvelope;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movies")
    Call<MovieEnvelope> getMovies(
            @Query("genre") String genre,
            @Query("offset") int offset,
            @Query("limit") int limit);
}