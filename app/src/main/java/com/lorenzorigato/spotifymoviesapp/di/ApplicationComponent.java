package com.lorenzorigato.spotifymoviesapp.di;

import android.app.Application;

import com.lorenzorigato.base.di.module.RepositoryModule;
import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.movies.di.MoviesModule;
import com.lorenzorigato.spotifymoviesapp.SpotifyMovieApplication;
import com.lorenzorigato.spotifymoviesapp.di.module.NavigatorModule;
import com.lorenzorigato.base.di.module.SecurityModule;
import com.lorenzorigato.spotifymoviesapp.di.module.UtilModule;

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
          MoviesModule.class}
)
public interface ApplicationComponent extends AndroidInjector<SpotifyMovieApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SpotifyMovieApplication> {

        @BindsInstance
        public abstract Builder setApplication(Application application);

        @Override
        public abstract ApplicationComponent build();
    }
}
