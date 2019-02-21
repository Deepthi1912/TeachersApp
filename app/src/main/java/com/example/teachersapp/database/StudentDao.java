package com.example.teachersapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teachersapp.model.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAll();

    @Insert
    void insert(Student student);

    @Update
    void update (Student student);

    @Delete
    void delete (Student student);

    @Query("DELETE FROM student")
    void deleteAll();

    @Insert
    void insert(List<Student> students);
}
