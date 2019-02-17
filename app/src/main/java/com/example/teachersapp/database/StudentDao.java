package com.example.teachersapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

}
