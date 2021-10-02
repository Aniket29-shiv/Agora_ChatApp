package io.agora.activity;

import androidx.room.Dao;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDao {


    @Query("SELECT * FROM chatDatabaseHelper")
    List<ChatDatabaseHelper> getAll();

    @Insert
    void insert(ChatDatabaseHelper task);

    @Delete
    void delete(ChatDatabaseHelper task);

    @Update
    void update(ChatDatabaseHelper task);

}
