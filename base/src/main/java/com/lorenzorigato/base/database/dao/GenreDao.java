package com.lorenzorigato.base.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM genres_table")
    List<Genre> findAll();

    @Query("SELECT * FROM genres_table WHERE name == :name")
    Genre findByName(String name);

    @Query("SELECT * FROM genres_table WHERE name == :name")
    LiveData<Genre> findByNameLiveData(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Genre... genres);
}
