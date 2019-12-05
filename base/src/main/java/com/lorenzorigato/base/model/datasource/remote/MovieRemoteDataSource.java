package com.lorenzorigato.base.model.datasource.remote;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.config.interfaces.IConfiguration;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;
import com.lorenzorigato.base.network.error.NoInternetError;
import com.lorenzorigato.base.network.service.dto.MovieDTO;

import java.util.ArrayList;

public class MovieRemoteDataSource implements IMovieRemoteDataSource {


    // Private class attributes ********************************************************************
    private IReachabilityChecker reachabilityChecker;
    private IConfiguration configuration;


    // Constructor *********************************************************************************
    public MovieRemoteDataSource(IReachabilityChecker reachabilityChecker, IConfiguration configuration) {
        this.reachabilityChecker = reachabilityChecker;
        this.configuration = configuration;
    }


    @Override
    public void fetchMovies(String genre, int offset, AsyncCallback<FetchMoviesResponse> callback) {
        if (!this.reachabilityChecker.isInternetAvailable()) {
            if (callback != null) {
                callback.onCompleted(new NoInternetError(), null);
            }

            return;
        }

        if (callback != null) {
            ArrayList<Movie> movies = new ArrayList<>();
            movies.add(new Movie(1, "Prova", 5.6, "https://image.tmdb.org/t/p/w342/5ig0kdWz5kxR4PHjyCgyI5khCzd.jpg"));
            movies.add(new Movie(1, "Prova2", 8.6, "https://image.tmdb.org/t/p/w342/5ig0kdWz5kxR4PHjyCgyI5khCzd.jpg"));
            FetchMoviesResponse response = new FetchMoviesResponse(movies, 1);
            callback.onCompleted(null, response);
        }

    }


    // Private class functions *********************************************************************
    private Movie mapToMovie(MovieDTO movieDTO) {
        return new Movie(
                movieDTO.getId(),
                movieDTO.getTitle(),
                movieDTO.getRating(),
                this.configuration.getServerUrl() + movieDTO.getCover().getMedium().substring(1));
    }
}
