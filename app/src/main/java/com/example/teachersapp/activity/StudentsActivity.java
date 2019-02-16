package com.example.teachersapp.activity;

import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.teachersapp.MyDatabase;
import com.example.teachersapp.R;
import com.example.teachersapp.Student;
import com.example.teachersapp.adapter.StudentAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class StudentsActivity extends AppCompatActivity {
    private static final String STATE_KEY = "students_activity";
    private Parcelable mRecyclerViewState;

    private List<Student> students = new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private TypedArray avatars;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.open_student);

        recyclerView = findViewById(R.id.student_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        adapter = new StudentAdapter(MyDatabase.getDatabase(StudentsActivity.this).studentDAO().getAll(), StudentsActivity.this);
        recyclerView.setAdapter(adapter);
        avatars = getResources().obtainTypedArray(R.array.avatars_array);


        findViewById(R.id.add_student_fab).setOnClickListener(v -> {
            Uri uri = Uri.parse("android.resource://com.example.project/" + avatars.getResourceId(new Random().nextInt(avatars.length()), 0));
            Student newStudent;
            newStudent = new Student(String.valueOf(new Random().nextInt(10000)), uri.toString());
            students.add(newStudent);
            MyDatabase.getDatabase(getApplicationContext()).studentDAO().insert(newStudent);
            adapter.notifyDataSetChanged();

        });
    }


}
