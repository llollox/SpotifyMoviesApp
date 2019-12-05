package com.lorenzorigato.base.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.util.Objects;

/**
 * This class represent the join entity between Genre and Movie.
 * For this reason it has two attributes which represent the foreign key on Genre and Movie models.
 *
 * This class contains also an index attribute which is useful to guarantee
 * that the values are always sorted in the order in which they was fetched by the server.
 * This is important because it prevents the case in which the order changes between when
 * the movies are fetched from internet or from the db.
 */

@Entity(
        tableName = "genres_movies_table",
        primaryKeys = {"genre_id", "movie_id"},
        indices = {
                @Index(value = "genre_id"),
                @Index(value = "movie_id")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Genre.class,
                        parentColumns = {"id"},
                        childColumns = {"genre_id"}),

                @ForeignKey(
                        entity = Movie.class,
                        parentColumns = {"id"},
                        childColumns = {"movie_id"})
        })
public class GenreMovieJoin {

    // Class attributes ****************************************************************************
    @ColumnInfo(name = "genre_id")
    int genreId;

    @ColumnInfo(name = "movie_id")
    int movieId;


    // Constructor *********************************************************************************
    public GenreMovieJoin(int genreId, int movieId) {
        this.genreId = genreId;
        this.movieId = movieId;
    }


    // Class methods *******************************************************************************
    public int getGenreId() {
        return genreId;
    }

    public int getMovieId() {
        return movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreMovieJoin that = (GenreMovieJoin) o;
        return genreId == that.genreId &&
                movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, movieId);
    }

    @Override
    public String toString() {
        return "GenreMovieJoin{" +
                "genreId=" + genreId +
                ", movieId=" + movieId +
                '}';
    }
}
