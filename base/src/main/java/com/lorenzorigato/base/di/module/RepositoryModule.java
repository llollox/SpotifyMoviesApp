package com.lorenzorigato.base.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.model.datasource.local.IGenreLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.IGenreRemoteDataSource;
import com.lorenzorigato.base.model.repository.GenreRepository;
import com.lorenzorigato.base.model.repository.IGenreRepository;

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
}
