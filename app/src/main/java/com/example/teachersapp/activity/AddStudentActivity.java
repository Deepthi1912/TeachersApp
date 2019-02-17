package com.example.teachersapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.teachersapp.database.StudentDatabase;
import com.example.teachersapp.R;
import com.example.teachersapp.model.Student;

import java.util.Objects;

public class AddStudentActivity extends AppCompatActivity {
    private static final String TAG = "AddStudentActivity";
    public static final String EXTRA_FIRST_NAME = "com.example.teachersapp.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.example.teachersapp.EXTRA_LAST_NAME";
    public static final String EXTRA_PHOTO_URI = "com.example.teachersapp.EXTRA_PHOTO_URI";
    private static final int GALLERY_REQUEST_CODE = 1;
    private TextInputEditText editTextFirstName;
    private TextInputEditText editTextLastName;
    private ImageButton photoImageButton;
    private boolean isImageSelected = false;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_student);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        photoImageButton = findViewById(R.id.photo_imageButton);
        editTextFirstName = findViewById(R.id.firstname_input);
        editTextLastName = findViewById(R.id.lastname_input);

        photoImageButton.setOnClickListener(v -> {
            Log.d(TAG, "ImageButton: pressed!");
            pickFromGallery();
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
            case R.id.save_student:
                saveStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveStudent() {

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();

        if (!isImageSelected || (firstName.trim().isEmpty() && lastName.trim().isEmpty())) {
            Toast.makeText(this, R.string.data_input_failed, Toast.LENGTH_SHORT).show();
        } else if (isImageSelected){
            String photoUri = selectedImageUri.toString();
            Intent i = new Intent();
            i.putExtra(EXTRA_FIRST_NAME, firstName);
            i.putExtra(EXTRA_LAST_NAME, lastName);
            i.putExtra(EXTRA_PHOTO_URI, photoUri);

            setResult(RESULT_OK, i);
            finish();
        }
    }

    private void pickFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    selectedImageUri = data.getData();
                    ((ImageButton) findViewById(R.id.photo_imageButton)).setImageURI(selectedImageUri);
                    isImageSelected = true;
                    break;
            }
        }
    }

}
