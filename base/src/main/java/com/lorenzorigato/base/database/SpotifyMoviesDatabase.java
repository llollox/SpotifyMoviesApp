package com.lorenzorigato.base.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lorenzorigato.base.database.dao.ActorDao;
import com.lorenzorigato.base.database.dao.GenreDao;
import com.lorenzorigato.base.database.dao.GenreMovieJoinDao;
import com.lorenzorigato.base.database.dao.MovieDao;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;

@Database(entities = {Genre.class, Movie.class, GenreMovieJoin.class, Actor.class}, version = 1)
public abstract class SpotifyMoviesDatabase extends RoomDatabase {

    public abstract ActorDao getActorDao();
    public abstract GenreDao getGenreDao();
    public abstract MovieDao getMovieDao();
    public abstract GenreMovieJoinDao getGenreMovieJoinDao();
}