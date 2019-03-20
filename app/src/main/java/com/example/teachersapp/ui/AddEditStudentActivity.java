package com.example.teachersapp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teachersapp.R;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachersapp.db.entity.StudentEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

public class AddEditStudentActivity extends AppCompatActivity {
    private static final String TAG = "AddEditStudentActivity";
    private static final int REQUEST_SELECT_PHOTO = 1;
    public static final int REQUEST_TAKE_PHOTO = 2;
    public static final String SAVE_STATE = "com.example.teachersapp.AddEditStudentActivity";

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextView score;
    private ImageButton photoButton;
    private ImageButton addScoreButton;
    private ImageButton subtractScoreButton;

    private ProgressDialog progressBar;
    private boolean isImageChanged = false;
    private boolean isImageSelected = false;
    private boolean isDefaultImageSelected = true;
    private StudentEntity editableStudent;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private Bitmap thumbnail;
    private String currentPhotoPath;


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
        if (i.hasExtra(StudentEntity.class.getCanonicalName())) {
            setTitle(R.string.edit_student);
            editableStudent = i.getParcelableExtra(StudentEntity.class.getCanonicalName());
            firstName.setText(editableStudent.getFirstName());
            lastName.setText(editableStudent.getLastName());
            photoButton.setImageBitmap(BitmapFactory.decodeByteArray(editableStudent.getPhoto(), 0, editableStudent.getPhoto().length));
            isImageSelected = true;
            score.setText(String.valueOf(editableStudent.getScore()));
        } else {
            setTitle(R.string.add_student);
        }

        photoButton.setOnClickListener(v -> {
            Log.d(TAG, "photoButton: pressed!");
            new AlertDialog.Builder(AddEditStudentActivity.this)
                    .setTitle(R.string.set_image)
                    .setItems(R.array.uploadImages, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                dispatchSelectImageIntent();
                                break;
                            case 1:
                                dispatchTakePictureIntent();
                                break;
                            case 2:
                                photoButton.setImageResource(R.drawable.student);
                                isDefaultImageSelected = true;
                                isImageChanged = true;
                                break;
                        }
                    })
                    .show();
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

    private void dispatchSelectImageIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(photoPickerIntent, REQUEST_SELECT_PHOTO);
        }
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
                if (getIntent().hasExtra(StudentEntity.class.getCanonicalName())) {
                    Intent i = new Intent(AddEditStudentActivity.this, StudentActivity.class);
                    i.putExtra(StudentEntity.class.getCanonicalName(), (StudentEntity) getIntent().getParcelableExtra(StudentEntity.class.getCanonicalName()));
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

    @TargetApi(Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred while creating the File");
                e.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.teachersapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = photoButton.getWidth();
        int targetH = photoButton.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        photoButton.setImageBitmap(bitmap);
        isImageChanged = true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void saveStudent() {
        String firstName = Objects.requireNonNull(this.firstName.getText()).toString();
        String lastName = Objects.requireNonNull(this.lastName.getText()).toString();
        int score = Integer.parseInt(this.score.getText().toString());
        if ((!isImageSelected && !isDefaultImageSelected) || (firstName.trim().isEmpty() && lastName.trim().isEmpty())) {
            Toast.makeText(this, R.string.data_input_failed, Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent();
            Bitmap bitmap;
            byte[] data;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            setProgressBar();
            if (isDefaultImageSelected){
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.student);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            } else {
                photoButton.setDrawingCacheEnabled(true);
                photoButton.buildDrawingCache();
                bitmap = photoButton.getDrawingCache();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);
            }
            data = baos.toByteArray();
            if (getIntent().hasExtra(StudentEntity.class.getCanonicalName()) && data.length != 0) {
                editableStudent.setFirstName(firstName);
                editableStudent.setLastName(lastName);
                if (isImageChanged)
                    editableStudent.setPhoto(data);
                editableStudent.setScore(score);
                i.putExtra(StudentEntity.class.getCanonicalName(), editableStudent);
            } else if (data.length != 0){
                i.putExtra(StudentEntity.class.getCanonicalName(), new StudentEntity(firstName, lastName, data, score));
            }

            setResult(RESULT_OK, i);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_PHOTO:
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(Objects.requireNonNull(imageUri));
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        photoButton.setImageBitmap(selectedImage);
                        isImageChanged = true;
                        isImageSelected = true;
                        isDefaultImageSelected = false;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    break;
                case REQUEST_TAKE_PHOTO:
                    galleryAddPic();
                    setPic();
                    isImageSelected = true;
                    isDefaultImageSelected = false;
            }
        }
    }

    public void setProgressBar() {
        progressBar = new ProgressDialog(AddEditStudentActivity.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        new Thread(() -> {
            while (progressBarStatus < 100) {
                progressBarStatus += 25;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progressBarHandler.post(() -> progressBar.setProgress(progressBarStatus));
            }
            if (progressBarStatus >= 100) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.dismiss();
            }
        }).start();
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
