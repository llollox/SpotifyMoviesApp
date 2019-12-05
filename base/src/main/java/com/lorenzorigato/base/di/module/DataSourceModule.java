package com.lorenzorigato.base.di.module;

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

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class})
public class DataSourceModule {

    @ApplicationScope
    @Provides
    public static IGenreLocalDataSource providesGenreLocalDataSource() {
        return new GenreLocalDataSource();
    }

    @ApplicationScope
    @Provides
    public static IGenreRemoteDataSource providesGenreRemoteDataSource(IReachabilityChecker reachabilityChecker) {
        return new GenreRemoteDataSource(reachabilityChecker);
    }

    @ApplicationScope
    @Provides
    public static IMovieLocalDataSource providesMovieLocalDataSource() {
        return new MovieLocalDataSource();
    }

    @ApplicationScope
    @Provides
    public static IMovieRemoteDataSource providesMovieRemoteDataSource(IReachabilityChecker reachabilityChecker) {
        return new MovieRemoteDataSource(reachabilityChecker);
    }
}
