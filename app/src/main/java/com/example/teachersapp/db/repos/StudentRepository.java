package com.example.teachersapp.db.repos;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.teachersapp.db.entity.StudentEntity;
import com.example.teachersapp.db.dao.StudentDao;
import com.example.teachersapp.db.AppDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StudentRepository {
    private StudentDao studentDao;
    private LiveData<List<StudentEntity>> allStudents;

    private Executor executor = Executors.newSingleThreadExecutor();

    public StudentRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        studentDao = database.studentDAO();
        allStudents = database.studentDAO().getAll();
    }

    public void insert(StudentEntity student) {
        executor.execute(() -> studentDao.insert(student));
    }

    public void update(StudentEntity student) {
        executor.execute(() -> studentDao.update(student));
    }

    public void delete (StudentEntity student) {
        executor.execute(() -> studentDao.delete(student));
    }

    public void deleteAllStudents() {
        executor.execute(() -> studentDao.deleteAll());
    }

    public LiveData<List<StudentEntity>> getAllStudents() {
        return allStudents;
    }

    public void insertAll(List<StudentEntity> students) {
        executor.execute(() -> studentDao.insertAll(students));
    }


}
