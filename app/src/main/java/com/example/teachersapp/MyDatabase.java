package com.example.teachersapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Student.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase db;

    public abstract StudentDao studentDAO();

    public static MyDatabase getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return db;
    }
    public static void destroyDatabase() {
        db = null;
    }
}
