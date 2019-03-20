package com.example.teachersapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teachersapp.db.entity.StudentEntity;
import com.example.teachersapp.model.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    LiveData<List<StudentEntity>> getAll();

    @Insert
    void insert(StudentEntity student);

    @Update
    void update (StudentEntity student);

    @Delete
    void delete (StudentEntity student);

    @Query("DELETE FROM student")
    void deleteAll();

    @Insert
    void insert(List<StudentEntity> students);

}
