package com.example.teachersapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.teachersapp.database.StudentDatabase;
import com.example.teachersapp.R;
import com.example.teachersapp.adapter.StudentAdapter;
import com.example.teachersapp.model.Student;
import com.example.teachersapp.viewmodel.StudentViewModel;

import java.util.Objects;

public class StudentsActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    private static final String STATE_KEY = "students_activity";
    private static final String TAG = "StudentsActivity";

    private StudentViewModel studentViewModel;

    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.students);

        recyclerView = findViewById(R.id.student_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(this, students -> {
            adapter.setStudents(students);
            adapter.notifyDataSetChanged();
        });

        findViewById(R.id.add_student_fab).setOnClickListener(v -> {
            Log.d(TAG, "FAB: pressed!");
            Intent i = new Intent(StudentsActivity.this, AddStudentActivity.class);
            startActivityForResult(i, ADD_NOTE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String firstName = data.getStringExtra(AddStudentActivity.EXTRA_FIRST_NAME);
            String lastName = data.getStringExtra(AddStudentActivity.EXTRA_LAST_NAME);
            String photoUri = data.getStringExtra(AddStudentActivity.EXTRA_PHOTO_URI);
            Student student = new Student(firstName, lastName, photoUri);
            studentViewModel.insert(student);
            Toast.makeText(this, R.string.student_saved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.students_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                studentViewModel.deleteAllStudents();
        }
        return super.onOptionsItemSelected(item);
    }


}
