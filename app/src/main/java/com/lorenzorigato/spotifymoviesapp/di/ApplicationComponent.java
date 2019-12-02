package com.lorenzorigato.spotifymoviesapp.di;

import com.lorenzorigato.spotifymoviesapp.SpotifyMovieApplication;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
  modules = {
    AndroidSupportInjectionModule.class,
    SecurityModule.class}
)
public interface ApplicationComponent extends AndroidInjector<SpotifyMovieApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SpotifyMovieApplication> {

        @Override
        public abstract ApplicationComponent build();
    }
}
