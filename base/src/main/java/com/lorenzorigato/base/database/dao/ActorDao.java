package com.lorenzorigato.base.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.lorenzorigato.base.model.entity.Actor;

import java.util.List;

@Dao
public interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Actor> actors);
}
