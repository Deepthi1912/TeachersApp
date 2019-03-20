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
//        new InsertStudentAsyncTask(studentDao).execute(student);
    }

    public void update(StudentEntity student) {
        new UpdateStudentAsyncTask(studentDao).execute(student);
    }

    public void delete (StudentEntity student) {
        new DeleteStudentAsyncTask(studentDao).execute(student);
    }

    public void deleteAllStudents() {
        new DeleteAllStudentsAsyncTask(studentDao).execute();
    }

    public LiveData<List<StudentEntity>> getAllStudents() {
        return allStudents;
    }

    public void insert(List<StudentEntity> students) {
        new InsertStudentsAsyncTask(studentDao).execute(students.toArray(new StudentEntity[]{}));
    }

    private static class InsertStudentAsyncTask extends AsyncTask<StudentEntity, Void, Void> {
        private StudentDao studentDao;

        public InsertStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(StudentEntity... students) {
            studentDao.insert(students[0]);
            return null;
        }
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<StudentEntity, Void, Void> {
        private StudentDao studentDao;

        public UpdateStudentAsyncTask (StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(StudentEntity... students) {
            studentDao.update(students[0]);
            return null;
        }
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<StudentEntity, Void, Void> {
        private StudentDao studentDao;

        public DeleteStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(StudentEntity... students) {
            studentDao.delete(students[0]);
            return null;
        }
    }

    private static class DeleteAllStudentsAsyncTask extends AsyncTask<StudentEntity, Void, Void> {
        private StudentDao studentDao;

        public DeleteAllStudentsAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(StudentEntity... students) {
            studentDao.deleteAll();
            return null;
        }
    }

    private static class InsertStudentsAsyncTask extends AsyncTask<StudentEntity, Void, Void>{
        private StudentDao studentDao;
        public InsertStudentsAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(StudentEntity... students) {
            studentDao.insert(Arrays.asList(students));
            return null;
        }
    }
}
