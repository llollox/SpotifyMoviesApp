package com.lorenzorigato.base.network.service;

import com.lorenzorigato.base.network.service.envelope.MovieEnvelope;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movies.json")
    Call<MovieEnvelope> getMovies(
            @Query("genre") String genre,
            @Query("after") int afterMovieId,
            @Query("limit") int limit);
}