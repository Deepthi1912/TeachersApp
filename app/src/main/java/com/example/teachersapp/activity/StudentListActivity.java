package com.example.teachersapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.teachersapp.R;
import com.example.teachersapp.adapter.StudentAdapter;
import com.example.teachersapp.model.Student;
import com.example.teachersapp.viewmodel.StudentViewModel;

import java.util.Objects;

// TODO: provide possibility to cancel deleting a student by swipe (and deleting all students?)
public class StudentListActivity extends AppCompatActivity {
    public static final int ADD_STUDENT_REQUEST = 1;
    public static final int DELETE_STUDENT_REQUEST = 2;
    public static final int EDIT_STUDENT_REQUEST = 3;
    private static final String TAG = "StudentListActivity";
    private static final int EDIT_OR_DELETE_STUDENT_REQUEST = 4;

    private StudentViewModel studentViewModel;

    private StudentAdapter adapter;
    private int selectedContactPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.students);

        RecyclerView recyclerView = findViewById(R.id.student_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        adapter = new StudentAdapter();
        adapter.setOnStudentClickListener((Student student, int position) -> {
            selectedContactPosition = position;
            Intent i = new Intent(StudentListActivity.this, StudentActivity.class);
            i.putExtra(Student.class.getCanonicalName(), student);
            startActivityForResult(i, EDIT_OR_DELETE_STUDENT_REQUEST);
        });

        recyclerView.setAdapter(adapter);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(this, students -> adapter.submitList(students));

        findViewById(R.id.add_student_fab).setOnClickListener(v -> {
            Log.d(TAG, "FAB: pressed!");
            Intent i = new Intent(StudentListActivity.this, AddEditStudentActivity.class);
            startActivityForResult(i, ADD_STUDENT_REQUEST);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                studentViewModel.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(StudentListActivity.this, R.string.student_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STUDENT_REQUEST && resultCode == RESULT_OK) {
            studentViewModel.insert(data.getParcelableExtra(Student.class.getCanonicalName()));
            Toast.makeText(this, R.string.student_saved, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_OR_DELETE_STUDENT_REQUEST && resultCode == RESULT_OK && data != null && data.hasExtra(Student.class.getCanonicalName())) {
            studentViewModel.update(data.getParcelableExtra(Student.class.getCanonicalName()));
            Toast.makeText(this, R.string.student_updated, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_OR_DELETE_STUDENT_REQUEST && resultCode == RESULT_OK) {
            studentViewModel.delete(adapter.getContactAt(selectedContactPosition));
            Toast.makeText(this, R.string.student_deleted, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_students:
                deleteAllStudents();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllStudents() {
        if (adapter.getItemCount() > 0) {
            AlertDialog confirmationDialog = new AlertDialog.Builder(StudentListActivity.this)
                    .setMessage(R.string.delete_all_students_q)
                    .setPositiveButton(R.string.delete, (dialog, which) -> {
                        studentViewModel.deleteAllStudents();
                        Toast.makeText(StudentListActivity.this, R.string.all_students_deleted, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()))
                    .create();
            confirmationDialog.show();
        } else {
            Toast.makeText(StudentListActivity.this, R.string.list_is_empty, Toast.LENGTH_SHORT).show();
        }

    }


}
