package com.example.teachersapp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.teachersapp.CreateStudentActivity;
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
    private static final String TAG = "StudentsActivity";

    private Parcelable mRecyclerViewState;

    private List<Student> students = new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.students);

        recyclerView = findViewById(R.id.student_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        adapter = new StudentAdapter(students, StudentsActivity.this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.add_student_fab).setOnClickListener(v -> {
            Log.d(TAG, "FAB: pressed!");
            Intent i = new Intent(StudentsActivity.this, CreateStudentActivity.class);
            startActivity(i);
            /*Uri uri = Uri.parse("android.resource://com.example.project/" + avatars.getResourceId(new Random().nextInt(avatars.length()), 0));
            Student newStudent;
            newStudent = new Student(String.valueOf(new Random().nextInt(10000)), uri.toString());
            students.add(newStudent);
            MyDatabase.getDatabase(getApplicationContext()).studentDAO().insert(newStudent);
            adapter.notifyDataSetChanged();*/

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.students_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                students.clear();
                MyDatabase.getDatabase(getApplicationContext()).studentDAO().deleteAll();
                adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }


}
