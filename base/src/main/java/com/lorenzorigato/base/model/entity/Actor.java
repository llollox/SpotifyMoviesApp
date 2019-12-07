package com.lorenzorigato.base.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity(
        tableName = "actors_table",
        indices = {@Index(value = "movieId")})
public class Actor {


    // Class attributes ****************************************************************************
    @PrimaryKey
    @NotNull
    String id;

    @ColumnInfo
    String name;

    @ColumnInfo
    int gender;

    @ColumnInfo
    String character;

    @ColumnInfo
    String photoUrl;

    @ColumnInfo
    int movieId;


    // Constructor *********************************************************************************
    public Actor(String id, String name, int gender, String character, String photoUrl, int movieId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.character = character;
        this.photoUrl = photoUrl;
        this.movieId = movieId;
    }


    // Class methods *******************************************************************************
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return gender == actor.gender &&
                movieId == actor.movieId &&
                Objects.equals(id, actor.id) &&
                Objects.equals(name, actor.name) &&
                Objects.equals(character, actor.character) &&
                Objects.equals(photoUrl, actor.photoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, character, photoUrl, movieId);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", character='" + character + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", movieId=" + movieId +
                '}';
    }
}
