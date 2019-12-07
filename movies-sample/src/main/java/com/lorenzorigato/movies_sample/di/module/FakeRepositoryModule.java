package com.lorenzorigato.movies_sample.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies_sample.model.repository.FakeGenreRepository;
import com.lorenzorigato.movies_sample.model.repository.FakeMovieRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class FakeRepositoryModule {

    @ApplicationScope
    @Provides
    public static IGenreRepository providesGenreRepository() {
        return new FakeGenreRepository();
    }

    @ApplicationScope
    @Provides
    public static IMovieRepository providesMovieRepository() {
        return new FakeMovieRepository();
    }
}

