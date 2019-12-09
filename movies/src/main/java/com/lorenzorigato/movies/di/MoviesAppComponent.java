package com.lorenzorigato.movies.di;

import android.app.Application;

import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.di.module.RepositoryModule;
import com.lorenzorigato.base.di.module.SecurityModule;
import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.movies.MoviesApplication;
import com.lorenzorigato.movies.di.module.ConfigModule;
import com.lorenzorigato.movies.di.module.NavigatorModule;
import com.lorenzorigato.movies.di.module.UtilModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@ApplicationScope
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                SecurityModule.class,
                NavigatorModule.class,
                UtilModule.class,
                RepositoryModule.class,
                MoviesModule.class,
                ConfigModule.class}
)
public interface MoviesAppComponent extends AndroidInjector<MoviesApplication> {

    @Component.Builder
    public abstract class Builder extends AndroidInjector.Builder<MoviesApplication> {

        @BindsInstance
        public abstract Builder setApplication(Application application);

        @Override
        public abstract MoviesAppComponent build();
    }

    public SpotifyMoviesDatabase getDatabase();
}