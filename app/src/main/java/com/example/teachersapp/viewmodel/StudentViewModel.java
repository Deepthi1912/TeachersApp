package com.example.teachersapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.teachersapp.db.entity.StudentEntity;
import com.example.teachersapp.db.repos.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository repository;
    private LiveData<List<StudentEntity>> allStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
        allStudents = repository.getAllStudents();
    }

    public void insert(StudentEntity student) {
        repository.insert(student);
    }

    public void insert(List<StudentEntity> students) { repository.insert(students); }

    public void update(StudentEntity student) {
        repository.update(student);
    }

    public void delete(StudentEntity student) {
        repository.delete(student);
    }

    public void deleteAllStudents() {
        repository.deleteAllStudents();
    }

    public LiveData<List<StudentEntity>> getAllStudents() {
        return allStudents;
    }

}
