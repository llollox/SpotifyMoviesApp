package com.lorenzorigato.base.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.model.datasource.local.interfaces.IGenreLocalDataSource;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IGenreRemoteDataSource;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.model.repository.GenreRepository;
import com.lorenzorigato.base.model.repository.MovieRepository;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DataSourceModule.class})
public class RepositoryModule {

    @ApplicationScope
    @Provides
    public static IGenreRepository providesGenreRepository(
            IGenreLocalDataSource localDataSource,
            IGenreRemoteDataSource remoteDataSource) {
        return new GenreRepository(localDataSource, remoteDataSource);
    }

    @ApplicationScope
    @Provides
    public static IMovieRepository providesMovieRepository(
            IMovieLocalDataSource localDataSource,
            IMovieRemoteDataSource remoteDataSource) {

        return new MovieRepository(localDataSource, remoteDataSource);
    }
}
