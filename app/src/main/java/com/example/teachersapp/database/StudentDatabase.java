package com.example.teachersapp.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.example.teachersapp.model.Student;

@Database(entities = {Student.class}, version = 4)
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
