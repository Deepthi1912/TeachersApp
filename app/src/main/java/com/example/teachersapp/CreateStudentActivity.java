package com.example.teachersapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Objects;

public class CreateStudentActivity extends AppCompatActivity {
    private static final String TAG = "CreateStudentActivity";
    private static final int GALLERY_REQUEST_CODE = 1;
    private boolean isImageSelected = false;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private ImageButton photoImageButton;
    private Button saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_student);
        photoImageButton = findViewById(R.id.photo_imageButton);
        firstName = findViewById(R.id.firstname_input);
        lastName = findViewById(R.id.lastname_input);
        saveButton = findViewById(R.id.save_student_button);
        photoImageButton.setOnClickListener(v -> {
            Log.d(TAG, "ImageButton: pressed!");
            pickFromGallery();
        });

        saveButton.setOnClickListener(v -> {
            Log.d(TAG, "Save button: pressed!");
            if (isImageSelected && nameNotEmpty()) {
                // TODO: update database
            } else {
                Toast.makeText(CreateStudentActivity.this, R.string.data_input_failed, Toast.LENGTH_SHORT).show();
            }
        });


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
                    Uri selectedImage = data.getData();
                    ((ImageButton) findViewById(R.id.photo_imageButton)).setImageURI(selectedImage);
                    isImageSelected = true;
                    break;
            }
        }
    }

    private boolean nameNotEmpty() {
        return (firstName.getText() != null && !firstName.getText().toString().trim().equals("")) || (lastName.getText() != null && !lastName.getText().toString().trim().equals(""));
    }
}
