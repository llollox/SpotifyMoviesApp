package com.lorenzorigato.base.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieWithActors {

    @Embedded
    public Movie movie;

    @Relation(
            parentColumn = "id",
            entityColumn = "movieId")
    public List<Actor> actors;


    public Movie getMovie() {
        return movie;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
