package com.lorenzorigato.base.di.module;

import android.app.Application;

import androidx.room.Room;

import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    
    @ApplicationScope
    @Provides
    public static SpotifyMoviesDatabase providesDatabase(Application application) {
        return Room.databaseBuilder(
                application.getApplicationContext(),
                SpotifyMoviesDatabase.class, "spotify_movies_database")
            .build();
    }
}
