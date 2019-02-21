package com.example.teachersapp.repos;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.teachersapp.database.StudentDao;
import com.example.teachersapp.database.StudentDatabase;
import com.example.teachersapp.model.Student;

import java.util.Arrays;
import java.util.List;

public class StudentRepository {
    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {
        StudentDatabase database = StudentDatabase.getInstance(application);
        studentDao = database.studentDAO();
        allStudents = database.studentDAO().getAll();
    }

    public void insert(Student student) {
        new InsertStudentAsyncTask(studentDao).execute(student);
    }

    public void update(Student student) {
        new UpdateStudentAsyncTask(studentDao).execute(student);
    }

    public void delete (Student student) {
        new DeleteStudentAsyncTask(studentDao).execute(student);
    }

    public void deleteAllStudents() {
        new DeleteAllStudentsAsyncTask(studentDao).execute();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insert(List<Student> students) {
        new InsertStudentsAsyncTask(studentDao).execute(students.toArray(new Student[]{}));
    }

    private static class InsertStudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao studentDao;

        public InsertStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insert(students[0]);
            return null;
        }
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao studentDao;

        public UpdateStudentAsyncTask (StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.update(students[0]);
            return null;
        }
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao studentDao;

        public DeleteStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.delete(students[0]);
            return null;
        }
    }

    private static class DeleteAllStudentsAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao studentDao;

        public DeleteAllStudentsAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.deleteAll();
            return null;
        }
    }

    private class InsertStudentsAsyncTask extends AsyncTask<Student, Void, Void>{
        private StudentDao studentDao;
        public InsertStudentsAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insert(Arrays.asList(students));
            return null;
        }
    }
}
