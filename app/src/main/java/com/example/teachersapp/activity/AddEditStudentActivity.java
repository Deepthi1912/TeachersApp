package com.example.teachersapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachersapp.R;
import com.example.teachersapp.model.Student;

import java.util.Objects;

public class AddEditStudentActivity extends AppCompatActivity {
    private static final String TAG = "AddEditStudentActivity";
    private static final int ADD_STUDENT_REQUEST = 1;
    private static final int EDIT_STUDENT_REQUEST = 3;
    private static final int PICK_FROM_GALLERY_REQUEST = 5;
    public static final String EXTRA_ID = "com.example.teachersapp.EXTRA_ID";
    public static final String EXTRA_FIRST_NAME = "com.example.teachersapp.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.example.teachersapp.EXTRA_LAST_NAME";
    public static final String EXTRA_PHOTO_URI = "com.example.teachersapp.EXTRA_PHOTO_URI";
    private static final String EXTRA_SCORE = "com.example.teachersapp.EXTRA_SCORE";
    public static final String SAVE_STATE = "com.example.teachersapp.AddEditStudentActivity";


    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextView score;
    private ImageButton photoButton;
    private ImageButton addScoreButton;
    private ImageButton subtractScoreButton;
    private boolean isImageSelected = false;
    private Uri selectedImageUri;
    private Student editableStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_student);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        photoButton = findViewById(R.id.photo_imageButton);
        firstName = findViewById(R.id.firstname_input);
        lastName = findViewById(R.id.lastname_input);
        score = findViewById(R.id.edit_score);
        addScoreButton = findViewById(R.id.add_score_button);
        subtractScoreButton = findViewById(R.id.subtract_score_button);

        Intent i = getIntent();
        if (i.hasExtra(Student.class.getCanonicalName())) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.edit_student);
            editableStudent = i.getParcelableExtra(Student.class.getCanonicalName());
            firstName.setText(editableStudent.getFirstName());
            lastName.setText(editableStudent.getLastName());
            photoButton.setImageURI(Uri.parse(editableStudent.getPhotoUri()));
            isImageSelected = true;
            selectedImageUri = Uri.parse(editableStudent.getPhotoUri());
            // Don't pass just int
            score.setText(String.valueOf(editableStudent.getScore()));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_student);
        }

        photoButton.setOnClickListener(v -> {
            Log.d(TAG, "photoButton: pressed!");
            pickFromGallery();
        });

        addScoreButton.setOnClickListener(v -> {
            Log.d(TAG, "addScoreButton: pressed!");
            score.setText(String.valueOf(Integer.parseInt(String.valueOf(score.getText())) + 1));
        });

        subtractScoreButton.setOnClickListener(v -> {
            Log.d(TAG, "subtractScoreButton: pressed!");
            score.setText(String.valueOf(Integer.parseInt(String.valueOf(score.getText())) - 1));
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getIntent().hasExtra(Student.class.getCanonicalName())) {
                    Intent i = new Intent(AddEditStudentActivity.this, StudentActivity.class);
                    i.putExtra(Student.class.getCanonicalName(), (Student) getIntent().getParcelableExtra(Student.class.getCanonicalName()));
                    NavUtils.navigateUpTo(this, i);
                } else {
                    NavUtils.navigateUpTo(this, Objects.requireNonNull(getParentActivityIntent()));
                }
                return true;
            case R.id.action_save_student:
                saveStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveStudent() {
        String firstName = Objects.requireNonNull(this.firstName.getText()).toString();
        String lastName = Objects.requireNonNull(this.lastName.getText()).toString();
        int score = Integer.parseInt(this.score.getText().toString());
        if (!isImageSelected || (firstName.trim().isEmpty() && lastName.trim().isEmpty())) {
            Toast.makeText(this, R.string.data_input_failed, Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent();
            if (getIntent().hasExtra(Student.class.getCanonicalName())) {
                editableStudent.setFirstName(firstName);
                editableStudent.setLastName(lastName);
                editableStudent.setPhotoUri(selectedImageUri.toString());
                editableStudent.setScore(score);
                i.putExtra(Student.class.getCanonicalName(), editableStudent);
            } else {
                i.putExtra(Student.class.getCanonicalName(), new Student(firstName, lastName, selectedImageUri.toString(), score));
            }

            setResult(RESULT_OK, i);
            finish();
        }
        /*if (!isImageSelected || (firstName.trim().isEmpty() && lastName.trim().isEmpty())) {
            Toast.makeText(this, R.string.data_input_failed, Toast.LENGTH_SHORT).show();
        } else if (isImageSelected || selectedImageUri != null){
            Intent i = new Intent();
            if (getIntent().hasExtra(Student.class.getCanonicalName())) {
                editableStudent.setFirstName(firstName);
                editableStudent.setLastName(lastName);
                editableStudent.setPhotoUri(selectedImageUri.toString());
                editableStudent.setScore(score);
                i.putExtra(Student.class.getCanonicalName(), editableStudent);
            } else {
                i.putExtra(Student.class.getCanonicalName(), new Student(firstName, lastName, selectedImageUri.toString(), score));
            }

            setResult(RESULT_OK, i);
            finish();
        }*/
    }

    private void pickFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, PICK_FROM_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_GALLERY_REQUEST:
                    selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        Log.d(TAG, "pickedURI == " + selectedImageUri);
                        ((ImageButton) findViewById(R.id.photo_imageButton)).setImageURI(selectedImageUri);
                        isImageSelected = true;
                    } else {
                        Log.d(TAG, "pickedURI == null");
                    }
                    break;
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score.setText(String.valueOf(savedInstanceState.getString(SAVE_STATE)));
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_STATE, score.getText().toString());
    }
}
