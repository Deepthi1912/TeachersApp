package com.example.teachersapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT * FROM student WHERE name LIKE :name LIMIT 1")
    Student findByName(String name);

    @Insert
    void insertAll(List<Student> students);

    @Insert
    void insert(Student student);

    @Update
    void update (Student student);

    @Delete
    void delete (Student student);

    @Delete
    void deleteAll (Student... students);
}
