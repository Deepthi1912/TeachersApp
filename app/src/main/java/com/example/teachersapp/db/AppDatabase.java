package com.example.teachersapp.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.example.teachersapp.db.entity.StudentEntity;
import com.example.teachersapp.db.dao.StudentDao;

@Database(entities = {StudentEntity.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "app_db.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract StudentDao studentDAO();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            //.addCallback(roomCallback)
                            .build();
                }
            }
        }
        return instance;
    }

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private StudentDao studentDao;
//
//        public PopulateDbAsyncTask(AppDatabase db) {
//            this.studentDao = db.studentDAO();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            // pre-populate database
//            return null;
//        }
//    }
}
