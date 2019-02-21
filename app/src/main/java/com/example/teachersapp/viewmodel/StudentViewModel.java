package com.example.teachersapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.teachersapp.model.Student;
import com.example.teachersapp.repos.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository repository;
    private LiveData<List<Student>> allStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
        allStudents = repository.getAllStudents();
    }

    public void insert(Student student) {
        repository.insert(student);
    }

    public void insert(List<Student> students) { repository.insert(students); }

    public void update(Student student) {
        repository.update(student);
    }

    public void delete(Student student) {
        repository.delete(student);
    }

    public void deleteAllStudents() {
        repository.deleteAllStudents();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }
}
