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

    public MovieWithActors(Movie movie, List<Actor> actors) {
        this.movie = movie;
        this.actors = actors;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
