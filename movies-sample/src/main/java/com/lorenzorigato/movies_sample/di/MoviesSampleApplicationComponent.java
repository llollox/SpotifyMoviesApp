package com.lorenzorigato.movies_sample.di;

import android.app.Application;

import com.lorenzorigato.base.di.module.SecurityModule;
import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.movies.di.MoviesModule;
import com.lorenzorigato.movies_sample.MoviesSampleApplication;
import com.lorenzorigato.movies_sample.di.module.ActivityModule;
import com.lorenzorigato.movies_sample.di.module.ConfigModule;
import com.lorenzorigato.movies_sample.di.module.FakeRepositoryModule;
import com.lorenzorigato.movies_sample.di.module.NavigatorModule;
import com.lorenzorigato.movies_sample.di.module.UtilModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@ApplicationScope
@Component(
  modules = {
          ActivityModule.class,
          AndroidSupportInjectionModule.class,
          SecurityModule.class,
          NavigatorModule.class,
          UtilModule.class,
          FakeRepositoryModule.class,
          MoviesModule.class,
          ConfigModule.class}
)
public interface MoviesSampleApplicationComponent extends AndroidInjector<MoviesSampleApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MoviesSampleApplication> {

        @BindsInstance
        public abstract Builder setApplication(Application application);

        @Override
        public abstract MoviesSampleApplicationComponent build();
    }
}
