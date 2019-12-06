package com.lorenzorigato.base.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "movies_table")
public class Movie {


    // Class attributes ****************************************************************************
    @PrimaryKey
    int id;

    @ColumnInfo
    String title;

    @ColumnInfo
    double rating;

    @ColumnInfo
    boolean isFavorite = false;

    @ColumnInfo
    String posterFullPath;


    // Constructor *********************************************************************************
    public Movie(int id, String title, double rating, String posterFullPath) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.posterFullPath = posterFullPath;
    }


    // Class methods *******************************************************************************
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPosterFullPath() {
        return posterFullPath;
    }

    public void setPosterFullPath(String posterFullPath) {
        this.posterFullPath = posterFullPath;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                Double.compare(movie.rating, rating) == 0 &&
                isFavorite == movie.isFavorite &&
                Objects.equals(title, movie.title) &&
                Objects.equals(posterFullPath, movie.posterFullPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, rating, isFavorite, posterFullPath);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", isFavorite=" + isFavorite +
                ", posterFullPath='" + posterFullPath + '\'' +
                '}';
    }
}
