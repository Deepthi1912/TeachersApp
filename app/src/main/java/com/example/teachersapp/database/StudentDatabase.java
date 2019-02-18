package com.example.teachersapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.media.MediaRouter;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.teachersapp.R;
import com.example.teachersapp.model.Student;

import java.util.Random;

@Database(entities = {Student.class}, version = 3)
public abstract class StudentDatabase extends RoomDatabase {

    private static StudentDatabase instance;

    public abstract StudentDao studentDAO();

    public static synchronized StudentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), StudentDatabase.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();


        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDao studentDao;

        public PopulateDbAsyncTask(StudentDatabase db) {
            this.studentDao = db.studentDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // pre-populate database
            return null;
        }
    }
}
