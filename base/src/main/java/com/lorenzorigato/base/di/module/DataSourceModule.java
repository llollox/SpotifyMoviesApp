package com.lorenzorigato.base.di.module;

import com.lorenzorigato.base.config.interfaces.IConfiguration;
import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.database.dao.GenreDao;
import com.lorenzorigato.base.database.dao.GenreMovieJoinDao;
import com.lorenzorigato.base.database.dao.MovieDao;
import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.model.datasource.local.GenreLocalDataSource;
import com.lorenzorigato.base.model.datasource.local.MovieLocalDataSource;
import com.lorenzorigato.base.model.datasource.local.interfaces.IGenreLocalDataSource;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.GenreRemoteDataSource;
import com.lorenzorigato.base.model.datasource.remote.MovieRemoteDataSource;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IGenreRemoteDataSource;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;
import com.lorenzorigato.base.network.service.GenreService;
import com.lorenzorigato.base.network.service.MovieService;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class, DatabaseModule.class})
public class DataSourceModule {

    @ApplicationScope
    @Provides
    public static IGenreLocalDataSource providesGenreLocalDataSource(SpotifyMoviesDatabase db) {
        GenreDao genreDao = db.getGenreDao();
        return new GenreLocalDataSource(genreDao);
    }

    @ApplicationScope
    @Provides
    public static IGenreRemoteDataSource providesGenreRemoteDataSource(GenreService genreService, IReachabilityChecker reachabilityChecker) {
        return new GenreRemoteDataSource(genreService, reachabilityChecker);
    }

    @ApplicationScope
    @Provides
    public static IMovieLocalDataSource providesMovieLocalDataSource(SpotifyMoviesDatabase db) {
        MovieDao movieDao = db.getMovieDao();
        GenreMovieJoinDao genreMovieJoinDao = db.getGenreMovieJoinDao();
        return new MovieLocalDataSource(genreMovieJoinDao, movieDao);
    }

    @ApplicationScope
    @Provides
    public static IMovieRemoteDataSource providesMovieRemoteDataSource(MovieService movieService, IReachabilityChecker reachabilityChecker, IConfiguration configuration) {
        return new MovieRemoteDataSource(movieService, reachabilityChecker, configuration);
    }
}
