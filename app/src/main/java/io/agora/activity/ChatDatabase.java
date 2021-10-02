package io.agora.activity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChatDatabaseHelper.class}, version = 1, exportSchema = true)
public abstract class ChatDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();
}
