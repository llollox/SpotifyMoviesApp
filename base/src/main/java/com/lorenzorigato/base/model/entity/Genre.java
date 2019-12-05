package com.lorenzorigato.base.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * This class represent the Genre model into the application.
 *
 * The index on name class has the following benefits.
 * First of all it makes the search faster, in O(log(n)) time,
 * instead of search through all the records, which has a complexity of (O(n)).
 *
 * The second advantage is that, setting the parameter unique as TRUE,
 * it prevents to insert into the DB two genres with the same name.
 */

@Entity(
        tableName = "genres_table",
        indices = {@Index(value = "name", unique = true)})
public class Genre {

    // Class attributes ****************************************************************************
    @PrimaryKey
    int id;

    @ColumnInfo
    String name;


    // Constructor *********************************************************************************
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }


    // Class methods *******************************************************************************
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id == genre.id &&
                Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}