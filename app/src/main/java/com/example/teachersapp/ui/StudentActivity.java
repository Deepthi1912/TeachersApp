package com.example.teachersapp.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachersapp.R;
import com.example.teachersapp.db.entity.StudentEntity;

import java.util.Objects;

public class StudentActivity extends AppCompatActivity {
    private static final String TAG = "StudentActivity";
    public static final int EDIT_STUDENT_REQUEST = 3;
    private TextView firstName;
    private TextView lastName;
    private TextView score;
    private ImageView photo;
    private StudentEntity student;
    private ImageView enlargedPhoto;
    private ConstraintLayout layout;

    @Override
    public void onBackPressed() {
        if (enlargedPhoto.getVisibility() == View.VISIBLE)
            enlargedPhoto.setVisibility(View.INVISIBLE);
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setTitle(R.string.student_info);

        layout = findViewById(R.id.student_layout);
        firstName = findViewById(R.id.student_firstname);
        lastName = findViewById(R.id.student_lastname);
        score = findViewById(R.id.student_score);
        photo = findViewById(R.id.student_photo);
        enlargedPhoto = findViewById(R.id.student_photo_big);

        Intent i = getIntent();
        student = i.getParcelableExtra(StudentEntity.class.getCanonicalName());
        editView(student);

        photo.setOnClickListener(v -> {
            enlargedPhoto.setImageBitmap(BitmapFactory.decodeByteArray(student.getPhoto(), 0, student.getPhoto().length));
            enlargedPhoto.setVisibility(View.VISIBLE);
        });

        layout.setOnClickListener(v -> {
            enlargedPhoto.setVisibility(View.INVISIBLE);
        });

        enlargedPhoto.setOnClickListener(v -> {
            if (enlargedPhoto.getVisibility() == View.VISIBLE) {
                enlargedPhoto.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        enlargedPhoto.setVisibility(View.INVISIBLE);
        switch (item.getItemId()) {
            case R.id.action_edit_student:
                editStudent();
                return true;
            case R.id.action_delete_student:
                deleteStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: СДЕЛАТЬ ФРАГМЕНТ ДАИЛОГА ДЛЯ УДАЛЕНИЯ (DialogFragment)
    // Ответные действия диалога ловис с помощью интерфейса handleDialogListener, которые расширяет активити
    // такие диалоги переживают изменение конфигурации устройства
    // можно использовать метод saveInstanceState
    private void deleteStudent() {
        AlertDialog confirmationDialog = new AlertDialog.Builder(StudentActivity.this)
                .setMessage(R.string.delete_student_q)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    setResult(RESULT_OK);
                    finish();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()))
                .create();
        confirmationDialog.show();
    }

    private void editStudent() {
        // TODO: AddEditStudentActivity
        Intent i = new Intent(StudentActivity.this, AddEditStudentActivity.class);
        i.putExtra(StudentEntity.class.getCanonicalName(), student);
        startActivityForResult(i, EDIT_STUDENT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_STUDENT_REQUEST && resultCode == RESULT_OK) {
            student = data.getParcelableExtra(StudentEntity.class.getCanonicalName());
            editView(student);
            Toast.makeText(this, R.string.student_updated, Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra(StudentEntity.class.getCanonicalName(), student);
            setResult(RESULT_OK, i);
        }
    }

    private void editView(StudentEntity student) {
        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        score.setText(String.valueOf(student.getScore()));
        photo.setImageBitmap(BitmapFactory.decodeByteArray(student.getPhoto(), 0, student.getPhoto().length));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        enlargedPhoto.setVisibility(View.INVISIBLE);
    }
}
