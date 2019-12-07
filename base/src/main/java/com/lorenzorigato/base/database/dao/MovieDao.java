package com.lorenzorigato.base.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import java.util.List;

@Dao
public abstract class MovieDao {

    private SpotifyMoviesDatabase db;

    public MovieDao(SpotifyMoviesDatabase db) {
        this.db = db;
    }

    @Transaction
    @Query("SELECT * FROM movies_table WHERE id = :id")
    public abstract LiveData<MovieWithActors> findById(int id);

    @Query("SELECT * FROM movies_table WHERE id IN (:ids)")
    public abstract LiveData<List<Movie>> findByIds(List<Integer> ids);

    @Query("SELECT * FROM movies_table")
    public abstract List<Movie> getAll();

    @Query("SELECT * FROM movies_table WHERE isFavorite = 1")
    public abstract LiveData<List<Movie>> findFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(Movie... movies);

    @Transaction
    public void insertAll(List<Movie> movies, List<GenreMovieJoin> genreMovieJoins, List<Actor> actors) {
        this.insertAll(movies.toArray(new Movie[movies.size()]));
        this.db.getGenreMovieJoinDao().insertAll(genreMovieJoins.toArray(new GenreMovieJoin[genreMovieJoins.size()]));
        this.db.getActorDao().insertAll(actors);
    }
}
