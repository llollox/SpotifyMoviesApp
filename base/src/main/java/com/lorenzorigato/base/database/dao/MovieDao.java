package com.lorenzorigato.base.database.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
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


    // Private class attributes ********************************************************************
    private SpotifyMoviesDatabase db;


    // Constructor *********************************************************************************
    public MovieDao(SpotifyMoviesDatabase db) {
        this.db = db;
    }


    // Class methods *******************************************************************************
    @Query("SELECT * FROM movies_table WHERE id = :id")
    public abstract Movie findById(int id);

    @Transaction
    @Query("SELECT * FROM movies_table WHERE id = :id")
    public abstract LiveData<MovieWithActors> findByIdWithActors(int id);

    @Query("SELECT * FROM movies_table WHERE isFavorite = 1")
    public abstract DataSource.Factory<Integer, Movie> findFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<Movie> movies);

    @Transaction
    public void insertAll(List<Movie> movies, List<GenreMovieJoin> genreMovieJoins, List<Actor> actors) {
        this.insertAll(movies);
        this.db.getGenreMovieJoinDao().insertAll(genreMovieJoins);
        this.db.getActorDao().insertAll(actors);
    }
}
