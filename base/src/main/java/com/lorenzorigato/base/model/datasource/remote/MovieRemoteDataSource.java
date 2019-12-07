package com.lorenzorigato.base.model.datasource.remote;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.config.interfaces.IConfiguration;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;
import com.lorenzorigato.base.network.error.NetworkRequestError;
import com.lorenzorigato.base.network.error.NoInternetError;
import com.lorenzorigato.base.network.service.MovieService;
import com.lorenzorigato.base.network.service.dto.ActorDTO;
import com.lorenzorigato.base.network.service.dto.MovieDTO;
import com.lorenzorigato.base.network.service.envelope.MovieEnvelope;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRemoteDataSource implements IMovieRemoteDataSource {


    // Private class attributes ********************************************************************
    private MovieService movieService;
    private IReachabilityChecker reachabilityChecker;
    private IConfiguration configuration;


    // Constructor *********************************************************************************
    public MovieRemoteDataSource(
            MovieService movieService,
            IReachabilityChecker reachabilityChecker,
            IConfiguration configuration) {
        this.movieService = movieService;
        this.reachabilityChecker = reachabilityChecker;
        this.configuration = configuration;
    }


    @Override
    public void fetchMovies(String genre, int offset, int pageSize, AsyncCallback<FetchMoviesResponse> callback) {
        if (!this.reachabilityChecker.isInternetAvailable()) {
            if (callback != null) {
                callback.onCompleted(new NoInternetError(), null);
            }
            return;
        }

        this.movieService.getMovies(genre, offset, pageSize).enqueue(new Callback<MovieEnvelope>() {
            @Override
            public void onResponse(@NotNull Call<MovieEnvelope> call, @NotNull Response<MovieEnvelope> response) {
                if (callback == null) {
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {
                    List<MovieDTO> movieDTOs = response.body().getData();
                    ArrayList<Movie> movies = new ArrayList<>();
                    ArrayList<Actor> actors = new ArrayList<>();
                    for (MovieDTO movieDTO : movieDTOs) {
                        movies.add(mapToMovie(movieDTO));

                        for (ActorDTO actorDTO : movieDTO.getCast()) {
                            actors.add(mapToActor(actorDTO, movieDTO.getId()));
                        }
                    }

                    int numTotalMovies = response.body().getMetadata().getTotal();
                    FetchMoviesResponse fetchMoviesResponse = new FetchMoviesResponse(movies, actors, numTotalMovies);
                    callback.onCompleted(null, fetchMoviesResponse);
                }
                else {
                    callback.onCompleted(new NetworkRequestError(), null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<MovieEnvelope> call, @NotNull Throwable t) {
                if (callback != null) {
                    callback.onCompleted(t, null);
                }
            }
        });
    }


    // Private class functions *********************************************************************
    private Movie mapToMovie(MovieDTO movieDTO) {
        return new Movie(
                movieDTO.getId(),
                movieDTO.getTitle(),
                movieDTO.getSubtitle(),
                movieDTO.getDescription(),
                movieDTO.getRating(),
                this.configuration.getServerUrl() + movieDTO.getCover().getMedium().substring(1));
    }

    private Actor mapToActor(ActorDTO actorDTO, int movieId) {
        return new Actor(
                UUID.randomUUID().toString(),
                actorDTO.getName(),
                actorDTO.getGender(),
                actorDTO.getCharacter(),
                actorDTO.getProfile_path(),
                movieId);
    }
}
