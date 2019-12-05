package com.lorenzorigato.base.model.datasource.remote;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;
import com.lorenzorigato.base.network.error.NoInternetError;
import com.lorenzorigato.base.network.service.dto.MovieDTO;

import java.util.ArrayList;

public class MovieRemoteDataSource implements IMovieRemoteDataSource {


    // Private class attributes ********************************************************************
    private IReachabilityChecker reachabilityChecker;


    // Constructor *********************************************************************************
    public MovieRemoteDataSource(IReachabilityChecker reachabilityChecker) {
        this.reachabilityChecker = reachabilityChecker;
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
            movies.add(new Movie(1, "Prova", 5.6, "/system/pictures/photos/000/000/323/thumb/open-uri20191202-4543-1si1cvh?1575302497"));
            movies.add(new Movie(1, "Prova2", 8.6, "/system/pictures/photos/000/000/323/medium/open-uri20191202-4543-1si1cvh?1575302497"));
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
                movieDTO.getCover().getMedium());
    }
}
