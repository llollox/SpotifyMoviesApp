package com.lorenzorigato.base.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;

import java.util.List;

@Dao
public interface GenreMovieJoinDao {

    @Query("SELECT * " +
            "FROM movies_table INNER JOIN genres_movies_table ON movies_table.id = genres_movies_table.movie_id " +
            "WHERE genres_movies_table.genre_id = :genreId " +
            "ORDER BY movies_table.title")
    LiveData<List<Movie>> findMoviesByGenreId(int genreId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(GenreMovieJoin... genreMovieJoins);
}
